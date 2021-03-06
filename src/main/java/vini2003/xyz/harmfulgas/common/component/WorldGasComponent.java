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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import org.jetbrains.annotations.Nullable;

import vini2003.xyz.harmfulgas.registry.client.HarmfulGasNetworking;
import vini2003.xyz.harmfulgas.registry.common.HarmfulGasComponents;

import java.util.*;

import static net.minecraft.util.math.MathHelper.clamp;

public final class WorldGasComponent implements Component, ServerTickingComponent {
	private final Set<BlockPos> nodes = new HashSet<>();
	private final Set<BlockPos> nodesToAdd = new HashSet<>();
	private final Set<BlockPos> nodesToRemove = new HashSet<>();
	
	private final Map<PlayerEntity, Set<BlockPos>> nodeParticles = new HashMap<>();
	
	private final World world;
	
	public WorldGasComponent(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}

	public void scheduleRemoval(BlockPos pos) {
		nodesToRemove.add(pos);
	}
	
	public void executeRemovals() {
		nodesToRemove.forEach(nodes::remove);
		
		nodesToRemove.clear();
	}
	
	public void scheduleAddition(BlockPos pos) {
		nodesToAdd.add(pos);
	}
	
	public void executeAdditions() {
		nodesToAdd.forEach(nodes::add);
		
		nodesToAdd.clear();
	}
	
	public void executeParticles() {
		Object2BooleanMap<PlayerEntity> playersNearGasClouds = new Object2BooleanArrayMap<>();
		
		for (PlayerEntity player : world.getPlayers()) {
			playersNearGasClouds.put(player, false);
		}
		
		for (BlockPos pos : nodes) {
			for (PlayerEntity player : world.getPlayers()) {
				if (player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < 8 * 8) {
					playersNearGasClouds.put(player, true);
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
		
		for (PlayerEntity player : world.getPlayers()) {
			PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
			
			buf.writeBoolean(playersNearGasClouds.getBoolean(player));
			
			ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.NEAR_GAS_CLOUD, buf);
		}
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
		
		for (BlockPos centerPos : nodes) {
			for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN, Direction.UP}) {
				BlockPos sidePos = centerPos.offset(direction);
				
				if (sidePos.isWithinDistance(((ServerWorld) world).getSpawnPos(), 128) && !nodes.contains(sidePos) && sidePos.getY() < world.getTopPosition(Heightmap.Type.WORLD_SURFACE, sidePos).getY() + 2) {
					BlockState sideState = world.getBlockState(sidePos);
					BlockState centerState = world.getBlockState(centerPos);
					
					if (isTraversableForPropagation(centerState, centerPos, sideState, sidePos, direction)) {
						scheduleAddition(sidePos);
					}
				}
			}
		}
		
		executeAdditions();
		executeRemovals();
		executeParticles();
	}
	
	/**
	 * Serializes this {@link WorldGasComponent} to a {@link CompoundTag}.
	 */
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
	
	/**
	 * Deserializes this {@link WorldGasComponent} from a {@link CompoundTag}.
	 */
	@Override
	public void readFromNbt(CompoundTag tag) {
		if (world == null)
			return;
		
		CompoundTag dataTag = tag.getCompound("data");
		
		for (String key : dataTag.getKeys()) {
			CompoundTag pointTag = dataTag.getCompound(key);
			
			nodes.add(BlockPos.fromLong(pointTag.getLong("pos")));
		}
	}
	
	/**
	 * Returns the {@link WorldGasComponent} of the given {@link V}.
	 */
	@Nullable
	public static <V> WorldGasComponent get(V v) {
		try {
			return HarmfulGasComponents.WORLD_GAS_COMPONENT.get(v);
		} catch (Exception justShutUpAlready) {
			return null;
		}
	}
}