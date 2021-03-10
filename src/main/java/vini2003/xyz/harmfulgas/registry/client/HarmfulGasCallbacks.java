package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import vini2003.xyz.harmfulgas.client.util.GasParticleUtils;

public class HarmfulGasCallbacks {
	private static void recalculateRotatedVertices(WorldRenderContext context) {
		GasParticleUtils.recalculateRotatedVertices(context);
	}
	
	public static void initialize() {
		WorldRenderEvents.START.register(HarmfulGasCallbacks::recalculateRotatedVertices);
	}
}
