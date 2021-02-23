package vini2003.xyz.breakabone.mixin.client;

import com.sun.jna.platform.unix.X11;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.breakabone.client.utilities.BreakABoneClientUtilities;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.client.BreakABoneShaders;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "setModelPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)V")
	void breakabone_setModelPose(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfo ci) {
		BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(BreakABoneClientUtilities.getPlayer());
		
		PlayerEntityModel model = ((PlayerEntityRenderer) (Object) this).getModel();
		
		model.head.visible = bodyParts.hasHead();
		model.helmet.visible = bodyParts.hasHead();
		
		model.jacket.visible = bodyParts.hasTorso();
		model.torso.visible = bodyParts.hasTorso();
		
		model.rightArm.visible = bodyParts.hasRightArm();
		model.rightSleeve.visible = bodyParts.hasRightArm();
		
		model.leftArm.visible = bodyParts.hasLeftArm();
		model.leftSleeve.visible = bodyParts.hasLeftArm();
		
		model.rightLeg.visible = bodyParts.hasRightLeg();
		model.rightPantLeg.visible = bodyParts.hasRightLeg();
		
		model.leftLeg.visible = bodyParts.hasLeftLeg();
		model.leftPantLeg.visible = bodyParts.hasLeftLeg();
		
		BreakABoneShaders.enableBlur = !bodyParts.hasHead() && bodyParts.hasBlur();
	}
}
