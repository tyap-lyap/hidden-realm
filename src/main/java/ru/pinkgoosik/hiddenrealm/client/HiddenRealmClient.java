package ru.pinkgoosik.hiddenrealm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.block.RefresherBlock;
import ru.pinkgoosik.hiddenrealm.block.TradingPedestalBlock;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;
import ru.pinkgoosik.hiddenrealm.client.model.ShopkeeperModel;
import ru.pinkgoosik.hiddenrealm.client.model.TestLunarCreeperModel;
import ru.pinkgoosik.hiddenrealm.client.render.LunarCoinEntityRenderer;
import ru.pinkgoosik.hiddenrealm.client.render.TestLunarCreeperRenderer;
import ru.pinkgoosik.hiddenrealm.client.render.TradingPedestalRenderer;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

public class HiddenRealmClient implements ClientModInitializer {

	public static final EntityModelLayer SHOPKEEPER_LAYER = new EntityModelLayer(HiddenRealmMod.id("shopkeeper"), "main");
	public static final EntityModelLayer TESTLUNARCREEPER_LAYER = new EntityModelLayer(HiddenRealmMod.id("lunar_creeper"), "main");

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(HiddenRealmEntities.TEST_LUNAR_CREEPER, TestLunarCreeperRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(TESTLUNARCREEPER_LAYER, TestLunarCreeperModel::getTexturedModelData);

		EntityRendererRegistry.register(HiddenRealmEntities.SHOPKEEPER, (context) -> new MobEntityRenderer(context, new ShopkeeperModel(context.getPart(SHOPKEEPER_LAYER)), 0.5f) {
            @Override
            public Identifier getTexture(Entity entity) {
                return HiddenRealmMod.id("textures/entity/shopkeeper.png");
            }
        });

		EntityRendererRegistry.register(HiddenRealmEntities.LUNAR_COIN, (context) -> new LunarCoinEntityRenderer(context) {});
		EntityRendererRegistry.register(HiddenRealmEntities.FIRE_TRAIL, (context) -> new EmptyEntityRenderer<>(context) {});

		EntityModelLayerRegistry.registerModelLayer(SHOPKEEPER_LAYER, ShopkeeperModel::getTexturedModelData);

		BlockEntityRendererFactories.register(HiddenRealmBlockEntities.TRADING_PEDESTAL, ctx -> new TradingPedestalRenderer<>());

		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			onHudRender(drawContext);
		});

		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.BOTTLE_WISP, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.BAZAAR_LAMP, RenderLayer.getCutout());
	}

	private static void onHudRender(DrawContext context) {
		MinecraftClient client = MinecraftClient.getInstance();
		HitResult hitResult = client.crosshairTarget;

		if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState block = client.world.getBlockState(blockPos);

			if(block.getBlock() instanceof TradingPedestalBlock && client.world.getBlockEntity(blockPos) instanceof TradingPedestalBlockEntity entity && !entity.sellingItem.isEmpty()) {
				context.getMatrices().push();
				var height = context.getScaledWindowHeight();
				var width = context.getScaledWindowWidth();

				int price = entity.price;

				if(price > ((LunarCoinExtension)client.player).getLunarCoin()) {
					var text = Text.translatable("message.hiddenrealm.not_enough_coins");
					context.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2), (height / 2) + 10, 16076354);
				}
				else {
					context.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID,"lunar_coin_ui"), (width / 2) - (30 + client.textRenderer.getWidth(String.valueOf(price))), (height / 2) + 7 ,1, 15, 15);
					context.drawTextWithShadow(client.textRenderer, price + " -> ", (width / 2) - (12 + client.textRenderer.getWidth(String.valueOf(price))), (height / 2) + 11, 1622517);
					context.drawItem(entity.sellingItem, (width / 2) + 5, (height / 2) + 5);

					if(entity.sellingItem.getCount() > 1) {
						context.drawTextWithShadow(client.textRenderer, String.valueOf(entity.sellingItem.getCount()), (width / 2) + 22, (height / 2) + 11, 1622517);
					}
				}

				context.getMatrices().pop();
			}
			if(block.getBlock() instanceof RefresherBlock) {
				context.getMatrices().push();
				var height = context.getScaledWindowHeight();
				var width = context.getScaledWindowWidth();
				int price = 2;

				if(((LunarCoinExtension)client.player).getLunarCoin() >= price) {
					var text = Text.literal(Language.getInstance().get("message.hiddenrealm.refresh_price").replace("%price%", String.valueOf(price)));

					context.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2) + 10, (height / 2) + 10, 1622517);
					context.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID,"lunar_coin_ui"), (width / 2) - (8 + (client.textRenderer.getWidth(text) / 2)), (height / 2) + 5 ,1, 15, 15);
				}
				else {
					var text = Text.translatable("message.hiddenrealm.not_enough_coins");
					context.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2), (height / 2) + 10, 16076354);
				}

				context.getMatrices().pop();
			}
		}
	}
}
