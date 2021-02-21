package vini2003.xyz.breakabone;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.options.GameOptions;
import vini2003.xyz.breakabone.registry.client.BreakABoneCallbacks;

@Environment(EnvType.CLIENT)
public class BreakABoneClient implements ClientModInitializer {
	public static boolean isBlind = false;
	
	@Override
	public void onInitializeClient() {
		BreakABoneCallbacks.initialize();
	}
}
