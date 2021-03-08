package vini2003.xyz.harmfulgas.client.utilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;

public class ClientUtilities {
	public static ClientPlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
	
	public static Tessellator getTessellator() {
		return Tessellator.getInstance();
	}
	
	public static TextureManager getTextureManager() {
		return MinecraftClient.getInstance().getTextureManager();
	}
	
	public static ParticleManager getParticleManager() {
		return MinecraftClient.getInstance().particleManager;
	}
}
