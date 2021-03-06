package vini2003.xyz.harmfulgas.registry.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import vini2003.xyz.harmfulgas.common.component.ChunkAtmosphereComponent;

public class HarmfulGasCallbacks {
	public static void initialize() {
		ServerTickEvents.START_SERVER_TICK.register((server) -> {
			ChunkAtmosphereComponent.resetTotalVolumesAdded();
		});
	}
}
