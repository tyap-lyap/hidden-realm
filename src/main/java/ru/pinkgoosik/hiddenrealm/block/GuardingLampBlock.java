package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.blockentity.GuardingLampBlockEntity;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;

public class GuardingLampBlock extends Block implements BlockEntityProvider {
	private static final VoxelShape SHAPE = createShape();
	public static final BooleanProperty ENABLED = Properties.ENABLED;

	public GuardingLampBlock(Settings settings) {
		super(settings);
	}

	private static VoxelShape createShape() {
		VoxelShape shape = VoxelShapes.empty();
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.1875, 0.125, 0.1875, 0.8125, 0.6875, 0.8125), BooleanBiFunction.OR);
		shape = VoxelShapes.combine(shape, VoxelShapes.cuboid(0.3125, 0.6875, 0.3125, 0.6875, 0.8125, 0.6875), BooleanBiFunction.OR);

		return shape;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			this.toggle(state, world, pos, null);
			return ActionResult.CONSUME;
		}
	}

	public void toggle(BlockState state, World world, BlockPos pos, @Nullable PlayerEntity player) {
		state = state.cycle(ENABLED);
		world.setBlockState(pos, state, Block.NOTIFY_ALL);
		playClickSound(player, world, pos, state);
	}

	protected static void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
		float f = state.get(ENABLED) ? 0.6F : 0.5F;
		world.playSound(player, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ENABLED);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GuardingLampBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, HiddenRealmBlockEntities.GUARDING_LAMP, GuardingLampBlockEntity::tick);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
		return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
	}
}
