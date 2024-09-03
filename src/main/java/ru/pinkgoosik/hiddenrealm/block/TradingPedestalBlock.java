package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;

public class TradingPedestalBlock extends Block implements BlockEntityProvider {
	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

	public TradingPedestalBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TradingPedestalBlockEntity(pos, state);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if(world.getBlockEntity(pos) instanceof TradingPedestalBlockEntity entity) {
			if(!entity.sellingItem.isEmpty()) {
				if(!world.isClient()) {
					dropStack(world, pos.up(), entity.sellingItem.copy());
					entity.sellingItem = ItemStack.EMPTY;
					entity.updateListeners();
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hit);
	}

//	@Override
//	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//		return SHAPE;
//	}
}
