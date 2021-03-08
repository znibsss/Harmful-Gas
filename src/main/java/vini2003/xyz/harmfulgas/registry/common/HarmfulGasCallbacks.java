package vini2003.xyz.harmfulgas.registry.common;

import me.shedaniel.cloth.api.common.events.v1.PlayerJoinCallback;
import me.shedaniel.cloth.api.common.events.v1.PlayerLeaveCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasCallbacks {
	private static void playerChangeWorld(PlayerEntity player, World origin, World destination) {
		WorldGasComponent gasComponent = WorldGasComponent.get(origin);
		
		gasComponent.getParticles().remove(player.getUuid());
		gasComponent.getCooldowns().put(player.getUuid(), 150);
	}
	
	private static void playerRespawn(PlayerEntity oldPlayer, PlayerEntity newPlayer, boolean alive) {
		WorldGasComponent gasComponent = WorldGasComponent.get(newPlayer.getEntityWorld());
		
		gasComponent.getParticles().remove(newPlayer.getUuid());
		gasComponent.getCooldowns().put(newPlayer.getUuid(), 150);
	}
	
	private static void playerLeave(PlayerEntity player) {
		WorldGasComponent gasComponent = WorldGasComponent.get(player.getEntityWorld());
		
		gasComponent.getParticles().remove(player.getUuid());
	}
	
	public static void initialize() {
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(HarmfulGasCallbacks::playerChangeWorld);
		
		ServerPlayerEvents.AFTER_RESPAWN.register(HarmfulGasCallbacks::playerRespawn);
		
		PlayerLeaveCallback.EVENT.register(HarmfulGasCallbacks::playerLeave);
	}
}
