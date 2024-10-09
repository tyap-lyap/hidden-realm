package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LunarVinesBlock extends Block implements Fertilizable {
	private static final VoxelShape SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);

	public static final BooleanProperty TIP = BooleanProperty.of("tip");

	public LunarVinesBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(TIP, true));
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TIP);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if(!ctx.getWorld().getBlockState(ctx.getBlockPos().up()).isAir()) return super.getPlacementState(ctx);
		return null;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if(world.getBlockState(pos.up()).isAir()) return Blocks.AIR.getDefaultState();
		if(world.getBlockState(pos.down()).isAir()) return getDefaultState().with(TIP, true);
		if(world.getBlockState(pos.down()).isOf(this)) return getDefaultState().with(TIP, false);
		return state;
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(TIP) && world.getBlockState(pos.down()).isAir();
    }

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if(world.getBlockState(pos.down()).isAir()) {
			world.setBlockState(pos.down(), this.getDefaultState());
		}
	}
}
