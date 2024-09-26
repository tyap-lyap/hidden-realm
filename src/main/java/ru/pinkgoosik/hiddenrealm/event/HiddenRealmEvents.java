package ru.pinkgoosik.hiddenrealm.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.data.BazaarInstance;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.extension.PlayerExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

public class HiddenRealmEvents {

	public static void init() {

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			var id = key.getValue().toString();

			if(id.contains("minecraft:chests/") && !id.contains("minecraft:chests/village") && !id.contains("minecraft:chests/trial_chambers")) {
				LootPool.Builder poolBuilder = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(0.1F).build())
					.with(ItemEntry.builder(HiddenRealmItems.LUNAR_COINS_POUCH).build());
				tableBuilder.pool(poolBuilder.build());
			}
		});

		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			((LunarCoinExtension)newPlayer).setLunarCoin(((LunarCoinExtension)oldPlayer).getLunarCoin());
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			var bazaar = server.getWorld(HiddenRealmMod.SILENT_BAZAAR);

			if(bazaar != null && bazaar.getBlockState(new BlockPos(-32, 0, -32)).isAir()) {
				StructureTemplate structure = server.getStructureTemplateManager().getTemplateOrBlank(HiddenRealmMod.id("silent_bazaar"));
				StructurePlacementData data = new StructurePlacementData().setMirror(BlockMirror.NONE).setIgnoreEntities(true);
				structure.place(bazaar, new BlockPos(-32, 0, -32), new BlockPos(0, 0, 0), data, bazaar.getRandom(), 0);
			}

			BazaarInstance.init(server, bazaar);
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if(BazaarInstance.instance != null) BazaarInstance.serverTick(server);
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (!player.isCreative() && !player.isSpectator()) {
				if(world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue()) || player.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE)) {
					if(player.getStackInHand(hand).getItem() instanceof FluidModificationItem) {
						return TypedActionResult.fail(player.getStackInHand(hand));
					}
				}
			}

			return TypedActionResult.pass(player.getStackInHand(hand));
		});

		UseBlockCallback.EVENT.register((player, world, hand, res) -> {
			if (!player.isCreative() && !player.getStackInHand(hand).isEmpty() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue()) && player.getStackInHand(hand).getItem() instanceof BlockItem) {
				return ActionResult.FAIL;
			}

			if(!player.isCreative() && !player.getStackInHand(hand).isEmpty() && player.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE) && player.getStackInHand(hand).getItem() instanceof BlockItem) {
				return ActionResult.FAIL;
			}

			return ActionResult.PASS;
		});

		PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
			if (!player.isCreative() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
				return false;
			}
			if(!player.isCreative() && player.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE) && state.isIn(TagKey.of(RegistryKeys.BLOCK, HiddenRealmMod.id("protected_by_guarding_lamp")))) {
				return false;
			}
			return true;
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(!player.isCreative() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue()) && entity instanceof PlayerEntity) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
			if(entity instanceof ServerPlayerEntity player && player instanceof PlayerExtension ex) {

				if (player.getWorld().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
					player.setHealth(20F);
					ex.teleportToPrevPosition();
					player.setPortalCooldown(300);
					player.sendMessage(Text.translatable("message.hiddenrealm.silent_bazaar_death"));
					return false;
				}
			}

			return true;
		});
	}
}
