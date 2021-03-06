package vini2003.xyz.harmfulgas.registry.common;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasCommands {
	public static int place(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BlockPos pos = BlockPosArgumentType.getBlockPos(context, "pos");
		
		World world = context.getSource().getWorld();
		
		WorldGasComponent.get(world).add(pos);
		
		return 1;
	}
	
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> harmfulGasRoot = CommandManager.literal("harmfulgas").build();
			
			LiteralCommandNode<ServerCommandSource> harmfulGasPlace =
					CommandManager.literal("place")
							.requires((source) -> source.hasPermissionLevel(2))
							.then(
									CommandManager.argument("pos", BlockPosArgumentType.blockPos()).executes(HarmfulGasCommands::place).build()
							)
							.build();
			
			harmfulGasRoot.addChild(harmfulGasPlace);
			
			dispatcher.getRoot().addChild(harmfulGasRoot);
		});
	}
}
