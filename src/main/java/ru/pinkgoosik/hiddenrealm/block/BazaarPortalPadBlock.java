package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;

public class BazaarPortalPadBlock extends Block {

	public BazaarPortalPadBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		if(world.getBlockState(pos.up()).isAir()) {
			world.setBlockState(pos.up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());
			if(world.getBlockState(pos.up().up()).isAir()) {
				world.setBlockState(pos.up().up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());
			}
			if(world.getBlockState(pos.up().up().up()).isAir()) {
				world.setBlockState(pos.up().up().up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());
			}
		}

	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if(world.getBlockState(pos.up()).isOf(HiddenRealmBlocks.BAZAAR_PORTAL)) {
			world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
		}
		if(world.getBlockState(pos.up().up()).isOf(HiddenRealmBlocks.BAZAAR_PORTAL)) {
			world.setBlockState(pos.up().up(), Blocks.AIR.getDefaultState());
		}
		if(world.getBlockState(pos.up().up().up()).isOf(HiddenRealmBlocks.BAZAAR_PORTAL)) {
			world.setBlockState(pos.up().up().up(), Blocks.AIR.getDefaultState());
		}
		return super.onBreak(world, pos, state, player);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if(world.getBlockState(pos.up()).isAir()) {
			world.setBlockState(pos.up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());

			if(world.getBlockState(pos.up().up()).isAir()) {
				world.setBlockState(pos.up().up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());

				if(world.getBlockState(pos.up().up().up()).isAir()) {
					world.setBlockState(pos.up().up().up(), HiddenRealmBlocks.BAZAAR_PORTAL.getDefaultState());
				}
			}
		}
	}
}
