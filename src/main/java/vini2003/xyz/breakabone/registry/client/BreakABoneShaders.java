package vini2003.xyz.breakabone.registry.client;

import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

public class BreakABoneShaders {
	private static final ManagedShaderEffect BLUR_SHADER =
			ShaderEffectManager
					.getInstance()
					.manage(new Identifier("shaders/post/blur.json"));
	
	public static boolean enableBlur = true;
	
	public static void initialize() {
		ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
			if (enableBlur) {
				BLUR_SHADER.render(tickDelta);
			}
		});
	}
}
