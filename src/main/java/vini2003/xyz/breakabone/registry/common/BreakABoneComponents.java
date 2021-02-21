package vini2003.xyz.breakabone.registry.common;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import vini2003.xyz.breakabone.BreakABone;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;

public class BreakABoneComponents implements EntityComponentInitializer {
	public static final ComponentKey<BodyPartComponent> BODY_PARTS = ComponentRegistry.getOrCreate(BreakABone.identifier("body_parts"), BodyPartComponent.class);
	
	public static void initialize() {
	
	}
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerForPlayers(BODY_PARTS, BodyPartComponent::new);
	}
}
