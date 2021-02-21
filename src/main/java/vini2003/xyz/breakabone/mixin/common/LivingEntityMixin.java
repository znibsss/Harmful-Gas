package vini2003.xyz.breakabone.mixin.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(at = @At("RETURN"), method = "getJumpVelocity", cancellable = true)
	public void breakabone_getJumpVelocity(CallbackInfoReturnable<Float> cir) {
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
