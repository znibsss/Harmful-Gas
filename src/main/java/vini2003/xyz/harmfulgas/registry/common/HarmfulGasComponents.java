package vini2003.xyz.harmfulgas.registry.common;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import vini2003.xyz.harmfulgas.HarmfulGas;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasComponents implements WorldComponentInitializer {
	public static final ComponentKey<WorldGasComponent> WORLD_GAS_COMPONENT = ComponentRegistry.getOrCreate(HarmfulGas.identifier("world_gas_component"), WorldGasComponent.class);
	
	public static void initialize() {
	
	}
	
	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {
		worldComponentFactoryRegistry.register(WORLD_GAS_COMPONENT, WorldGasComponent::new);
	}
}
