package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import vini2003.xyz.breakabone.BreakABone;
import vini2003.xyz.breakabone.common.screenhandler.BodyPartScreenHandler;

public class BreakABoneScreenHandlers {
	public static final ScreenHandlerType<BodyPartScreenHandler> BODY_PART = ScreenHandlerRegistry.registerExtended(BreakABone.identifier("body_part"), ((syncId, inventory, buffer) -> {
		return new BodyPartScreenHandler(syncId, inventory.player);
	}));
	
	public static void initialize() {
	
	}
}
