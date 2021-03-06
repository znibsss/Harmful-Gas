package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import vini2003.xyz.harmfulgas.client.particle.GasParticle;
import vini2003.xyz.harmfulgas.client.utilities.ClientUtilities;

public class HarmfulGasCallbacks {
	public static void initialize() {
		WorldRenderEvents.AFTER_SETUP.register((context) -> {
			GasParticle.DRAW_PARTICLES = true;
			
			//ClientUtilities.getParticleManager().renderParticles(context.matrixStack(), null, context.lightmapTextureManager(), context.camera(), context.tickDelta());
		});
	}
}
