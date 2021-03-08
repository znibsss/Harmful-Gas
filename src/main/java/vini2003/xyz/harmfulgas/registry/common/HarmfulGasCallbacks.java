package vini2003.xyz.harmfulgas.registry.common;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasCallbacks {
	public static void initialize() {
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(((player, origin, destination) -> {
			WorldGasComponent gasComponent = WorldGasComponent.get(origin);
			
			gasComponent.getNodeParticles().remove(player.getUuid());
			gasComponent.getCooldowns().put(player.getUuid(), 150);
		}));
		
		ServerPlayerEvents.AFTER_RESPAWN.register(((oldPlayer, newPlayer, alive) -> {
			WorldGasComponent gasComponent = WorldGasComponent.get(newPlayer.getServerWorld());
			
			gasComponent.getNodeParticles().remove(newPlayer.getUuid());
			gasComponent.getCooldowns().put(newPlayer.getUuid(), 150);
		}));
	}
}
