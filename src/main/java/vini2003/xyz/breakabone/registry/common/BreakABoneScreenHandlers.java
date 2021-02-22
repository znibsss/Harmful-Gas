package vini2003.xyz.breakabone.registry.common;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.BreakABone;
import vini2003.xyz.breakabone.common.screenhandler.BodyPartSelectorScreenHandler;

public class BreakABoneScreenHandlers {
	public static final ScreenHandlerType<BodyPartSelectorScreenHandler> BODY_PART_SELECTOR = ScreenHandlerRegistry.registerExtended(BreakABone.identifier("body_part_selector"), ((syncId, inventory, buffer) -> {
		return new BodyPartSelectorScreenHandler(syncId, inventory.player);
	}));
	
	public static final ExtendedScreenHandlerFactory BODY_PART_FACTORY = new ExtendedScreenHandlerFactory() {
		@Override
		public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
		
		}
		
		@Override
		public Text getDisplayName() {
			return LiteralText.EMPTY;
		}
		
		@Override
		public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
			return new BodyPartSelectorScreenHandler(syncId, player);
		}
	};
	
	public static void initialize() {
	
	}
}
