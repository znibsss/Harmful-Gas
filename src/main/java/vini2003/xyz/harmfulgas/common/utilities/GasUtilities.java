package vini2003.xyz.harmfulgas.common.utilities;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class GasUtilities {
	public static boolean isTraversableForPropagation(World world, BlockState centerState, BlockPos centerPos, BlockState sideState, BlockPos sidePos, Direction direction) {
		if (world == null) return false;
		
		return sideState.getFluidState().isEmpty() &&
				!(Registry.BLOCK.getId(sideState.getBlock()).toString().equals("astromine:airlock") && !sideState.get(Properties.POWERED))
				&& (sideState.isAir() || !sideState.isSideSolidFullSquare(world, sidePos, direction.getOpposite()))
				&& (centerState.isAir() || !centerState.isSideSolidFullSquare(world, centerPos, direction)
				&& (!sideState.isOpaqueFullCube(world, centerPos)));
	}
}
