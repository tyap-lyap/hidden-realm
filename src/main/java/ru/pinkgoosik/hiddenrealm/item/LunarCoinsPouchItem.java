package ru.pinkgoosik.hiddenrealm.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

public class LunarCoinsPouchItem extends Item {

	public LunarCoinsPouchItem(Settings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if(world.isClient()) return TypedActionResult.success(stack);

		int amount = 5 + world.getRandom().nextInt(6);
		((LunarCoinExtension)user).addLunarCoin(amount);

		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BUNDLE_REMOVE_ONE, SoundCategory.PLAYERS, 1.0F, 1.0F, world.random.nextLong());
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, 2F, world.random.nextLong());

		stack.decrementUnlessCreative(1, user);
		return TypedActionResult.success(stack);
	}
}
