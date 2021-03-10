package vini2003.xyz.harmfulgas.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientUtils {
	public static ClientPlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}
