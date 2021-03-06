package vini2003.xyz.harmfulgas.client.particle;

import net.minecraft.client.render.RenderPhase;

public class RenderPhaseAccessor extends RenderPhase {
	public RenderPhaseAccessor(String name, Runnable beginAction, Runnable endAction) {
		super(name, beginAction, endAction);
	}
	
	public static RenderPhase.Target getParticlesTarget() {
		return PARTICLES_TARGET;
	}
}
