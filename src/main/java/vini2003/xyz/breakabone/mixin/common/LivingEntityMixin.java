package vini2003.xyz.breakabone.mixin.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow public abstract boolean isClimbing();
	
	@Inject(at = @At("RETURN"), method = "getJumpVelocity", cancellable = true)
	public void breakabone_getJumpVelocity(CallbackInfoReturnable<Float> cir) {
		if ((Object) this instanceof PlayerEntity) {
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);
			
			if (!bodyParts.hasHead() && !bodyParts.hasTorso() && !bodyParts.hasLeftArm() && !bodyParts.hasRightArm()) {
				float multiplier = 1F;
				
				if (bodyParts.hasLeftLeg()) multiplier += 0.125F;
				if (bodyParts.hasRightLeg()) multiplier += 0.125F;
				
				cir.setReturnValue(cir.getReturnValueF() * multiplier);
				cir.cancel();
				
				return;
			}
			
			if (!bodyParts.hasHead() && !bodyParts.hasTorso() && !bodyParts.hasLeftLeg() && !bodyParts.hasRightLeg()) {
				float multiplier = 1F;

				if (bodyParts.hasLeftArm()) multiplier -= 0.125F;
				if (bodyParts.hasRightArm()) multiplier -= 0.125F;
				
				cir.setReturnValue(cir.getReturnValueF() * multiplier);
				cir.cancel();
				
				return;
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "isClimbing", cancellable = true)
	void breakabone_isClimbing(CallbackInfoReturnable<Boolean> cir) {
		if ((Object) this instanceof PlayerEntity) {
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);
			
			if (!bodyParts.hasAnyLeg() && !bodyParts.hasAnyArm()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
	
	@Inject(at = @At("RETURN"), method = "travel")
	void breakabone_travel(Vec3d movementInput, CallbackInfo ci) {
		if((Object) this instanceof PlayerEntity && isClimbing()) {
			PlayerEntity player = (PlayerEntity) (Object) this;
			
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);
			
			if (!bodyParts.hasAnyArm()) {
				player.setVelocity(player.getVelocity().getX(), player.getVelocity().getY() * 0.75F, player.getVelocity().getZ());
			}
			
			if (!bodyParts.hasAnyLeg()) {
				player.setVelocity(player.getVelocity().getX(), player.getVelocity().getY() * 0.75F, player.getVelocity().getZ());
			}
		}
	}
	
	@Inject(at = @At("RETURN"), method = "getMaxHealth", cancellable = true)
	void breakabone_getMaxHealth(CallbackInfoReturnable<Float> cir) {
		if ((Object) this instanceof PlayerEntity) {
			float healthMultiplier = 1F;
			
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(this);
			
			if (!bodyParts.hasHead()) {
				healthMultiplier -= 0.125F;
			}
			
			if (!bodyParts.hasLeftArm()) {
				healthMultiplier -= 0.125F;
			}
			
			if (!bodyParts.hasRightArm()) {
				healthMultiplier -= 0.125F;
			}
			
			if (!bodyParts.hasTorso()) {
				healthMultiplier -= 0.25F;
			}
			
			if (!bodyParts.hasLeftLeg()) {
				healthMultiplier -= 0.175F;
			}
			
			if (!bodyParts.hasRightLeg()) {
				healthMultiplier -= 0.175F;
			}
			
			cir.setReturnValue(cir.getReturnValueF() * Math.max(0.125F, healthMultiplier));
			cir.cancel();
		}
	}
}
