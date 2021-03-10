package vini2003.xyz.harmfulgas.client.util;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.util.math.Vector3f;

public class GasParticleUtils {
	public static Vector3f[] rotatedVertices;
	
	public static boolean shouldClear = false;
	
	public static void recalculateRotatedVertices(WorldRenderContext context) {
		Vector3f[] newRotatedVertices = new Vector3f[]{
				new Vector3f(-6.0F, -6.0F, 0.0F),
				new Vector3f(-6.0F, 6.0F, 0.0F),
				new Vector3f(6.0F, 6.0F, 0.0F),
				new Vector3f(6.0F, -6.0F, 0.0F)
		};
		
		for (int i = 0; i < 4; ++i) {
			Vector3f vertex = newRotatedVertices[i];
			
			vertex.rotate(context.camera().getRotation());
		}
		
		rotatedVertices = newRotatedVertices;
	}
}
