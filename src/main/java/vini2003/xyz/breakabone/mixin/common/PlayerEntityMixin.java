package vini2003.xyz.breakabone.mixin.common;

import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Shadow public abstract EntityDimensions getDimensions(EntityPose pose);
	
	@Shadow @Final private static Map<EntityPose, EntityDimensions> POSE_DIMENSIONS;
	
	@Shadow @Final public static EntityDimensions STANDING_DIMENSIONS;
	
	@Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
	void breakabone_getDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
		if (pose == EntityPose.STANDING || pose == EntityPose.CROUCHING) {
			EntityDimensions dimensions = POSE_DIMENSIONS.get(pose);
			
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);

			cir.setReturnValue(EntityDimensions.changing(dimensions.width, bodyParts.getHeight(dimensions)));
			cir.cancel();
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getActiveEyeHeight", cancellable = true)
	void breakabone_getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
		if (pose == EntityPose.STANDING || pose == EntityPose.CROUCHING) {
			cir.setReturnValue(dimensions.height * 0.85F);
			cir.cancel();
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getMovementSpeed", cancellable = true)
	void breakabone_getMovementSpeed(CallbackInfoReturnable<Float> cir) {
		if ((Object) this instanceof PlayerEntity) {
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);
			
			if (!bodyParts.hasHead() && !bodyParts.hasTorso() && !bodyParts.hasLeftArm() && !bodyParts.hasRightArm()) {
				float multiplier = 1F;
				
				if (bodyParts.hasLeftLeg()) multiplier += 0.5F;
				if (bodyParts.hasRightLeg()) multiplier += 0.5F;
				
				cir.setReturnValue(cir.getReturnValueF() * multiplier);
				cir.cancel();
				
				return;
			}
			
			if (!bodyParts.hasHead() && !bodyParts.hasTorso() && !bodyParts.hasLeftLeg() && !bodyParts.hasRightLeg()) {
				float multiplier = 1F;
				
				if (bodyParts.hasLeftArm()) multiplier -= 0.25F;
				if (bodyParts.hasRightArm()) multiplier -= 0.25F;
				
				cir.setReturnValue(cir.getReturnValueF() * multiplier);
				cir.cancel();
				
				return;
			}
		}
	}
}
