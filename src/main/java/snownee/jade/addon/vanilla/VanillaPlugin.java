package snownee.jade.addon.vanilla;

import java.util.Map;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.TrappedChestBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.ForgeRegistries;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.Identifiers;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IWailaConfig.IConfigOverlay;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.config.PluginConfig;
import snownee.jade.overlay.DisplayHelper;

@WailaPlugin
public class VanillaPlugin implements IWailaPlugin {

	public static IWailaClientRegistration CLIENT_REGISTRATION;
	private static float savedProgress;
	private static float progressAlpha;
	private static boolean canHarvest;

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(BrewingStandProvider.INSTANCE, BrewingStandBlockEntity.class);
		registration.registerBlockDataProvider(BeehiveProvider.INSTANCE, BeehiveBlockEntity.class);
		registration.registerBlockDataProvider(CommandBlockProvider.INSTANCE, CommandBlockEntity.class);
		registration.registerBlockDataProvider(JukeboxProvider.INSTANCE, JukeboxBlockEntity.class);
		registration.registerBlockDataProvider(LecternProvider.INSTANCE, LecternBlockEntity.class);
		registration.registerBlockDataProvider(RedstoneProvider.INSTANCE, ComparatorBlockEntity.class);
		registration.registerBlockDataProvider(FurnaceProvider.INSTANCE, AbstractFurnaceBlockEntity.class);

		registration.registerEntityDataProvider(ChestedHorseProvider.INSTANCE, AbstractChestedHorse.class);
		registration.registerEntityDataProvider(PotionEffectsProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityDataProvider(MobGrowthProvider.INSTANCE, AgeableMob.class);
		registration.registerEntityDataProvider(MobBreedingProvider.INSTANCE, Animal.class);
		registration.registerEntityDataProvider(ChickenEggProvider.INSTANCE, Chicken.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		CLIENT_REGISTRATION = registration;

		registration.registerBlockComponent(BlockStatesProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(BrewingStandProvider.INSTANCE, BrewingStandBlock.class);
		registration.registerEntityComponent(HorseStatsProvider.INSTANCE, AbstractHorse.class);
		registration.registerEntityComponent(ChestedHorseProvider.INSTANCE, AbstractChestedHorse.class);
		registration.registerEntityComponent(ItemFrameProvider.INSTANCE, ItemFrame.class);
		registration.registerEntityComponent(PotionEffectsProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityComponent(MobGrowthProvider.INSTANCE, AgeableMob.class);
		registration.registerEntityComponent(MobBreedingProvider.INSTANCE, Animal.class);
		registration.registerBlockComponent(TNTStabilityProvider.INSTANCE, TntBlock.class);
		registration.registerBlockComponent(BeehiveProvider.INSTANCE, BeehiveBlock.class);
		registration.registerBlockComponent(NoteBlockProvider.INSTANCE, NoteBlock.class);
		registration.registerEntityComponent(ArmorStandProvider.INSTANCE, ArmorStand.class);
		registration.registerEntityComponent(PaintingProvider.INSTANCE, Painting.class);
		registration.registerEntityComponent(ChickenEggProvider.INSTANCE, Chicken.class);
		registration.registerBlockComponent(HarvestToolProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(CommandBlockProvider.INSTANCE, CommandBlock.class);
		registration.registerBlockComponent(EnchantmentPowerProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(TotalEnchantmentPowerProvider.INSTANCE, EnchantmentTableBlock.class);
		registration.registerBlockComponent(PlayerHeadProvider.INSTANCE, AbstractSkullBlock.class);
		registration.registerEntityComponent(VillagerProfessionProvider.INSTANCE, Villager.class);
		registration.registerEntityComponent(VillagerProfessionProvider.INSTANCE, ZombieVillager.class);
		registration.registerEntityComponent(ItemTooltipProvider.INSTANCE, ItemEntity.class);
		registration.registerBlockComponent(FurnaceProvider.INSTANCE, AbstractFurnaceBlock.class);
		registration.registerEntityComponent(AnimalOwnerProvider.INSTANCE, Entity.class);
		registration.registerEntityComponent(FallingBlockProvider.INSTANCE, FallingBlockEntity.class);
		registration.registerEntityIcon(FallingBlockProvider.INSTANCE, FallingBlockEntity.class);
		registration.registerEntityComponent(EntityHealthProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityComponent(EntityArmorProvider.INSTANCE, LivingEntity.class);
		registration.registerBlockComponent(RedstoneProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(CropProgressProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(JukeboxProvider.INSTANCE, JukeboxBlock.class);
		registration.registerBlockComponent(LecternProvider.INSTANCE, LecternBlock.class);
		registration.registerBlockComponent(MobSpawnerProvider.INSTANCE, SpawnerBlock.class);

		((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(HarvestToolProvider.INSTANCE);

		registration.addConfig(Identifiers.MC_EFFECTIVE_TOOL, true);
		registration.addConfig(Identifiers.MC_HARVEST_TOOL_NEW_LINE, false);
		registration.addConfig(Identifiers.MC_BREAKING_PROGRESS, true);

		registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
			Player player = accessor.getPlayer();
			if (player.isCreative() || player.isSpectator())
				return accessor;
			if (accessor instanceof BlockAccessor target) {
				if (target.getBlock() instanceof TrappedChestBlock) {
					BlockState state = getCorrespondingNormalChest(target.getBlockState());
					if (state != target.getBlockState()) {
						return CLIENT_REGISTRATION.createBlockAccessor(state, target.getBlockEntity(), target.getLevel(), player, target.getServerData(), target.getHitResult(), target.isServerConnected());
					}
				} else if (target.getBlock() instanceof InfestedBlock) {
					Block block = ((InfestedBlock) target.getBlock()).getHostBlock();
					return CLIENT_REGISTRATION.createBlockAccessor(block.defaultBlockState(), target.getBlockEntity(), target.getLevel(), player, target.getServerData(), target.getHitResult(), target.isServerConnected());
				} else if (target.getBlock() == Blocks.POWDER_SNOW) {
					Block block = Blocks.SNOW_BLOCK;
					return CLIENT_REGISTRATION.createBlockAccessor(block.defaultBlockState(), null, target.getLevel(), player, target.getServerData(), target.getHitResult(), target.isServerConnected());
				}
			}
			return accessor;
		});

		registration.addAfterRenderCallback((tooltip, rect, matrixStack, accessor) -> {
			if (!PluginConfig.INSTANCE.get(Identifiers.MC_BREAKING_PROGRESS)) {
				progressAlpha = 0;
				return;
			}
			Minecraft mc = Minecraft.getInstance();
			MultiPlayerGameMode playerController = mc.gameMode;
			if (playerController == null || playerController.destroyBlockPos == null) {
				return;
			}
			BlockState state = mc.level.getBlockState(playerController.destroyBlockPos);
			if (playerController.isDestroying())
				canHarvest = ForgeHooks.isCorrectToolForDrops(state, mc.player);
			int color = canHarvest ? 0xFFFFFF : 0xFF4444;
			int height = rect.getHeight();
			int width = rect.getWidth();
			if (!VanillaPlugin.CLIENT_REGISTRATION.getConfig().getOverlay().getSquare()) {
				height -= 1;
				width -= 2;
			}
			progressAlpha += mc.getDeltaFrameTime() * (playerController.isDestroying() ? 0.1F : -0.1F);
			if (playerController.isDestroying()) {
				progressAlpha = Math.min(progressAlpha, 0.53F); //0x88 = 0.53 * 255
				float progress = state.getDestroyProgress(mc.player, mc.player.level, playerController.destroyBlockPos);
				if (playerController.destroyProgress + progress >= 1) {
					progressAlpha = 1;
				}
				progress = playerController.destroyProgress + mc.getFrameTime() * progress;
				progress = Mth.clamp(progress, 0, 1);
				savedProgress = progress;
			} else {
				progressAlpha = Math.max(progressAlpha, 0);
			}
			color = IConfigOverlay.applyAlpha(color, progressAlpha);
			DisplayHelper.fill(matrixStack, 0, height - 1, width * savedProgress, height, color);

		});
	}

	public static IDisplayHelper getDisplayHelper() {
		return CLIENT_REGISTRATION.getDisplayHelper();
	}

	public static IElementHelper getElementHelper() {
		return CLIENT_REGISTRATION.getElementHelper();
	}

	private static final Cache<BlockState, BlockState> CHEST_CACHE = CacheBuilder.newBuilder().build();

	private static BlockState getCorrespondingNormalChest(BlockState state) {
		try {
			return CHEST_CACHE.get(state, () -> {
				ResourceLocation trappedName = state.getBlock().getRegistryName();
				if (trappedName.getPath().startsWith("trapped_")) {
					ResourceLocation chestName = new ResourceLocation(trappedName.getNamespace(), trappedName.getPath().substring(8));
					Block block = ForgeRegistries.BLOCKS.getValue(chestName);
					if (block != null) {
						return copyProperties(state, block.defaultBlockState());
					}
				}
				return state;
			});
		} catch (Exception e) {
			return state;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> BlockState copyProperties(BlockState oldState, BlockState newState) {
		for (Map.Entry<Property<?>, Comparable<?>> entry : oldState.getValues().entrySet()) {
			Property<T> property = (Property<T>) entry.getKey();
			if (newState.hasProperty(property))
				newState = newState.setValue(property, property.getValueClass().cast(entry.getValue()));
		}
		return newState;
	}
}