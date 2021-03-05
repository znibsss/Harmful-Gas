package vini2003.xyz.harmfulgas.mixin.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasTextureSheets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
	@Shadow @Mutable
	private static List<ParticleTextureSheet> PARTICLE_TEXTURE_SHEETS;
	
	static {
		List<ParticleTextureSheet> newSheets = new ArrayList<>();
		
		newSheets.addAll(PARTICLE_TEXTURE_SHEETS);
		
		newSheets.add(HarmfulGasTextureSheets.GAS);
		
		PARTICLE_TEXTURE_SHEETS = newSheets;
	}
}
