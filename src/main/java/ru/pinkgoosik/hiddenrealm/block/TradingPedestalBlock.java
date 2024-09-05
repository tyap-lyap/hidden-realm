package ru.pinkgoosik.hiddenrealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;
import ru.pinkgoosik.hiddenrealm.data.BazaarTrades;
import ru.pinkgoosik.hiddenrealm.data.Trade;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

public class TradingPedestalBlock extends Block implements BlockEntityProvider {

	public TradingPedestalBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TradingPedestalBlockEntity(pos, state);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if(!world.isClient && world.getBlockEntity(pos) instanceof TradingPedestalBlockEntity entity) {
			Trade trade = BazaarTrades.TRADES.get(Random.create().nextInt(BazaarTrades.TRADES.size()));

			entity.sellingItem = new ItemStack(trade.item, trade.count);
			entity.price = trade.price;
			entity.renewable = trade.renewable;
		}
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if(world.getBlockEntity(pos) instanceof TradingPedestalBlockEntity entity) {
			if(!entity.sellingItem.isEmpty() && ((LunarCoinExtension)player).getLunarCoin() >= entity.price) {
				if(!world.isClient()) {
					dropStack(world, pos.up(), entity.sellingItem.copy());
					if(!entity.renewable) {
						entity.sellingItem = ItemStack.EMPTY;
						entity.updateListeners();
					}
					((LunarCoinExtension)player).setLunarCoin(((LunarCoinExtension)player).getLunarCoin() - entity.price);
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hit);
	}
}
