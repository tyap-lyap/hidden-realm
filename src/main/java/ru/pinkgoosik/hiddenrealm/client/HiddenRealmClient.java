package ru.pinkgoosik.hiddenrealm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
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
import ru.pinkgoosik.hiddenrealm.client.model.MoonblessedZombieModel;
import ru.pinkgoosik.hiddenrealm.client.model.ShopkeeperModel;
import ru.pinkgoosik.hiddenrealm.client.model.MoonblessedCreeperModel;
import ru.pinkgoosik.hiddenrealm.client.particle.BazaarPortalParticle;
import ru.pinkgoosik.hiddenrealm.client.render.LunarCoinEntityRenderer;
import ru.pinkgoosik.hiddenrealm.client.render.MoonblessedCreeperRenderer;
import ru.pinkgoosik.hiddenrealm.client.render.MoonblessedZombieRenderer;
import ru.pinkgoosik.hiddenrealm.client.render.TradingPedestalRenderer;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmParticles;

public class HiddenRealmClient implements ClientModInitializer {

	public static final EntityModelLayer SHOPKEEPER_LAYER = new EntityModelLayer(HiddenRealmMod.id("shopkeeper"), "main");
	public static final EntityModelLayer MOONBLESSED_ZOMBIE_LAYER = new EntityModelLayer(HiddenRealmMod.id("moonblessed_zombie"), "main");
	public static final EntityModelLayer MOONBLESSED_CREEPER_LAYER = new EntityModelLayer(HiddenRealmMod.id("moonblessed_creeper"), "main");
	public static final EntityModelLayer MOONBLESSED_SKELETON_LAYER = new EntityModelLayer(HiddenRealmMod.id("moonblessed_skeleton"), "main");

	public static int cachedCoins = 0;
	public static int showLunarTimer;
	public static int coinsDifference = 0;

	@Override
	public void onInitializeClient() {
		ParticleFactoryRegistry.getInstance().register(HiddenRealmParticles.BAZAAR_PORTAL, BazaarPortalParticle.Factory::new);

		EntityRendererRegistry.register(HiddenRealmEntities.MOONBLESSED_CREEPER, MoonblessedCreeperRenderer::new);
		EntityRendererRegistry.register(HiddenRealmEntities.MOONBLESSED_ZOMBIE, MoonblessedZombieRenderer::new);
		EntityRendererRegistry.register(HiddenRealmEntities.MOONBLESSED_SKELETON, (context) -> new BipedEntityRenderer(context, new SkeletonEntityModel(context.getPart(MOONBLESSED_SKELETON_LAYER)), 0.5f) {
			@Override
			public Identifier getTexture(Entity entity) {
				return HiddenRealmMod.id("textures/entity/moonblessed_skeleton.png");
			}
		});

		EntityRendererRegistry.register(HiddenRealmEntities.SHOPKEEPER, (context) -> new MobEntityRenderer(context, new ShopkeeperModel(context.getPart(SHOPKEEPER_LAYER)), 0.5f) {
            @Override
            public Identifier getTexture(Entity entity) {
                return HiddenRealmMod.id("textures/entity/shopkeeper.png");
            }
        });


		EntityRendererRegistry.register(HiddenRealmEntities.LUNAR_COIN, (context) -> new LunarCoinEntityRenderer(context) {});
		EntityRendererRegistry.register(HiddenRealmEntities.FIRE_TRAIL, (context) -> new EmptyEntityRenderer<>(context) {});
		EntityRendererRegistry.register(HiddenRealmEntities.SPORE, (context) -> new EmptyEntityRenderer<>(context) {});

		EntityModelLayerRegistry.registerModelLayer(MOONBLESSED_CREEPER_LAYER, MoonblessedCreeperModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SHOPKEEPER_LAYER, ShopkeeperModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MOONBLESSED_ZOMBIE_LAYER, MoonblessedZombieModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MOONBLESSED_SKELETON_LAYER, SkeletonEntityModel::getTexturedModelData);

		BlockEntityRendererFactories.register(HiddenRealmBlockEntities.TRADING_PEDESTAL, ctx -> new TradingPedestalRenderer<>());

		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			onHudRender(drawContext);
		});

		ClientTickEvents.START_WORLD_TICK.register((world) -> {
			if(MinecraftClient.getInstance().player instanceof LunarCoinExtension extension) {
				if (cachedCoins != extension.getLunarCoin()) {
					coinsDifference = extension.getLunarCoin() - cachedCoins;
					cachedCoins = extension.getLunarCoin();
					showLunarTimer = 200;
				}
				if(showLunarTimer != 0) {
					showLunarTimer--;
				}
				else coinsDifference = 0;
			}

		});

		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.BOTTLE_WISP, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.BAZAAR_LAMP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.GUARDING_LAMP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.LUNAR_FLAG, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(HiddenRealmBlocks.LUNAR_VINES, RenderLayer.getCutout());
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


				context.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID,"lunar_coin_ui"), (width / 2) - (30 + client.textRenderer.getWidth(String.valueOf(price))), (height / 2) + 7 ,1, 16, 16);
				context.drawTextWithShadow(client.textRenderer, price + " -> ", (width / 2) - (12 + client.textRenderer.getWidth(String.valueOf(price))), (height / 2) + 11, 1622517);
				context.drawItem(entity.sellingItem, (width / 2) + 5, (height / 2) + 5);

				if(entity.sellingItem.getCount() > 1) {
					context.drawTextWithShadow(client.textRenderer, String.valueOf(entity.sellingItem.getCount()), (width / 2) + 22, (height / 2) + 11, 1622517);
				}


				if(price > ((LunarCoinExtension)client.player).getLunarCoin()) {
					var text = Text.translatable("message.hiddenrealm.not_enough_coins");
					context.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2), (height / 2) + 22, 16076354);
				}

				HiddenRealmClient.showLunarTimer = 50;
				context.getMatrices().pop();
			}
			if(block.getBlock() instanceof RefresherBlock) {
				context.getMatrices().push();
				var height = context.getScaledWindowHeight();
				var width = context.getScaledWindowWidth();
				int price = 2;


				var text = Text.literal(Language.getInstance().get("message.hiddenrealm.refresh_price").replace("%price%", String.valueOf(price)));

				context.drawTextWithShadow(client.textRenderer, text, (width / 2) - (client.textRenderer.getWidth(text) / 2) + 10, (height / 2) + 10, 1622517);
				context.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID,"lunar_coin_ui"), (width / 2) - (8 + (client.textRenderer.getWidth(text) / 2)), (height / 2) + 5 ,1, 15, 15);

				if(price > ((LunarCoinExtension)client.player).getLunarCoin()) {
					var noMoneyText = Text.translatable("message.hiddenrealm.not_enough_coins");
					context.drawTextWithShadow(client.textRenderer, noMoneyText, (width / 2) - (client.textRenderer.getWidth(noMoneyText) / 2), (height / 2) + 22, 16076354);
				}
				HiddenRealmClient.showLunarTimer = 50;
				context.getMatrices().pop();
			}
		}
	}
}
