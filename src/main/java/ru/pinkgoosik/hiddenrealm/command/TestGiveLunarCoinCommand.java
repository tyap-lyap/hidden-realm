package ru.pinkgoosik.hiddenrealm.command;

import com.mojang.brigadier.CommandDispatcher;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TestGiveLunarCoinCommand {

		public static void init() {
			CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> register(dispatcher));
		}

		public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
			dispatcher.register(literal("setlunar")
				.then(argument("lunar",integer())
					.executes(ctx -> setLunar(ctx.getSource(), getInteger(ctx, "lunar")))));
		}

		private static int setLunar(ServerCommandSource source, int index) {

			if(source.getPlayer() != null){
				((LunarCoinExtension)source.getPlayer()).setLunarCoin(index);
			}

			return 1;
		}
	}
