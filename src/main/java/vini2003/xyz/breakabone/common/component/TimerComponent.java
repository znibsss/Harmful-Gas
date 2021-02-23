package vini2003.xyz.breakabone.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import static java.lang.System.nanoTime;

public class TimerComponent implements PlayerComponent<TimerComponent>, AutoSyncedComponent {
	private long origin;
	
	private PlayerEntity player;
	
	public TimerComponent(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void readFromNbt(CompoundTag tag) {
		origin = tag.getLong("Origin");
	}
	
	@Override
	public void writeToNbt(CompoundTag tag) {
		tag.putLong("Origin", origin);
	}
	
	public void reset() {
		origin = nanoTime();
	}
	
	public boolean hasNanoseconds(long nanoseconds) {
		return nanoTime() - origin > nanoseconds;
	}
	
	public boolean hasMilliseconds(long milliseconds) {
		return hasNanoseconds(milliseconds * 1_000L);
	}
	
	public boolean hasSeconds(long seconds) {
		return hasMilliseconds(seconds * 1_000L);
	}
	
	public boolean hasMinutes(long minutes) {
		return hasSeconds(minutes * 60);
	}
	
	public boolean hasHours(long hours) {
		return hasMinutes(hours * 60);
	}
	
	public boolean hasDays(long days) {
		return hasHours(days * 24);
	}
}
