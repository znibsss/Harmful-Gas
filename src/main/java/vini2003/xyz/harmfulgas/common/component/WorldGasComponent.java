package vini2003.xyz.harmfulgas.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vini2003.xyz.harmfulgas.common.utilities.BlockPosUtilities;
import vini2003.xyz.harmfulgas.common.utilities.DirectionUtilities;
import vini2003.xyz.harmfulgas.common.utilities.GasUtilities;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasComponents;

import java.util.*;

public final class WorldGasComponent implements Component, ServerTickingComponent {
	private final Set<BlockPos> nodes = new HashSet<>();
	private final List<BlockPos> nodesIndexed = new ArrayList<>();
	private final Set<BlockPos> nodesToAdd = new HashSet<>();
	
	private final Map<UUID, Set<BlockPos>> particles = new HashMap<>();
	
	private final Map<UUID, Integer> cooldowns = new HashMap<>();
	
	private final World world;
	
	private BlockPos originPos = new BlockPos(0, 0, 0);
	
	private boolean paused;
	
	private long age;
	
	private int progress;
	
	private int speed;
	
	public WorldGasComponent(World world) {
		this.world = world;
		this.paused = false;
		this.age = 0;
		this.progress = 0;
		this.speed = 20;
	}
	
	public void add(BlockPos pos) {
		nodesToAdd.add(pos);
	}
	
	@Override
	public void serverTick() {
		if (world == null)
			return;
		
		if (!isPaused()) {
			++age;
			
			int start = nodesIndexed.size() / speed * progress;
			
			int end = Math.min(Math.max(256, start + nodesIndexed.size() / speed), nodesIndexed.size());
			
			for (int i = start; i < end; ++i) {
				BlockPos pos = nodesIndexed.get(i);
				
				double maxDist = 64F + age / (375.0F / (21 - speed));
				
				double posDist = BlockPosUtilities.minimumSquaredDistance(world.getPlayers(), pos);
				
				for (Direction direction : DirectionUtilities.DIRECTIONS) {
					BlockPos sidePos = pos.offset(direction);
					
					double sidePosDist = BlockPosUtilities.minimumSquaredDistance(world.getPlayers(), sidePos);
					
					if (sidePos.getY() < 90
							&& sidePos.getY() > 48
							&& !nodes.contains(sidePos)
							&& ((age % 600 == 0 && sidePosDist < posDist) || (age % speed == 0 && sidePos.isWithinDistance(originPos, maxDist)))
							&& sidePos.getY() < world.getTopPosition(Heightmap.Type.WORLD_SURFACE, sidePos).getY() + 2) {
						BlockState sideState = world.getBlockState(sidePos);
						BlockState centerState = world.getBlockState(pos);
						
						if (GasUtilities.isTraversableForPropagation(world, centerState, pos, sideState, sidePos, direction)) {
							nodesToAdd.add(sidePos);
						}
					}
				}
				
				for (PlayerEntity player : world.getPlayers()) {
					double distance = BlockPosUtilities.squaredDistance(player, pos);
					
					cooldowns.putIfAbsent(player.getUuid(), 150);
					
					if (distance < 1.0D && cooldowns.get(player.getUuid()) == 0) {
						player.damage(DamageSource.DROWN, 1.0F);
					}
					
					if (pos.getX() % 3 == 0 && pos.getZ() % 3 == 0) {
						particles.putIfAbsent(player.getUuid(), new HashSet<>());
						
						if (!particles.get(player.getUuid()).contains(pos)) {
							HarmfulGasNetworking.sendAddGasCloudPacket(player, pos);
							
							particles.get(player.getUuid()).add(pos);
						}
					}
				}
			}
			
			for (PlayerEntity player : world.getPlayers()) {
				cooldowns.putIfAbsent(player.getUuid(), 150);
				
				if (cooldowns.get(player.getUuid()) > 0) {
					cooldowns.put(player.getUuid(), cooldowns.get(player.getUuid()) - 1);
				}
			}
			
			nodes.addAll(nodesToAdd);
			nodesIndexed.addAll(nodesToAdd);
			
			nodesToAdd.clear();
			
			++progress;
			
			if (progress >= speed) {
				progress = 0;
			}
		}
	}
	
	@Override
	public void writeToNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = new CompoundTag();
		
		dataTag.putInt("OriginX", this.originPos.getX());
		dataTag.putInt("OriginY", this.originPos.getY());
		dataTag.putInt("OriginZ", this.originPos.getZ());
		
		dataTag.putBoolean("Paused", this.paused);
		
		dataTag.putLong("Age", this.age);
		
		dataTag.putInt("Progress", this.progress);
		dataTag.putInt("Speed", this.speed);
		
		int i = 0;
		
		for (BlockPos pos : nodes) {
			CompoundTag pointTag = new CompoundTag();
			pointTag.putLong("Pos", pos.asLong());
			
			dataTag.put(String.valueOf(i), pointTag);
			++i;
		}
		
		tag.put("Data", dataTag);
	}
	
	@Override
	public void readFromNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = tag.getCompound("Data");
		
		this.originPos = new BlockPos(
				dataTag.getInt("OriginX"),
				dataTag.getInt("OriginY"),
				dataTag.getInt("OriginZ")
		);
		
		this.paused = dataTag.getBoolean("Paused");
		
		this.age = dataTag.getLong("Age");
		
		this.progress = dataTag.getInt("Progress");
		this.speed = dataTag.getInt("Speed");
		
		for (String key : dataTag.getKeys()) {
			CompoundTag pointTag = dataTag.getCompound(key);
			
			this.nodesToAdd.add(BlockPos.fromLong(pointTag.getLong("Pos")));
		}
	}
	
	@Nullable
	public static <V> WorldGasComponent get(V v) {
		try {
			return HarmfulGasComponents.WORLD_GAS_COMPONENT.get(v);
		} catch (Exception justShutUpAlready) {
			return null;
		}
	}
	
	public Map<UUID, Set<BlockPos>> getParticles() {
		return particles;
	}
	
	public Set<BlockPos> getParticles(UUID uuid) {
		return particles.get(uuid);
	}
	
	public Map<UUID, Integer> getCooldowns() {
		return cooldowns;
	}
	
	public long getAge() {
		return age;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public BlockPos getOriginPos() {
		return originPos;
	}
	
	public void setOriginPos(BlockPos originPos) {
		this.originPos = originPos;
	}
}