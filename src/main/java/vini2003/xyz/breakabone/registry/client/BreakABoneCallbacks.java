package vini2003.xyz.breakabone.registry.client;

import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import vini2003.xyz.breakabone.BreakABoneClient;

public class BreakABoneCallbacks {
	public static void initialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			BreakABoneClient.isBlind = false;
		});
	}
}
