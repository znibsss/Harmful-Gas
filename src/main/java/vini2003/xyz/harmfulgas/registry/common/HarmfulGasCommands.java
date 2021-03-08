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
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;

public class HarmfulGasCommands {
	public static int start(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		BlockPos pos = context.getSource().getEntity().getBlockPos();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.add(pos);
		gasComponent.setOriginPos(((ServerWorld) world).getSpawnPos());
		gasComponent.getCooldowns().put(player.getUuid(), 300);
		
		player.sendMessage(new TranslatableText("text.harmfulgas.start").formatted(Formatting.GREEN), true);
		
		return 1;
	}
	
	public static int pause(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		gasComponent.setPaused(!gasComponent.isPaused());
		
		if (gasComponent.isPaused()) {
			player.sendMessage(new TranslatableText("text.harmfulgas.paused").formatted(Formatting.GREEN), true);
		} else {
			player.sendMessage(new TranslatableText("text.harmfulgas.unpaused").formatted(Formatting.RED), true);
		}
		
		return 1;
	}
	
	public static int speed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		World world = context.getSource().getWorld();
		
		PlayerEntity player = context.getSource().getPlayer();
		
		WorldGasComponent gasComponent = WorldGasComponent.get(world);
		
		int speed = IntegerArgumentType.getInteger(context, "speed");
		
		player.sendMessage(new TranslatableText("text.harmfulgas.speed", gasComponent.getSpeed(), speed).formatted(Formatting.GOLD), true);
		
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
		
		player.sendMessage(new TranslatableText("text.harmfulgas.refresh").formatted(Formatting.GOLD), true);
		
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
			
			LiteralCommandNode<ServerCommandSource> harmfulGasRefresh =
					CommandManager.literal("refresh")
							.requires((source) -> source.hasPermissionLevel(2))
							.executes(HarmfulGasCommands::refresh)
							.build();
			
			
			harmfulGasRoot.addChild(harmfulGasPlace);
			harmfulGasRoot.addChild(harmfulGasPause);
			harmfulGasRoot.addChild(harmfulGasSpeed);
			harmfulGasRoot.addChild(harmfulGasRefresh);
			
			dispatcher.getRoot().addChild(harmfulGasRoot);
		});
	}
}
