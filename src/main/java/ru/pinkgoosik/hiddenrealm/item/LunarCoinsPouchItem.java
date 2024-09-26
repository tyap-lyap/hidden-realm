package ru.pinkgoosik.hiddenrealm.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Language;
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

		int amount = 5 + world.getRandom().nextInt(11);
		((LunarCoinExtension)user).addLunarCoin(amount);
		var text = Text.literal(Language.getInstance().get("message.hiddenrealm.lunar_coins_pouch").replace("%amount%", String.valueOf(amount)));
		user.sendMessage(text, true);

		stack.decrementUnlessCreative(1, user);
		return TypedActionResult.success(stack);
	}
}
