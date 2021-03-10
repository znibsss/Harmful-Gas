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
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;

public class HarmfulGasCommands {
	public static int start(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.add(((ServerWorld) world).getSpawnPos());
		gasComponent.setOriginPos(((ServerWorld) world).getSpawnPos());
		gasComponent.getCooldowns().put(player.getUuid(), 300);
		
		context.getSource().sendFeedback(new TranslatableText("text.harmfulgas.start"), true);
		
		return 1;
	}
	
	public static int pause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.setPaused(true);
		
		context.getSource().sendFeedback(new TranslatableText("text.harmfulgas.paused"), true);
		
		return 1;
	}
	
	public static int resume(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.setPaused(false);
		
		context.getSource().sendFeedback(new TranslatableText("text.harmfulgas.resumed"), true);
		
		return 1;
	}
	
	
	public static int speed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		int speed = IntegerArgumentType.getInteger(context, "speed");
		
		context.getSource().sendFeedback(new TranslatableText("text.harmfulgas.speed", gasComponent.getSpeed(), speed), true);
		
		gasComponent.setSpeed(speed);
		
		return 1;
	}
	
	public static int refresh(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		HarmfulGasNetworking.sendRefreshGasCloudPacket(player);
		
		gasComponent.getParticles().remove(player.getUuid());
		gasComponent.getCooldowns().put(player.getUuid(), 150);
		
		context.getSource().sendFeedback(new TranslatableText("text.harmfulgas.refresh"), true);
		
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
			
			LiteralCommandNode<ServerCommandSource> harmfulGasResume =
					CommandManager.literal("resume")
							.requires((source) -> source.hasPermissionLevel(2))
							.executes(HarmfulGasCommands::resume)
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
			
			LiteralCommandNode<ServerCommandSource> harmfulGasRefresh =
					CommandManager.literal("refresh")
							.requires((source) -> source.hasPermissionLevel(2))
							.executes(HarmfulGasCommands::refresh)
							.build();
			
			
			harmfulGasRoot.addChild(harmfulGasPlace);
			harmfulGasRoot.addChild(harmfulGasPause);
			harmfulGasRoot.addChild(harmfulGasResume);
			harmfulGasRoot.addChild(harmfulGasSpeed);
			harmfulGasRoot.addChild(harmfulGasRefresh);
			
			dispatcher.getRoot().addChild(harmfulGasRoot);
		});
	}
}
