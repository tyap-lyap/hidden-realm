package ru.pinkgoosik.hiddenrealm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

import java.awt.*;

public class HiddenRealmClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(HiddenRealmClient::drawLunarCoins);
	}

	private static void drawLunarCoins(DrawContext drawContext, RenderTickCounter renderTickCounter) {
		Color color = new Color(24, 193, 245);
		if (!MinecraftClient.getInstance().options.hudHidden) {
			drawContext.drawText(MinecraftClient.getInstance().textRenderer, Text.of(((LunarCoinExtension)MinecraftClient.getInstance().player).getLunarCoin() + " TEST COCK"), 30 / 2, 80, color.getRGB(), true);
		}
	}
}
