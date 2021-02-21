package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;

import java.util.HashSet;
import java.util.Set;

public class BreakABoneCallbacks {
	private static final Set<Block> TWO_HANDED_BLOCKS = new HashSet<Block>() {{
		add(Blocks.ANVIL);
		add(Blocks.CHIPPED_ANVIL);
		add(Blocks.DAMAGED_ANVIL);
		add(Blocks.CRAFTING_TABLE);
		add(Blocks.FURNACE);
		add(Blocks.BLAST_FURNACE);
		add(Blocks.SMOKER);
		add(Blocks.CARTOGRAPHY_TABLE);
		add(Blocks.FLETCHING_TABLE);
		add(Blocks.GRINDSTONE);
		add(Blocks.SMITHING_TABLE);
		add(Blocks.STONECUTTER);
		add(Blocks.LECTERN);
		add(Blocks.LOOM);
		add(Blocks.COMPOSTER);
		add(Blocks.ENCHANTING_TABLE);
		add(Blocks.BREWING_STAND);
		add(Blocks.CAULDRON);
	}};
	
	public static void initialize() {
		UseBlockCallback.EVENT.register(((playerEntity, world, hand, blockHitResult) -> {
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(playerEntity);
			
			Block block = world.getBlockState(blockHitResult.getBlockPos()).getBlock();
			
			if (TWO_HANDED_BLOCKS.contains(block)) {
				if (hand == Hand.OFF_HAND && bodyParts.hasLeftArm() && !bodyParts.hasRightArm()) {
					playerEntity.sendMessage(new TranslatableText("message.breakabone.inventory.no_right_arm").formatted(Formatting.RED), true);
					
					return ActionResult.FAIL;
				}
				
				if (hand == Hand.MAIN_HAND && bodyParts.hasRightArm() && !bodyParts.hasLeftArm()) {
					playerEntity.sendMessage(new TranslatableText("message.breakabone.inventory.no_left_arm").formatted(Formatting.RED), true);
					
					return ActionResult.FAIL;
				}
			}
			
			return ActionResult.PASS;
		}));
	}
}
