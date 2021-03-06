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

import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;
import vini2003.xyz.harmfulgas.HarmfulGas;
import vini2003.xyz.harmfulgas.client.utilities.ClientAtmosphereUtilities;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;
import vini2003.xyz.harmfulgas.registry.client.HarmfulGasParticleTypes;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasComponents;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.minecraft.util.math.MathHelper.clamp;

public final class ChunkAtmosphereComponent implements Component, ServerTickingComponent {
	private final Set<BlockPos> volumes = new HashSet<>();
	private final Set<BlockPos> volumesToAdd = new HashSet<>();
	private final Set<BlockPos> volumesToRemove = new HashSet<>();
	
	private final Map<PlayerEntity, Object2IntMap<BlockPos>> particleTimes = new HashMap<>();
	
	private final World world;
	
	private final Chunk chunk;
	
	public ChunkAtmosphereComponent(Chunk chunk) {
		if (chunk instanceof WorldChunk) {
			this.world = ((WorldChunk) chunk).getWorld();
			this.chunk = chunk;
		} else {
			this.world = null;
			this.chunk = null;
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public Chunk getChunk() {
		return chunk;
	}
	
	public Set<BlockPos> getVolumes() {
		return volumes;
	}
	
	public boolean get(BlockPos position) {
		if (world == null)
			return false;
		
		return volumes.contains(position);
	}
	
	public void scheduleRemoval(BlockPos pos) {
		volumesToRemove.add(pos);
	}
	
	public void executeRemovals() {
		volumesToRemove.forEach(volumes::remove);
		
		volumesToRemove.clear();
	}
	
	public void scheduleAddition(BlockPos pos) {
		volumesToAdd.add(pos);
	}
	
	public void executeAdditions() {
		volumesToAdd.forEach(volumes::add);
		
		volumesToAdd.clear();
	}
	
	public void executeParticles() {
		volumes.forEach(pos -> {
			if (pos.getX() % 4 == 0 && pos.getZ() % 4 == 0) {
				for (PlayerEntity player : world.getPlayers()) {
					particleTimes.putIfAbsent(player, new Object2IntArrayMap<>());
					
					int time = particleTimes.get(player).getOrDefault(pos, 0);
					
					if (time == 0) {
						PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
						
						buf.writeInt(pos.getX());
						buf.writeInt(pos.getY());
						buf.writeInt(pos.getZ());
						
						ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.PARTICLE_ADDED, buf);
						
						time = 600;
					} else {
						--time;
					}
					
					particleTimes.get(player).put(pos, time);
				}
			}
		});
	}
	
	public static boolean isInChunk(ChunkPos chunkPos, BlockPos pos) {
		return pos.getX() >= chunkPos.getStartX() && pos.getX() < chunkPos.getEndX() && pos.getZ() >= chunkPos.getStartZ() && pos.getZ() < chunkPos.getEndZ();
	}
	
	public static ChunkPos getNeighborFromPos(ChunkPos chunkPos, BlockPos pos) {
		if (pos.getX() < chunkPos.getStartX()) {
			return new ChunkPos(chunkPos.x - 1, chunkPos.z);
		} else if (pos.getX() > chunkPos.getEndX()) {
			return new ChunkPos(chunkPos.x + 1, chunkPos.z);
		} else if (pos.getZ() < chunkPos.getStartZ()) {
			return new ChunkPos(chunkPos.x, chunkPos.z - 1);
		} else if (pos.getZ() > chunkPos.getEndZ()) {
			return new ChunkPos(chunkPos.x, chunkPos.z + 1);
		}
		return chunkPos;
	}
	
	public ChunkPos getNeighborFromPos(BlockPos pos) {
		if (world == null) return new ChunkPos(0, 0);
		
		return getNeighborFromPos(chunk.getPos(), pos);
	}
	
	public boolean isInChunk(BlockPos pos) {
		if (world == null) return false;
		
		return isInChunk(chunk.getPos(), pos);
	}
	
	public boolean isTraversableForPropagation(BlockState centerState, BlockPos centerPos, BlockState sideState, BlockPos sidePos, Direction direction) {
		if (world == null) return false;
		
		return sideState.getFluidState().isEmpty() &&
				!(Registry.BLOCK.getId(sideState.getBlock()).toString().equals("astromine:airlock") && !sideState.get(Properties.POWERED))
				&& (sideState.isAir() || !sideState.isSideSolidFullSquare(world, sidePos, direction.getOpposite()))
				&& (centerState.isAir() || !centerState.isSideSolidFullSquare(world, centerPos, direction)
				&& (!sideState.isOpaqueFullCube(world, centerPos)));
	}
	
	@Override
	public void serverTick() {
		if (world == null)
			return;
		
		if ((world.isChunkLoaded(chunk.getPos().x, chunk.getPos().z))) {
			main:
			for (BlockPos centerPos : volumes) {
				for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN}) {
					BlockPos sidePos = centerPos.offset(direction);
					
					if (!volumes.contains(sidePos) && sidePos.getY() < 81) {
						if (isInChunk(sidePos)) {
							BlockState sideState = world.getBlockState(sidePos);
							BlockState centerState = world.getBlockState(centerPos);
							
							if (isTraversableForPropagation(centerState, centerPos, sideState, sidePos, direction)) {
								scheduleAddition(sidePos);
								
								if (direction == Direction.WEST) {
									break;
								}
							}
						} else {
							ChunkPos neighborPos = getNeighborFromPos(sidePos);
							
							ChunkAtmosphereComponent chunkAtmosphereComponent = HarmfulGasComponents.CHUNK_ATMOSPHERE_COMPONENT.get(world.getChunk(neighborPos.x, neighborPos.z));
							
							BlockState sideState = world.getBlockState(sidePos);
							BlockState centerState = world.getBlockState(centerPos);
							
							if (isTraversableForPropagation(centerState, centerPos, sideState, sidePos, direction)) {
								chunkAtmosphereComponent.scheduleAddition(sidePos);
								
								if (direction == Direction.WEST) {
									break;
								}
							}
						}
					}
				}
			}
			
			executeAdditions();
			executeRemovals();
			executeParticles();
		}
	}
	
	/**
	 * Serializes this {@link ChunkAtmosphereComponent} to a {@link CompoundTag}.
	 */
	@Override
	public void writeToNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = new CompoundTag();
		
		int i = 0;
		
		for (BlockPos pos : volumes) {
			CompoundTag pointTag = new CompoundTag();
			pointTag.putLong("pos", pos.asLong());
			
			dataTag.put(String.valueOf(i), pointTag);
			++i;
		}
		
		tag.put("data", dataTag);
	}
	
	/**
	 * Deserializes this {@link ChunkAtmosphereComponent} from a {@link CompoundTag}.
	 */
	@Override
	public void readFromNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = tag.getCompound("data");
		
		for (String key : dataTag.getKeys()) {
			CompoundTag pointTag = dataTag.getCompound(key);
			
			volumes.add(BlockPos.fromLong(pointTag.getLong("pos")));
		}
	}
	
	/**
	 * Returns the {@link ChunkAtmosphereComponent} of the given {@link V}.
	 */
	@Nullable
	public static <V> ChunkAtmosphereComponent get(V v) {
		try {
			return HarmfulGasComponents.CHUNK_ATMOSPHERE_COMPONENT.get(v);
		} catch (Exception justShutUpAlready) {
			return null;
		}
	}
}