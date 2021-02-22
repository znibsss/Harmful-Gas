package vini2003.xyz.breakabone.registry.client;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import vini2003.xyz.breakabone.client.screen.BodyPartSelectorScreen;
import vini2003.xyz.breakabone.registry.common.BreakABoneScreenHandlers;

public class BreakABoneScreens {
	public static void initialize() {
		ScreenRegistry.register(BreakABoneScreenHandlers.BODY_PART_SELECTOR, BodyPartSelectorScreen::new);
	}
}
