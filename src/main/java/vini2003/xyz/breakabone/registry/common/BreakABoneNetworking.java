package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import vini2003.xyz.breakabone.BreakABone;

public class BreakABoneNetworking {
	public static final Identifier OPENED_BODY_PARTY_SELECTOR = BreakABone.identifier("body_part_selector");
	
	public static void initialize() {
		ServerPlayNetworking.registerGlobalReceiver(OPENED_BODY_PARTY_SELECTOR, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
			serverPlayerEntity.openHandledScreen(BreakABoneScreenHandlers.BODY_PART_FACTORY);
		}));
	}
}
