package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;
import ru.pinkgoosik.hiddenrealm.data.BazaarInstance;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

public class RefresherBlock extends Block {
	public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;


	public RefresherBlock(Settings settings) {
		super(settings);

		setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HALF, FACING);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

		if(((LunarCoinExtension)player).getLunarCoin() >= 2) {
			if(!world.isClient) {
				if(world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
					BazaarInstance.refreshTrades();
				}
				else {
					for (int y = -4; y <= 4; y++) {
						for (int x = -10; x <= 10; x++) {
							for (int z = -10; z <= 10; z++) {
								BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

								if(world.getBlockEntity(blockPos) instanceof TradingPedestalBlockEntity entity) {
									entity.refresh();
								}
							}
						}
					}
				}

				((LunarCoinExtension)player).setLunarCoin(((LunarCoinExtension)player).getLunarCoin() - 2);
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {

		switch (state.get(HALF)) {
			case LOWER -> world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
			case UPPER -> world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
		}
		return super.onBreak(world, pos, state, player);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		if(state.get(HALF).equals(DoubleBlockHalf.UPPER)) {
			return BlockRenderType.INVISIBLE;
		}
		return super.getRenderType(state);
	}
}
