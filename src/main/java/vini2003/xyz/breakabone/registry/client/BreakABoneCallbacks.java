package vini2003.xyz.breakabone.registry.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import vini2003.xyz.breakabone.BreakABoneClient;

public class BreakABoneCallbacks {
	public static void initialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			BreakABoneClient.isBlind = false;
		});
	}
}
