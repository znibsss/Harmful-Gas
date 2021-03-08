package vini2003.xyz.harmfulgas.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
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
	
	private final Map<UUID, Set<BlockPos>> nodeParticles = new HashMap<>();
	
	private final Map<UUID, Integer> cooldowns = new HashMap<>();
	
	private final World world;
	
	private boolean paused;
	
	private long age;
	
	private int tick;
	
	private int speed;
	
	public WorldGasComponent(World world) {
		this.world = world;
		this.paused = false;
		this.age = 0;
		this.tick = 0;
		this.speed = 20;
	}
	
	public void add(BlockPos pos) {
		nodesToAdd.add(pos);
	}
	
	private void executeAdditions() {
		nodesToAdd.forEach(nodes::add);
		nodesToAdd.forEach(nodesIndexed::add);
		
		nodesToAdd.clear();
	}
	
	@Override
	public void serverTick() {
		if (world == null)
			return;
		
		if (!isPaused()) {
			++age;
			
			int start = nodesIndexed.size() / speed * tick;
			
			int end = Math.min(Math.max(256, start + nodesIndexed.size() / speed), nodesIndexed.size());
			
			for (int i = start; i < end; ++i) {
				BlockPos pos = nodesIndexed.get(i);
				
				double posDist = BlockPosUtilities.minimumSquaredDistance(world.getPlayers(), pos);
				
				for (Direction direction : DirectionUtilities.DIRECTIONS) {
					BlockPos sidePos = pos.offset(direction);
					
					double sidePosDist = BlockPosUtilities.minimumSquaredDistance(world.getPlayers(), sidePos);
					
					if (!nodes.contains(sidePos)
							&& ((age % 600 == 0 && sidePosDist < posDist) || sidePos.isWithinDistance(((ServerWorld) world).getSpawnPos(), 32F + age / (375.0F / (21 - speed))))
							&& sidePos.getY() < world.getTopPosition(Heightmap.Type.WORLD_SURFACE, sidePos).getY() + 2) {
						BlockState sideState = world.getBlockState(sidePos);
						BlockState centerState = world.getBlockState(pos);
						
						if (GasUtilities.isTraversableForPropagation(world, centerState, pos, sideState, sidePos, direction)) {
							add(sidePos);
						}
					}
				}
				
				for (PlayerEntity player : world.getPlayers()) {
					double distance = BlockPosUtilities.squaredDistance(player, pos);
					
					if (distance < 4.0D * 4.0D && cooldowns.get(player.getUuid()) == 0) {
						player.damage(DamageSource.DROWN, 1.0F);
					}
					
					if (pos.getX() % 3 == 0 && pos.getZ() % 3 == 0) {
						nodeParticles.putIfAbsent(player.getUuid(), new HashSet<>());
						
						if (!nodeParticles.get(player.getUuid()).contains(pos)) {
							HarmfulGasNetworking.setAddGasCloudPacket(player, pos);
							
							nodeParticles.get(player.getUuid()).add(pos);
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
			
			executeAdditions();
			
			++tick;
			
			if (tick >= speed) {
				tick = 0;
			}
		}
	}
	
	@Override
	public void writeToNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = new CompoundTag();
		
		int i = 0;
		
		for (BlockPos pos : nodes) {
			CompoundTag pointTag = new CompoundTag();
			pointTag.putLong("pos", pos.asLong());
			
			dataTag.put(String.valueOf(i), pointTag);
			++i;
		}
		
		tag.put("data", dataTag);
	}
	
	@Override
	public void readFromNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = tag.getCompound("data");
		
		for (String key : dataTag.getKeys()) {
			CompoundTag pointTag = dataTag.getCompound(key);
			
			add(BlockPos.fromLong(pointTag.getLong("pos")));
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
	
	public World getWorld() {
		return world;
	}
	
	public Set<BlockPos> getNodes() {
		return nodes;
	}
	
	public Set<BlockPos> getNodesToAdd() {
		return nodesToAdd;
	}
	
	public Map<UUID, Set<BlockPos>> getNodeParticles() {
		return nodeParticles;
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
}