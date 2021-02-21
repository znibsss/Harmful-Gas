package vini2003.xyz.breakabone.registry.client;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import vini2003.xyz.breakabone.client.screen.BodyPartScreen;
import vini2003.xyz.breakabone.registry.common.BreakABoneScreenHandlers;

public class BreakABoneScreens {
	public static void initialize() {
		ScreenRegistry.register(BreakABoneScreenHandlers.BODY_PART, BodyPartScreen::new);
	}
}
