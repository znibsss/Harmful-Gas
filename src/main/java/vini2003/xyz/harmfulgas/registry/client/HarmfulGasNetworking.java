package vini2003.xyz.harmfulgas.registry.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import vini2003.xyz.harmfulgas.HarmfulGas;

public class HarmfulGasNetworking {
	public static final Identifier ADD_GAS_CLOUD = HarmfulGas.identifier("add_gas_cloud");
	
	public static void initialize() {
		ClientPlayNetworking.registerGlobalReceiver(ADD_GAS_CLOUD, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
			minecraftClient.world.addParticle(HarmfulGasParticleTypes.GAS, packetByteBuf.readInt(), packetByteBuf.readInt(), packetByteBuf.readInt(), 0D, 0D, 0D);
		}));
	}
	
	public static void setAddGasCloudPacket(PlayerEntity player, BlockPos pos) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		ServerPlayNetworking.send((ServerPlayerEntity) player, HarmfulGasNetworking.ADD_GAS_CLOUD, buf);
	}
}
