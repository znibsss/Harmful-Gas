package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import vini2003.xyz.harmfulgas.HarmfulGasClient;

import static net.minecraft.util.math.MathHelper.clamp;

public class HarmfulGasCallbacks {
	public static void initialize() {
		WorldRenderEvents.START.register((context) -> {
			HarmfulGasShaders.enableBlur = true;
			
			if (HarmfulGasClient.isNearGasCloud) {
				HarmfulGasShaders.blurModifierStrength = clamp(HarmfulGasShaders.blurModifierStrength += 0.0025F, 0.0001F, 1F);
			} else {
				HarmfulGasShaders.blurModifierStrength = clamp(HarmfulGasShaders.blurModifierStrength -= 0.005F, 0.0001F, 1F);
			}
			
			HarmfulGasShaders.BLUR_SHADER.setUniformValue("ModifierStrength", HarmfulGasShaders.blurModifierStrength);
		});
	}
}
