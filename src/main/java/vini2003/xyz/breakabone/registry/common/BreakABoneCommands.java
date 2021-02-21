package vini2003.xyz.breakabone.registry.common;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class BreakABoneCommands {
	private static int rightArm(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setRightArm(BoolArgumentType.getBool(context, "rightArm"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}
	
	private static int leftArm(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setLeftArm(BoolArgumentType.getBool(context, "leftArm"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}
	
	private static int rightLeg(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setRightLeg(BoolArgumentType.getBool(context, "rightLeg"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}
	
	private static int leftLeg(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setLeftLeg(BoolArgumentType.getBool(context, "leftLeg"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}
	
	private static int head(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setHead(BoolArgumentType.getBool(context, "head"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}
	
	private static int torso(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		BreakABoneComponents.BODY_PARTS.get(context.getSource().getPlayer()).setTorso(BoolArgumentType.getBool(context, "torso"));
		BreakABoneComponents.BODY_PARTS.syncWith(context.getSource().getPlayer(), ComponentProvider.fromEntity(context.getSource().getPlayer()));
		
		return 1;
	}

	
	public static void initialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			LiteralCommandNode<ServerCommandSource> breakABoneRoot = CommandManager.literal("breakabone").build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneRightArm =
					CommandManager.literal("right_arm")
							.then(CommandManager.argument("rightArm", BoolArgumentType.bool())
									.executes(BreakABoneCommands::rightArm)
									.build()
							)
							.build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneLeftArm =
					CommandManager.literal("left_arm")
							.then(CommandManager.argument("leftArm", BoolArgumentType.bool())
									.executes(BreakABoneCommands::leftArm)
									.build()
							)
							.build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneRightLeg =
					CommandManager.literal("right_leg")
							.then(CommandManager.argument("rightLeg", BoolArgumentType.bool())
									.executes(BreakABoneCommands::rightLeg)
									.build()
							)
							.build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneLeftLeg =
					CommandManager.literal("left_leg")
							.then(CommandManager.argument("leftLeg", BoolArgumentType.bool())
									.executes(BreakABoneCommands::leftLeg)
									.build()
							)
							.build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneHead =
					CommandManager.literal("head")
							.then(CommandManager.argument("head", BoolArgumentType.bool())
									.executes(BreakABoneCommands::head)
									.build()
							)
							.build();
			
			LiteralCommandNode<ServerCommandSource> breakABoneTorso =
					CommandManager.literal("torso")
							.then(CommandManager.argument("torso", BoolArgumentType.bool())
									.executes(BreakABoneCommands::torso)
									.build()
							)
							.build();
			
			breakABoneRoot.addChild(breakABoneRightArm);
			breakABoneRoot.addChild(breakABoneLeftArm);
			
			breakABoneRoot.addChild(breakABoneRightLeg);
			breakABoneRoot.addChild(breakABoneLeftLeg);
			
			breakABoneRoot.addChild(breakABoneHead);
			
			breakABoneRoot.addChild(breakABoneTorso);

			dispatcher.getRoot().addChild(breakABoneRoot);
		});
	}
}
