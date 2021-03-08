package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;
import vini2003.xyz.harmfulgas.HarmfulGas;

public class HarmfulGasParticleTypes {
	public static final DefaultParticleType GAS = Registry.register(Registry.PARTICLE_TYPE, HarmfulGas.identifier("gas"), FabricParticleTypes.simple(true));
	
	public static void initialize() {
	
	}
}
