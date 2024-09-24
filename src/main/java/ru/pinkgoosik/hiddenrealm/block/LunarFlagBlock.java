package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LunarFlagBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;

	private static final VoxelShape X_SHAPE = createXShape();
	private static final VoxelShape Z_SHAPE = createZShape();

	public LunarFlagBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
	}

	private static VoxelShape createZShape() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.4375, 0.6875, 0, 0.5625, 0.875, 1), BooleanBiFunction.OR);
		return shape;
	}

	private static VoxelShape createXShape() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0, 0.6875, 0.4375, 1, 0.875, 0.5625), BooleanBiFunction.OR);
		return shape;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(AXIS).equals(Direction.Axis.Z) ? Z_SHAPE : X_SHAPE;
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.get(AXIS)) {
                case Z -> state.with(AXIS, Direction.Axis.X);
                case X -> state.with(AXIS, Direction.Axis.Z);
                default -> state;
            };
            default -> state;
        };
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if(ctx.getSide().getAxis().isHorizontal()) {
			return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
		}
		else return null;
	}
}
