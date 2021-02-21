package vini2003.xyz.breakabone.client.utilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class BreakABoneClientUtilities {
	public static PlayerEntity getPlayer() {
		return MinecraftClient.getInstance().player;
	}
}
