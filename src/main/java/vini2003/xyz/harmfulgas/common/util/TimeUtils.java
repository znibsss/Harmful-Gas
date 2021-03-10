package vini2003.xyz.harmfulgas.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public class TimeUtils {
	public static int calculateMaximumHeight(World world, BlockPos pos, long age, int speed) {
		return Math.min(world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY() + 2, (int) (96 + age / (1500.0F / (21 - speed))));
	}
	
	public static int calculateMinimumHeight(long age, int speed) {
		return (int) (48 - age / (3000.0F / (21 - speed)));
	}
	
	
	public static int calculateMinimumDistance(long age, int speed) {
		return (int) (64 + age / (375.0F / (21 - speed)));
	}
	
	public static int calculateFollowTime(long age) {
		return age < 600 ? 200 : 600;
	}
}
