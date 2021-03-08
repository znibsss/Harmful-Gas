package vini2003.xyz.harmfulgas;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasCallbacks;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasCommands;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasComponents;

public class HarmfulGas implements ModInitializer {
	public static final String ID = "harmfulgas";
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		HarmfulGasCommands.initialize();
		HarmfulGasComponents.initialize();
		HarmfulGasCallbacks.initialize();
	}
}
