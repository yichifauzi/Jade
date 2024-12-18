package snownee.jade.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.server.packs.resources.ResourceManager;
import snownee.jade.util.JadeLanguages;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
	@Inject(method = "onResourceManagerReload", at = @At("RETURN"))
	private void jade$onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
		JadeLanguages.INSTANCE.onResourceManagerReload(resourceManager);
	}
}
