	package vini2003.xyz.harmfulgas.registry.client;
	
	import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
	import net.minecraft.util.Identifier;
	import vini2003.xyz.harmfulgas.HarmfulGas;
	import vini2003.xyz.harmfulgas.client.utilities.ClientAtmosphereUtilities;
	
	public class HarmfulGasNetworking {
		public static final Identifier PARTICLE_ADDED = HarmfulGas.identifier("particle_added");
		
		public static void initialize() {
			ClientPlayNetworking.registerGlobalReceiver(ClientAtmosphereUtilities.GAS_ERASED, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				packetByteBuf.retain();
				
				ClientAtmosphereUtilities.onGasErased(packetByteBuf);
			}));
			
			ClientPlayNetworking.registerGlobalReceiver(ClientAtmosphereUtilities.GAS_ADDED, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				packetByteBuf.retain();
				
				ClientAtmosphereUtilities.onGasAdded(packetByteBuf);
			}));
			
			ClientPlayNetworking.registerGlobalReceiver(ClientAtmosphereUtilities.GAS_REMOVED, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				packetByteBuf.retain();
				
				ClientAtmosphereUtilities.onGasRemoved(packetByteBuf);
			}));
			
			ClientPlayNetworking.registerGlobalReceiver(PARTICLE_ADDED, ((minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
				minecraftClient.world.addParticle(HarmfulGasParticleTypes.GAS, packetByteBuf.readInt(), packetByteBuf.readInt(), packetByteBuf.readInt(), 0D, 0D, 0D);
			}));
		}
	}
