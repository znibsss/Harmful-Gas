package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.util.math.Vector3f;
import vini2003.xyz.harmfulgas.HarmfulGasClient;
import vini2003.xyz.harmfulgas.client.utilities.GasParticleUtilities;

public class HarmfulGasCallbacks {
	private static void recalculateRotatedVertices(WorldRenderContext context) {
		GasParticleUtilities.recalculateRotatedVertices(context);
	}
	
	public static void initialize() {
		WorldRenderEvents.START.register(HarmfulGasCallbacks::recalculateRotatedVertices);
	}
}
