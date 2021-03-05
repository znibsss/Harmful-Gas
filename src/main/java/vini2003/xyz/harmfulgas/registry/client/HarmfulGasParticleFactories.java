package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.client.particle.GasParticle;

import java.util.Random;

public class HarmfulGasParticleFactories {
	public static void initialize() {
        ParticleFactoryRegistry.getInstance().register(HarmfulGasParticleTypes.GAS, GasParticle.Factory::new);
	}
}
