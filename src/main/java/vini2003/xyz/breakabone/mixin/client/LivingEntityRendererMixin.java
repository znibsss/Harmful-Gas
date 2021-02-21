package vini2003.xyz.breakabone.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin <T extends LivingEntity, M extends EntityModel<T>> {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V", ordinal = 1), method = "render")
	void breakabone_render(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntityRenderer) {
			EntityDimensions dimensions = livingEntity.getDimensions(livingEntity.getPose());
			
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(livingEntity);
			
			if (bodyParts.hasHead()) {
				matrixStack.translate(0F, 1.8F - dimensions.height, 0F);
			} else if ((bodyParts.hasLeftArm() || bodyParts.hasRightArm()) && (!bodyParts.hasLeftLeg() && !bodyParts.hasRightLeg())) {
				matrixStack.translate(0F, 0.675F, 0F);
			}
		}
	}
}
