package ru.pinkgoosik.hiddenrealm.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.HiddenRealmClient;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	public abstract TextRenderer getTextRenderer();

	@Shadow
	public abstract void render(DrawContext context, RenderTickCounter tickCounter);

	@Inject(method = "renderMainHud", at = @At(value = "TAIL"))
	public void renderLunarCoins(DrawContext drawContext, RenderTickCounter tickCounter, CallbackInfo ci) {
		if (HiddenRealmClient.showLunarTimer != 0 || MinecraftClient.getInstance().world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
			RenderSystem.enableBlend();

			if(HiddenRealmClient.showLunarTimer <= 20 && !MinecraftClient.getInstance().world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())){

				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float)HiddenRealmClient.showLunarTimer / 20);
			} else {
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F,1.0F);
			}

			Text coin = Text.of(((LunarCoinExtension) MinecraftClient.getInstance().player).getLunarCoin() + "");

			int widthFrame = this.getTextRenderer().getWidth(coin) + 1;

			drawContext.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID, "left_coin_frame"), 20, 21, 15, 15);
			drawContext.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID, "coin_frame"), 35, 21, widthFrame - 7, 15);
			drawContext.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID, "right_coin_frame"), 20 + widthFrame - 7 + 15, 21, 15, 15);
			drawContext.drawGuiTexture(Identifier.of(HiddenRealmMod.MOD_ID, "lunar_coin_ui"), 22, 20, 1, 16, 16);
			drawContext.drawText(this.getTextRenderer(), coin, 39, 25, 1622517, true);

			RenderSystem.disableBlend();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
