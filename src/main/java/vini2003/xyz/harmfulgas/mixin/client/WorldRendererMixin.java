package vini2003.xyz.harmfulgas.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.harmfulgas.client.particle.GasParticle;
import vini2003.xyz.harmfulgas.client.particle.RenderPhaseAccessor;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow @Nullable private Framebuffer particlesFramebuffer;
	
	@Shadow @Nullable public abstract Framebuffer getParticlesFramebuffer();
	
	@Shadow @Final private MinecraftClient client;
	
	@Inject(at = @At(value = "HEAD", target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0),
			method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V")
	void harmfulgas_render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
		GasParticle.DRAW_PARTICLES = true;
		MinecraftClient.getInstance().particleManager.renderParticles(matrices, MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), lightmapTextureManager, camera, tickDelta);
		GasParticle.DRAW_PARTICLES = false;
	}
}
