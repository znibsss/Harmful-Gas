package vini2003.xyz.harmfulgas.registry.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import vini2003.xyz.harmfulgas.HarmfulGasClient;

import static net.minecraft.util.math.MathHelper.clamp;

public class HarmfulGasCallbacks {
	public static void initialize() {
		WorldRenderEvents.START.register((context) -> {
			if (HarmfulGasClient.isNearGasCloud) {
				HarmfulGasShaders.enableBlur = true;
				
				HarmfulGasShaders.blurModifierStrength = clamp(HarmfulGasShaders.blurModifierStrength * 0.99F, 0F, 1F);
			} else {
				HarmfulGasShaders.enableBlur = false;
			}
			
			HarmfulGasShaders.BLUR_SHADER.setUniformValue("ModifierStrength", HarmfulGasShaders.blurModifierStrength);
		});
	}
}
