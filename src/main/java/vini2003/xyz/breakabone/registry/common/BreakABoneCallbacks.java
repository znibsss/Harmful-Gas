package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.common.component.TimerComponent;
import vini2003.xyz.breakabone.common.screenhandler.BodyPartSelectorScreenHandler;

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
		
		ServerTickEvents.END_SERVER_TICK.register((server) -> {
			server.getPlayerManager().getPlayerList().forEach(player -> {
				TimerComponent timer = BreakABoneComponents.TIMER.get(player);
				
				if (timer.hasMinutes(5) && !(player.currentScreenHandler instanceof BodyPartSelectorScreenHandler)) {
					timer.reset();
					
					BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(player);
					
					if (bodyParts.shouldRandomizeHead()) bodyParts.setHead(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					if (bodyParts.shouldRandomizeTorso()) bodyParts.setTorso(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					if (bodyParts.shouldRandomizeLeftArm()) bodyParts.setLeftArm(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					if (bodyParts.shouldRandomizeRightArm()) bodyParts.setRightArm(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					if (bodyParts.shouldRandomizeLeftLeg()) bodyParts.setLeftLeg(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					if (bodyParts.shouldRandomizeRightLeg()) bodyParts.setRightLeg(server.getWorld(World.OVERWORLD).getRandom().nextBoolean());
					
					BreakABoneComponents.BODY_PARTS.sync(player);
				}
			});
		});
	}
}
