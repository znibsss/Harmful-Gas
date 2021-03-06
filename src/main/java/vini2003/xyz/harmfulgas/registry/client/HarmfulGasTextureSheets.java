package vini2003.xyz.harmfulgas.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class HarmfulGasTextureSheets {
	public static final ParticleTextureSheet GAS = new ParticleTextureSheet() {
		public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
			textureManager.bindTexture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
			RenderSystem.enableBlend();
			RenderSystem.disableCull();
			RenderSystem.depthMask(false);
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.alphaFunc(516, 0.003921569F);
			bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
		}
		
		public void draw(Tessellator tessellator) {
			tessellator.draw();
		}
	};
}
