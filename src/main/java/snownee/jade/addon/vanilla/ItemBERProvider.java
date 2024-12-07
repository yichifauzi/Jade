package snownee.jade.addon.vanilla;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.JadeIds;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum ItemBERProvider implements IBlockComponentProvider {

	INSTANCE;

	@Override
	public @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
		BlockEntity blockEntity = accessor.getBlockEntity();
		if (blockEntity != null) {
			ItemStack itemStack = accessor.getPickedResult();
			CompoundTag compoundTag = blockEntity.saveCustomOnly(accessor.getLevel().registryAccess());
			//noinspection deprecation
			blockEntity.removeComponentsFromTag(compoundTag);
			BlockItem.setBlockEntityData(itemStack, blockEntity.getType(), compoundTag);
			itemStack.applyComponents(blockEntity.collectComponents());
			return IElementHelper.get().item(itemStack);
		}
		return null;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
	}

	@Override
	public ResourceLocation getUid() {
		return JadeIds.MC_ITEM_BER;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

}
