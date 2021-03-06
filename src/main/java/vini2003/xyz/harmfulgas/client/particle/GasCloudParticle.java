package vini2003.xyz.harmfulgas.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

public class GasCloudParticle extends SpriteBillboardParticle {
	public static boolean SHOW = false;
	
	public static boolean DRAW_PARTICLES = false;
	
	public GasCloudParticle(ClientWorld clientWorld, double x, double y, double z) {
		super(clientWorld, x, y, z);
		maxAge = Integer.MAX_VALUE;
	}
	
	@Override
	public ParticleTextureSheet getType() {
		return HarmfulGasTextureSheets.GAS;
	}
	
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Nullable
		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			GasCloudParticle gasCloudParticle = new GasCloudParticle(world, x, y, z);
			gasCloudParticle.setSprite(spriteProvider);
			gasCloudParticle.scale = 8F;
			gasCloudParticle.colorAlpha = 0.20F;
			return gasCloudParticle;
		}
	}
}
