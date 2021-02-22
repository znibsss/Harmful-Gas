package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;

import java.util.HashSet;
import java.util.Set;

public class BreakABoneCallbacks {
	private static final Set<Block> ONE_HANDED_BLOCKS = new HashSet<Block>() {{
		add(Blocks.ANVIL);
		add(Blocks.CHIPPED_ANVIL);
		add(Blocks.DAMAGED_ANVIL);
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
			
			if (block == Blocks.CRAFTING_TABLE && (!bodyParts.hasLeftArm() || !bodyParts.hasRightArm())) {
				return ActionResult.FAIL;
			} else if (!bodyParts.hasAnyArm()) {
				return ActionResult.FAIL;
			}
			
			return ActionResult.PASS;
		}));
	}
}
