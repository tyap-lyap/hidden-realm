package ru.pinkgoosik.hiddenrealm.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;

import java.util.Set;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class HiddenRealmCommands {

	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> HiddenRealmCommands.register(dispatcher));
	}

	private static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(literal("hiddenrealm").then(literal("teleport").then(literal("silent_bazaar").executes(context -> {
			var player = context.getSource().getPlayer();
			if(player != null) {
				player.teleport(player.getServer().getWorld(HiddenRealmMod.SILENT_BAZAAR), 0.5, 80, 0.5, Set.of(), -45, 0);
			}
			return 1;
		}))));
	}
}
