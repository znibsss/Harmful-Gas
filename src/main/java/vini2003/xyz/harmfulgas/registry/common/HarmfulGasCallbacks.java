package vini2003.xyz.harmfulgas.registry.common;

import me.shedaniel.cloth.api.common.events.v1.PlayerChangeWorldCallback;
import me.shedaniel.cloth.api.utils.v1.GameInstanceUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import vini2003.xyz.harmfulgas.common.component.WorldGasComponent;

public class HarmfulGasCallbacks {
	public static void initialize() {
		ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(((player, origin, destination) -> {
			WorldGasComponent gasComponent = WorldGasComponent.get(origin);
			
			gasComponent.getNodeParticles().remove(player.getUuid());
		}));
	}
}
