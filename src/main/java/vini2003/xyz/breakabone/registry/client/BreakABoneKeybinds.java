package vini2003.xyz.breakabone.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import vini2003.xyz.breakabone.registry.common.BreakABoneNetworking;

public class BreakABoneKeybinds {
	public static final KeyBinding keyBodyPartScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.breakabone.open_body_part_selector", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.breakabone.breakabone"));
	
	public static void initialize() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (keyBodyPartScreen.wasPressed()) {
				ClientPlayNetworking.send(BreakABoneNetworking.OPENED_BODY_PARTY_SELECTOR, new PacketByteBuf(Unpooled.buffer()));
			}
		});
	}
}
