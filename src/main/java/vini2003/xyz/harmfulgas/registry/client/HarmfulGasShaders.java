package vini2003.xyz.harmfulgas.registry.client;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

public class HarmfulGasShaders {
	public static final ManagedShaderEffect BLUR_SHADER =
			ShaderEffectManager
					.getInstance()
					.manage(new Identifier("shaders/post/circular_blur.json"));
	
	public static boolean enableBlur = true;
	
	public static float blurModifierStrength = 0.0F;
	
	public static void initialize() {
		ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
			if (enableBlur) {
				BLUR_SHADER.render(tickDelta);
			}
		});
	}
}
