package vini2003.xyz.breakabone;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import vini2003.xyz.breakabone.registry.common.*;

public class BreakABone implements ModInitializer {
	public static final String ID = "breakabone";
	
	public static Identifier identifier(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		BreakABoneCommands.initialize();
		BreakABoneCallbacks.initialize();
		BreakABoneComponents.initialize();
		BreakABoneScreenHandlers.initialize();
	}
}
