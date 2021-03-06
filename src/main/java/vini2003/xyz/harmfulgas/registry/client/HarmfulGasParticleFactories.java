package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import vini2003.xyz.harmfulgas.client.particle.GasCloudParticle;

public class HarmfulGasParticleFactories {
	public static void initialize() {
        ParticleFactoryRegistry.getInstance().register(HarmfulGasParticleTypes.GAS, GasCloudParticle.Factory::new);
	}
}
