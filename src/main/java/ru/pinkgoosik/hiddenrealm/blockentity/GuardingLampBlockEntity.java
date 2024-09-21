package ru.pinkgoosik.hiddenrealm.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.block.GuardingLampBlock;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;

public class GuardingLampBlockEntity extends BlockEntity {

	public GuardingLampBlockEntity(BlockPos pos, BlockState state) {
		super(HiddenRealmBlockEntities.GUARDING_LAMP, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, GuardingLampBlockEntity blockEntity) {
		if(!state.get(GuardingLampBlock.ENABLED)) return;

		if(world instanceof ServerWorld serverWorld) {
			serverWorld.getPlayers().forEach(player -> {
				float distance = MathHelper.sqrt((float)((player.getX() - (pos.getX() + 0.5)) * (player.getX() - (pos.getX() + 0.5)) + (player.getZ() - (pos.getZ() + 0.5)) * (player.getZ() - (pos.getZ() + 0.5))));

				if(distance <= 25.0 && (player.getY() <= pos.getY() + 12 && player.getY() >= pos.getY() - 8)) {
					if(serverWorld.getTime() % 20 == 0) {
//						player.sendMessage(Text.literal("distance: " + distance), true);
						player.addStatusEffect(new StatusEffectInstance(HiddenRealmEffects.GUARDING_LAMP_CURSE, 40, 0, false, false));
					}
				}
			});
		}
	}
}
