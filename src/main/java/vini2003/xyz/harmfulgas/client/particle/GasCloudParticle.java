package vini2003.xyz.harmfulgas.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

public class GasCloudParticle extends SpriteBillboardParticle {
	public static boolean SHOW = false;
	
	public static boolean DRAW_PARTICLES = false;
	
	public GasCloudParticle(ClientWorld clientWorld, double x, double y, double z) {
		super(clientWorld, x, y, z);
	}
	
	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		this.angle += tickDelta * 10F;
		super.buildGeometry(vertexConsumer, camera, tickDelta);
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
			gasCloudParticle.maxAge = Integer.MAX_VALUE;
			gasCloudParticle.scale = 4F;
			gasCloudParticle.colorAlpha = 0.25F;
			return gasCloudParticle;
		}
	}
}
