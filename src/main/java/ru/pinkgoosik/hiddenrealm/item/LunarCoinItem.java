package ru.pinkgoosik.hiddenrealm.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.entity.LunarCoinEntity;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

public class LunarCoinItem extends Item {

	public LunarCoinItem(Settings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		((LunarCoinExtension)user).addLunarCoin(1);

		itemStack.decrementUnlessCreative(1, user);
		return TypedActionResult.success(itemStack, world.isClient());
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {

		if(context.getSide().equals(Direction.UP)) {
			if(!context.getWorld().isClient()) {
				var coin = new LunarCoinEntity(HiddenRealmEntities.LUNAR_COIN, context.getWorld());
				coin.refreshPositionAndAngles(context.getHitPos().getX(), context.getHitPos().getY(), context.getHitPos().getZ(), 0, 0);
				context.getWorld().spawnEntity(coin);
			}
			return ActionResult.SUCCESS;
		}

		return super.useOnBlock(context);
	}
}
