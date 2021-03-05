package vini2003.xyz.harmfulgas;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasParticleFactories;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasParticleTypes;

@Environment(EnvType.CLIENT)
public class HarmfulGasClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HarmfulGasParticleTypes.initialize();
		HarmfulGasParticleFactories.initialize();
		HarmfulGasNetworking.initialize();
	}
}
