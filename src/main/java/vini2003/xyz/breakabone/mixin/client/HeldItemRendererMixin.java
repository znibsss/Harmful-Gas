package vini2003.xyz.breakabone.mixin.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@Inject(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
	void breakabone_renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (entity instanceof PlayerEntity) {
			BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(entity);
			
			Hand hand = entity.preferredHand;
			if (hand == null) hand = Hand.MAIN_HAND;
			
			if ((!bodyParts.hasLeftArm() && hand == Hand.OFF_HAND) || (!bodyParts.hasRightArm() && hand == Hand.MAIN_HAND)) {
				ci.cancel();
			}
		}
	}
}
