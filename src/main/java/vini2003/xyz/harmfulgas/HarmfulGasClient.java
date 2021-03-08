package vini2003.xyz.harmfulgas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.Vector3f;
import vini2003.xyz.harmfulgas.registry.client.*;

@Environment(EnvType.CLIENT)
public class HarmfulGasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HarmfulGasCallbacks.initialize();
		HarmfulGasParticleTypes.initialize();
		HarmfulGasParticleFactories.initialize();
		HarmfulGasNetworking.initialize();
		HarmfulGasTextureSheets.initialize();
	}
}
