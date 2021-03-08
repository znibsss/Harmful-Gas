package vini2003.xyz.harmfulgas.registry.common;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasCommands {
	public static int start(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		BlockPos pos = context.getSource().getEntity().getBlockPos();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.add(pos);
		gasComponent.setOriginPos(pos);
		gasComponent.getCooldowns().put(player.getUuid(), 300);
		
		return 1;
	}
	
	public static int pause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.setPaused(!gasComponent.isPaused());
		
		return 1;
	}
	
	public static int speed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		int speed = IntegerArgumentType.getInteger(context, "speed");
		
		gasComponent.setSpeed(speed);
		
		return 1;
	}
	
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> harmfulGasRoot = CommandManager.literal("harmfulgas").build();
			
			LiteralCommandNode<ServerCommandSource> harmfulGasPlace =
					CommandManager.literal("start")
							.requires((source) -> source.hasPermissionLevel(2))
							.executes(HarmfulGasCommands::start)
							.build();
			
			LiteralCommandNode<ServerCommandSource> harmfulGasPause =
					CommandManager.literal("pause")
							.requires((source) -> source.hasPermissionLevel(2))
							.executes(HarmfulGasCommands::pause)
							.build();
			
			LiteralCommandNode<ServerCommandSource> harmfulGasSpeed =
					CommandManager.literal("speed")
							.requires((source) -> source.hasPermissionLevel(2))
							.then(
									CommandManager.argument("speed", IntegerArgumentType.integer(1, 100))
											.executes(HarmfulGasCommands::speed)
											.build()
							)
							.executes(HarmfulGasCommands::pause)
							.build();
			
			
			harmfulGasRoot.addChild(harmfulGasPlace);
			harmfulGasRoot.addChild(harmfulGasPause);
			harmfulGasRoot.addChild(harmfulGasSpeed);
			
			dispatcher.getRoot().addChild(harmfulGasRoot);
		});
	}
}
