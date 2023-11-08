package snownee.jade.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.api.Identifiers;

@Mixin(HarvestToolProvider.class)
public abstract class HarvestToolProviderMixin implements IdentifiableResourceReloadListener {

	@Override
	public ResourceLocation getFabricId() {
		return Identifiers.MC_HARVEST_TOOL;
	}

}
