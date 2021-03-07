package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3f;
import vini2003.xyz.harmfulgas.HarmfulGasClient;

import static net.minecraft.util.math.MathHelper.clamp;

public class HarmfulGasCallbacks {
	public static void initialize() {
		WorldRenderEvents.START.register((context) -> {
			Vector3f[] rotatedVertices = new Vector3f[] {
					new Vector3f(-4.0F, -4.0F, 0.0F),
					new Vector3f(-4.0F, 4.0F, 0.0F),
					new Vector3f(4.0F, 4.0F, 0.0F),
					new Vector3f(4.0F, -4.0F, 0.0F)
			};
			
			for (int i = 0; i < 4; ++i) {
				Vector3f vertex = rotatedVertices[i];
				
				vertex.rotate(context.camera().getRotation());
			}
			
			HarmfulGasClient.rotatedVertices = rotatedVertices;
		});
	}
}
