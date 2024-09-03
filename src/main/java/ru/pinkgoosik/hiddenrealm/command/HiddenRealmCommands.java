package ru.pinkgoosik.hiddenrealm.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.extension.PlayerExtension;

import java.util.Set;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class HiddenRealmCommands {

	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> HiddenRealmCommands.register(dispatcher));
	}

	private static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("hiddenrealm").then(literal("teleport").then(literal("silent_bazaar").executes(context -> {
			var player = context.getSource().getPlayer();
			if(player instanceof PlayerExtension ex && !player.getWorld().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
				ex.savePrevPosition();
				player.teleport(player.getServer().getWorld(HiddenRealmMod.SILENT_BAZAAR), 0.5, 80, 0.5, Set.of(), -45, 0);
			}
			return 1;
		}))));

		dispatcher.register(literal("hiddenrealm").then(literal("setlunar")
			.then(argument("amount",  IntegerArgumentType.integer(0)).executes(ctx -> setLunar(ctx.getSource(), getInteger(ctx, "amount"))))));

		dispatcher.register(literal("hiddenrealm").then(literal("addlunar")
			.then(argument("amount",  IntegerArgumentType.integer()).executes(ctx -> addLunar(ctx.getSource(), getInteger(ctx, "amount"))))));

	}

	private static int setLunar(ServerCommandSource source, int coins) {
		if(source.getPlayer() != null) {
			((LunarCoinExtension)source.getPlayer()).setLunarCoin(coins);
		}
		return 1;
	}

	private static int addLunar(ServerCommandSource source, int coins) {
		if(source.getPlayer() != null) {
			((LunarCoinExtension)source.getPlayer()).addLunarCoin(coins);
		}
		return 1;
	}
}
