package vini2003.xyz.harmfulgas.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

public class GasParticle extends SpriteBillboardParticle {
	public static boolean SHOW = false;
	
	public static boolean DRAW_PARTICLES = false;
	
	public GasParticle(ClientWorld clientWorld, double x, double y, double z) {
		super(clientWorld, x, y, z);
		maxAge = 600;
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
			GasParticle gasParticle = new GasParticle(world, x, y, z);
			gasParticle.setSprite(spriteProvider);
			gasParticle.scale = 8F;
			gasParticle.colorAlpha = 0.20F;
			return gasParticle;
		}
	}
}
