package snownee.jade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.client.gui.Font;
import snownee.jade.api.config.IWailaConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

@Mixin(value = Font.StringRenderOutput.class, priority = 500)
public class StringRenderOutputMixin {

	@WrapOperation(method = "getShadowColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;scaleRGB(IF)I"))
	private int jade$getShadowColor(int i, float f, Operation<Integer> original) {
		if (DisplayHelper.enableBetterTextShadow() && IThemeHelper.get().isLightColorScheme()) {
			return IWailaConfig.Overlay.applyAlpha(i, 0.15F);
		}
		return original.call(i, f);
	}

}
