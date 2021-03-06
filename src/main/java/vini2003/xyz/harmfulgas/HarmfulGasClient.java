package vini2003.xyz.harmfulgas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import vini2003.xyz.harmfulgas.registry.client.*;

@Environment(EnvType.CLIENT)
public class HarmfulGasClient implements ClientModInitializer {
	public static boolean isNearGasCloud= false;
	
	@Override
	public void onInitializeClient() {
		HarmfulGasCallbacks.initialize();
		HarmfulGasParticleTypes.initialize();
		HarmfulGasParticleFactories.initialize();
		HarmfulGasNetworking.initialize();
		HarmfulGasShaders.initialize();
		HarmfulGasTextureSheets.initialize();
	}
}
