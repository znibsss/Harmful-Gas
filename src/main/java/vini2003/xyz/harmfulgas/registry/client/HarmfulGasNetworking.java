package vini2003.xyz.harmfulgas.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import vini2003.xyz.harmfulgas.HarmfulGas;
import vini2003.xyz.harmfulgas.client.util.GasParticleUtils;

public class HarmfulGasNetworking {
	public static final Identifier ADD_GAS_CLOUD = HarmfulGas.identifier("add_gas_cloud");
	
	public static final Identifier REFRESH_GAS_CLOUD = HarmfulGas.identifier("refresh_gas_cloud");
	
	private static void receiveAddGasCloudPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		if (client.world != null) {
			client.world.addParticle(HarmfulGasParticleTypes.GAS, buf.readInt(), buf.readInt(), buf.readInt(), 0D, 0D, 0D);
		}
	}
	
	private static void receiveRefreshGasCloudPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		GasParticleUtils.shouldClear = true;
	}
	
	public static void sendAddGasCloudPacket(PlayerEntity player, BlockPos pos) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.ADD_GAS_CLOUD, buf);
	}
	
	public static void sendRefreshGasCloudPacket(PlayerEntity player) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		
		ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.REFRESH_GAS_CLOUD, buf);
	}
	
	public static void initialize() {
		ClientPlayNetworking.registerGlobalReceiver(ADD_GAS_CLOUD, HarmfulGasNetworking::receiveAddGasCloudPacket);
		
		ClientPlayNetworking.registerGlobalReceiver(REFRESH_GAS_CLOUD, HarmfulGasNetworking::receiveRefreshGasCloudPacket);
	}
}
