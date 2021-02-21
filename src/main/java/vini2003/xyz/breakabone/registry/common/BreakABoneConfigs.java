package vini2003.xyz.breakabone.registry.common;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.util.ActionResult;
import vini2003.xyz.breakabone.common.config.BreakABoneConfig;

public class BreakABoneConfigs {
	public static void initialize() {
		AutoConfig.register(BreakABoneConfig.class, GsonConfigSerializer::new);
		
		AutoConfig.getConfigHolder(BreakABoneConfig.class).registerSaveListener((manager, newCache) -> {
			BreakABoneConfig.cache = newCache;
			return ActionResult.SUCCESS;
		});
		
		BreakABoneConfig.refresh();
	}
}
