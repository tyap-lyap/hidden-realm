package ru.pinkgoosik.hiddenrealm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.model.ShopkeeperModel;
import ru.pinkgoosik.hiddenrealm.client.render.TradingPedestalRenderer;
import ru.pinkgoosik.hiddenrealm.entity.LunarCoinEntity;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

public class HiddenRealmClient implements ClientModInitializer {

	public static final EntityModelLayer SHOPKEEPER_LAYER = new EntityModelLayer(HiddenRealmMod.id("shopkeeper"), "main");

	@Override
	public void onInitializeClient() {

		EntityRendererRegistry.register(HiddenRealmEntities.SHOPKEEPER, (context) -> new MobEntityRenderer(context, new ShopkeeperModel(context.getPart(SHOPKEEPER_LAYER)), 0.5f) {
            @Override
            public Identifier getTexture(Entity entity) {
                return HiddenRealmMod.id("textures/entity/shopkeeper.png");
            }
        });

		EntityRendererRegistry.register(HiddenRealmEntities.LUNAR_COIN, (context) -> new EmptyEntityRenderer<>(context) {});

		EntityModelLayerRegistry.registerModelLayer(SHOPKEEPER_LAYER, ShopkeeperModel::getTexturedModelData);

		BlockEntityRendererFactories.register(HiddenRealmBlockEntities.TRADING_PEDESTAL, ctx -> new TradingPedestalRenderer<>());
	}
}
