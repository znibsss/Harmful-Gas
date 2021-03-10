package vini2003.xyz.harmfulgas.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BlockPosUtils {
	public static double minimumSquaredDistance(List<? extends PlayerEntity> players, BlockPos pos) {
		double minimumSquaredDistance = Double.MAX_VALUE;
		
		for (PlayerEntity player : players) {
			if (minimumSquaredDistance > squaredDistance(player, pos)) {
				minimumSquaredDistance = squaredDistance(player, pos);
			}
		}
		
		return minimumSquaredDistance;
	}
	
	public static double squaredDistance(PlayerEntity player, BlockPos pos) {
		return player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
	}
}
