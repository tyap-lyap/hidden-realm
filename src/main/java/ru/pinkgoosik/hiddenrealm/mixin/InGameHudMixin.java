package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

import java.awt.*;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(method = "renderMainHud", at = @At(value = "HEAD"))
	public void renderLunarCoins(DrawContext drawContext, RenderTickCounter tickCounter, CallbackInfo ci) {
		Color color = new Color(24, 193, 245);
			drawContext.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID,"lunar_coin_ui"), 20, 20 ,1, 15, 15);
			drawContext.drawText(MinecraftClient.getInstance().textRenderer, Text.of(((LunarCoinExtension)MinecraftClient.getInstance().player).getLunarCoin()+ ""), 38, 24, color.getRGB(), true);
	}
}
