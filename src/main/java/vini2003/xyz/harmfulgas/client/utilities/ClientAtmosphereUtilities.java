package vini2003.xyz.harmfulgas.client.utilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import io.netty.buffer.Unpooled;
import vini2003.xyz.harmfulgas.HarmfulGas;

import java.util.HashSet;
import java.util.Set;

public class ClientAtmosphereUtilities {
	public static final Identifier GAS_ADDED = HarmfulGas.identifier("gas_added");
	public static final Identifier GAS_REMOVED = HarmfulGas.identifier("gas_removed");
	public static final Identifier GAS_ERASED = HarmfulGas.identifier("gas_erased");
	
	private static final Set<Long> VOLUMES = new HashSet<>();
	
	public static Set<Long> getVolumes() {
		return VOLUMES;
	}
	
	public static PacketByteBuf ofGasErased() {
		return new PacketByteBuf(Unpooled.buffer());
	}
	
	public static PacketByteBuf ofGasAdded(BlockPos gasPosition) {
		CompoundTag gasPayload = new CompoundTag();
		gasPayload.putLong("gasPosition", gasPosition.asLong());
		PacketByteBuf gasBuffer = new PacketByteBuf(Unpooled.buffer());
		gasBuffer.writeCompoundTag(gasPayload);
		return gasBuffer;
	}
	
	public static PacketByteBuf ofGasRemoved(BlockPos gasPosition) {
		CompoundTag gasPayload = new CompoundTag();
		gasPayload.putLong("gasPosition", gasPosition.asLong());
		PacketByteBuf gasBuffer = new PacketByteBuf(Unpooled.buffer());
		gasBuffer.writeCompoundTag(gasPayload);
		return gasBuffer;
	}
	
	public static void onGasErased(PacketByteBuf gasBuffer) {
		VOLUMES.clear();
	}
	
	public static void onGasAdded(PacketByteBuf gasBuffer) {
		CompoundTag gasPayload = gasBuffer.readCompoundTag();
		long gasPosition = gasPayload.getLong("gasPosition");
		VOLUMES.add(gasPosition);
	}
	
	public static void onGasRemoved(PacketByteBuf gasBuffer) {
		CompoundTag gasPayload = gasBuffer.readCompoundTag();
		long gasPosition = gasPayload.getLong("gasPosition");
		VOLUMES.remove(gasPosition);
	}
}