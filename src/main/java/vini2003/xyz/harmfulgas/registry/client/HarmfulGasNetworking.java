	package vini2003.xyz.harmfulgas.registry.client;
	
	import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
	import net.minecraft.util.Identifier;
	import vini2003.xyz.harmfulgas.HarmfulGas;
	import vini2003.xyz.harmfulgas.HarmfulGasClient;
	
	public class HarmfulGasNetworking {
		public static final Identifier NEAR_GAS_CLOUD = HarmfulGas.identifier("near_gas_cloud");
		
		public static final Identifier ADD_GAS_CLOUD = HarmfulGas.identifier("add_gas_cloud");
		
		public static void initialize() {
			ClientPlayNetworking.registerGlobalReceiver(NEAR_GAS_CLOUD, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				HarmfulGasClient.isNearGasCloud = packetByteBuf.readBoolean();
			}));
			
			ClientPlayNetworking.registerGlobalReceiver(ADD_GAS_CLOUD, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				minecraftClient.world.addParticle(HarmfulGasParticleTypes.GAS, packetByteBuf.readInt(), packetByteBuf.readInt(), packetByteBuf.readInt(), 0D, 0D, 0D);
			}));
		}
	}
