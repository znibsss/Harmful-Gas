package vini2003.xyz.breakabone.common.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "blackhole")
public class BreakABoneConfig implements ConfigData {
	@ConfigEntry.Gui.Excluded
	public static BreakABoneConfig cache;
	
	public static void refresh() {
		cache = AutoConfig.getConfigHolder(BreakABoneConfig.class).getConfig();
	}
}
