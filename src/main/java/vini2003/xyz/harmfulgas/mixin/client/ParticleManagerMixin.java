package vini2003.xyz.harmfulgas.mixin.client;

import com.google.common.collect.EvictingQueue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

import java.util.*;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
	@Final
	@Shadow
	@Mutable
	private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;
	
	@Redirect(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/EvictingQueue;create(I)Lcom/google/common/collect/EvictingQueue;"), method = "method_18125")
	private static <E> EvictingQueue<E> harmfulgas_tick(int maxSize) {
		return EvictingQueue.create(131072);
	}
	
	static {
		List<ParticleTextureSheet> newSheets = new ArrayList<>();
		
		newSheets.addAll(PARTICLE_TEXTURE_SHEETS);
		
		newSheets.add(HarmfulGasTextureSheets.GAS);
		
		PARTICLE_TEXTURE_SHEETS = newSheets;
	}
}
