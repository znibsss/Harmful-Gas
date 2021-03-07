package vini2003.xyz.harmfulgas.common.component;/*
 * MIT License
 *
 * Copyright (c) 2020 Chainmail Studios
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import org.jetbrains.annotations.Nullable;

import vini2003.xyz.harmfulgas.common.utilities.DirectionUtilities;
import vini2003.xyz.harmfulgas.common.utilities.WorldUtilities;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasComponents;

import java.util.*;

public final class WorldGasComponent implements Component, ServerTickingComponent {
	private final Set<BlockPos> nodes = new HashSet<>();
	private final List<BlockPos> nodesIndexed = new ArrayList<>();
	private final Set<BlockPos> nodesToAdd = new HashSet<>();
	private final Set<BlockPos> nodesToRemove = new HashSet<>();
	
	private final Map<PlayerEntity, Set<BlockPos>> nodeParticles = new HashMap<>();
	
	private final World world;
	
	private long age;
	
	private int tick;
	
	public WorldGasComponent(World world) {
		this.world = world;
		this.age = 0;
		this.tick = 0;
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
	
	public Set<BlockPos> getNodesToRemove() {
		return nodesToRemove;
	}
	
	public Map<PlayerEntity, Set<BlockPos>> getNodeParticles() {
		return nodeParticles;
	}
	
	public long getAge() {
		return age;
	}
	
	public void remove(BlockPos pos) {
		nodesToRemove.add(pos);
	}
	
	private void executeRemovals() {
		nodesToRemove.forEach(nodes::remove);
		nodesToRemove.forEach(nodesIndexed::remove);
		
		nodesToRemove.clear();
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
		
		++age;
		
		int start = nodesIndexed.size() / 20 * tick;
		
		int end = Math.min(Math.max(256, start + nodesIndexed.size() / 20), nodesIndexed.size());
		
		for (int i = start; i < end; ++i) {
			BlockPos pos = nodesIndexed.get(i);
			
			for (Direction direction : DirectionUtilities.DIRECTIONS) {
				BlockPos sidePos = pos.offset(direction);
				
				if (!nodes.contains(sidePos) && sidePos.isWithinDistance(((ServerWorld) world).getSpawnPos(), 192) && sidePos.getY() < world.getTopPosition(Heightmap.Type.WORLD_SURFACE, sidePos).getY() + 2) {
					BlockState sideState = world.getBlockState(sidePos);
					BlockState centerState = world.getBlockState(pos);
					
					if (WorldUtilities.isTraversableForPropagation(world, centerState, pos, sideState, sidePos, direction)) {
						add(sidePos);
					}
				}
			}
			
			for (PlayerEntity player : world.getPlayers()) {
				double distance = player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ());
				
				if (distance < 4.0D * 4.0D) {
					player.damage(DamageSource.DROWN, 1.0F);
				}
				
				if (pos.getX() % 3 == 0 && pos.getZ() % 3 == 0) {
					nodeParticles.putIfAbsent(player, new HashSet<>());
					
					if (!nodeParticles.get(player).contains(pos)) {
						PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
						
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
						
						ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.ADD_GAS_CLOUD, buf);
						
						nodeParticles.get(player).add(pos);
					}
				}
			}
		}
		
		executeAdditions();
		executeRemovals();
		
		++tick;
		
		if (tick == 20) {
			tick = 0;
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
}