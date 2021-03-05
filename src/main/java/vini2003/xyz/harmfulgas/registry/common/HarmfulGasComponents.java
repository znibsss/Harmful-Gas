package vini2003.xyz.harmfulgas.registry.common;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import vini2003.xyz.harmfulgas.HarmfulGas;
import vini2003.xyz.harmfulgas.common.component.ChunkAtmosphereComponent;

public class HarmfulGasComponents implements ChunkComponentInitializer {
	public static final ComponentKey<ChunkAtmosphereComponent> CHUNK_ATMOSPHERE_COMPONENT = ComponentRegistry.getOrCreate(HarmfulGas.identifier("chunk_atmosphere_component"), ChunkAtmosphereComponent.class);
	
	public static void initialize() {
	
	}
	
	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(CHUNK_ATMOSPHERE_COMPONENT, ChunkAtmosphereComponent::new);
	}
}
