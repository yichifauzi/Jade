package snownee.jade.addon.debug;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;

public enum EntityAttributesProvider implements IEntityComponentProvider {

	INSTANCE;

	@Override
	public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
		LivingEntity entity = (LivingEntity) accessor.getEntity();
		AttributeInstance attribute = entity.getAttribute(Attributes.SCALE);
		if (attribute == null) {
			return;
		}
		double defaultValue = attribute.getAttribute().value().getDefaultValue();
	}

	@Override
	public ResourceLocation getUid() {
		return JadeIds.DEBUG_ENTITY_ATTRIBUTES;
	}

	@Override
	public boolean enabledByDefault() {
		return false;
	}
}
