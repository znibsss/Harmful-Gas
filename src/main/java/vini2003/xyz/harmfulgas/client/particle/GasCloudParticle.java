package vini2003.xyz.harmfulgas.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.client.utilities.ClientUtilities;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

import static vini2003.xyz.harmfulgas.client.utilities.GasParticleUtilities.rotatedVertices;

public class GasCloudParticle extends SpriteBillboardParticle {
	public GasCloudParticle(ClientWorld clientWorld, double x, double y, double z) {
		super(clientWorld, x, y, z);
	}
	
	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		if (colorAlpha < 0.25F) {
			colorAlpha += 0.0005F;
		} else {
			colorAlpha = 0.25F;
		}
		
		if (ClientUtilities.getPlayer().squaredDistanceTo(x, y, z) > 192.0D * 192.0D) {
			return;
		}
		
		float cX = (float) (this.x - camera.getPos().getX());
		float cY = (float) (this.y - camera.getPos().getY());
		float cZ = (float) (this.z - camera.getPos().getZ());
		
		vertexConsumer.vertex(rotatedVertices[0].getX() + cX, rotatedVertices[0].getY() + cY, rotatedVertices[0].getZ() + cZ).texture(getMaxU(), getMaxV()).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(15728880).next();
		vertexConsumer.vertex(rotatedVertices[1].getX() + cX, rotatedVertices[1].getY() + cY, rotatedVertices[1].getZ() + cZ).texture(getMaxU(), getMinV()).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(15728880).next();
		vertexConsumer.vertex(rotatedVertices[2].getX() + cX, rotatedVertices[2].getY() + cY, rotatedVertices[2].getZ() + cZ).texture(getMinU(), getMinV()).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(15728880).next();
		vertexConsumer.vertex(rotatedVertices[3].getX() + cX, rotatedVertices[3].getY() + cY, rotatedVertices[3].getZ() + cZ).texture(getMinU(), getMaxV()).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(15728880).next();
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
			gasCloudParticle.sprite = spriteProvider.getSprite(gasCloudParticle.random);
			gasCloudParticle.maxAge = Integer.MAX_VALUE;
			gasCloudParticle.scale = 6F;
			gasCloudParticle.colorAlpha = 0F;
			
			return gasCloudParticle;
		}
	}
}
