package ru.pinkgoosik.hiddenrealm.blockentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.data.BazaarTrades;
import ru.pinkgoosik.hiddenrealm.data.Trade;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;

public class TradingPedestalBlockEntity extends BlockEntity {
	public ItemStack sellingItem = ItemStack.EMPTY;
	public int price = 0;
	public boolean renewable = false;

	public TradingPedestalBlockEntity(BlockPos pos, BlockState state) {
		super(HiddenRealmBlockEntities.TRADING_PEDESTAL, pos, state);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);

		nbt.put("sellingItem", this.sellingItem.encodeAllowEmpty(registryLookup));
		nbt.putInt("price", this.price);
		nbt.putBoolean("renewable", this.renewable);
	}

	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);

		this.sellingItem = ItemStack.fromNbtOrEmpty(registryLookup, nbt.getCompound("sellingItem"));
		this.price = nbt.getInt("price");
		this.renewable = nbt.getBoolean("renewable");
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = super.toInitialChunkDataNbt(registryLookup);
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	public void updateListeners() {
		this.markDirty();
		this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
	}

	public void refresh() {
		Trade trade = BazaarTrades.TRADES.get(getWorld().getRandom().nextInt(BazaarTrades.TRADES.size()));

		this.sellingItem = new ItemStack(trade.item, trade.count);
		this.price = trade.price;
		this.renewable = trade.renewable;
		this.updateListeners();
	}
}

