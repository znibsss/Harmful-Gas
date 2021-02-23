package vini2003.xyz.breakabone.common.screenhandler;

import com.github.vini2003.blade.client.utilities.Texts;
import com.github.vini2003.blade.common.handler.BaseScreenHandler;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;
import com.github.vini2003.blade.common.utilities.Networks;
import com.github.vini2003.blade.common.widget.base.ButtonWidget;
import com.github.vini2003.blade.common.widget.base.TextWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.BreakABone;
import vini2003.xyz.breakabone.client.screen.widget.BodyPartWidget;
import vini2003.xyz.breakabone.client.screen.widget.PlayerWidget;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.common.miscellaneous.BodyPart;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;
import vini2003.xyz.breakabone.registry.common.BreakABoneScreenHandlers;

import java.util.function.Supplier;

public class BodyPartSelectorScreenHandler extends BaseScreenHandler {
	public static final Text BLUR = new TranslatableText("text.breakabone.blur");
	
	public static final Text RANDOMIZE_HEAD = new TranslatableText("text.breakabone.randomize_head");
	public static final Text RANDOMIZE_TORSO = new TranslatableText("text.breakabone.randomize_torso");
	public static final Text RANDOMIZE_RIGHT_ARM = new TranslatableText("text.breakabone.randomize_right_arm");
	public static final Text RANDOMIZE_LEFT_ARM = new TranslatableText("text.breakabone.randomize_left_arm");
	public static final Text RANDOMIZE_RIGHT_LEG = new TranslatableText("text.breakabone.randomize_right_leg");
	public static final Text RANDOMIZE_LEFT_LEG = new TranslatableText("text.breakabone.randomize_left_leg");
	
	public static final Text SPEED_INCREASE = new TranslatableText("text.breakabone.speed_increase");
	public static final Text SPEED_DECREASE = new TranslatableText("text.breakabone.speed_decrease");
	
	public static final Text JUMP_INCREASE = new TranslatableText("text.breakabone.jump_increase");
	public static final Text JUMP_DECREASE = new TranslatableText("text.breakabone.jump_decrease");
	
	public static final Text SINGLE_HAND_LIMITATIONS = new TranslatableText("text.breakabone.single_hand_limitations");
	public static final Text NO_HAND_LIMITATIONS = new TranslatableText("text.breakabone.no_hand_limitations");
	
	public static final Text HEALTH_LIMITATIONS = new TranslatableText("text.breakabone.health_limitations");
	
	public static final Text CLIMBING_LIMITATIONS = new TranslatableText("text.breakabone.climbing_limitations");
	
	public static final Text ENABLED = new TranslatableText("text.breakabone.enabled").formatted(Formatting.GREEN);
	public static final Text DISABLED = new TranslatableText("text.breakabone.disabled").formatted(Formatting.RED);
	
	public static final Text YES = new TranslatableText("text.breakabone.yes").formatted(Formatting.GREEN);
	public static final Text NO = new TranslatableText("text.breakabone.no").formatted(Formatting.RED);
	
	public BodyPartSelectorScreenHandler(int syncId, @NotNull PlayerEntity player) {
		super(BreakABoneScreenHandlers.BODY_PART_SELECTOR, syncId, player);
	}
	
	@Override
	public void initialize(int width, int height) {
		BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(getPlayer());
		
		int selectorCenterX = width / 2 + width / 4;
		int selectorCenterY = height / 2;
		
		int modelCenterX = width / 2 - width / 4 + 128;
		int modelCenterY = height / 2 + width / 8 - 8;
		
		BodyPartWidget headWidget = new BodyPartWidget();
		headWidget.setSize(Size.of(25, 25));
		headWidget.setPosition(Position.of(selectorCenterX - 12.5F, selectorCenterY - 37.5F - 25F - 2.5F));
		headWidget.setPart(BodyPart.HEAD);
		headWidget.setEnabled(bodyParts.hasHead());
		headWidget.setEnabledTexture(BreakABone.identifier("textures/widget/head_enabled.png"));
		headWidget.setDisabledTexture(BreakABone.identifier("textures/widget/head_disabled.png"));
		
		addWidget(headWidget);
		
		BodyPartWidget torsoWidget = new BodyPartWidget();
		torsoWidget.setSize(Size.of(25F, 37.5F));
		torsoWidget.setPosition(Position.of(selectorCenterX - 12.5F, selectorCenterY - 37.5F));
		torsoWidget.setPart(BodyPart.TORSO);
		torsoWidget.setEnabled(bodyParts.hasTorso());
		torsoWidget.setEnabledTexture(BreakABone.identifier("textures/widget/torso_enabled.png"));
		torsoWidget.setDisabledTexture(BreakABone.identifier("textures/widget/torso_disabled.png"));
		
		addWidget(torsoWidget);
		
		BodyPartWidget leftArmWidget = new BodyPartWidget();
		leftArmWidget.setSize(Size.of(12.5F, 37.5F));
		leftArmWidget.setPosition(Position.of(selectorCenterX + 12.5F + 2.5F, selectorCenterY - 37.5F));
		leftArmWidget.setPart(BodyPart.LEFT_ARM);
		leftArmWidget.setEnabled(bodyParts.hasLeftArm());
		leftArmWidget.setEnabledTexture(BreakABone.identifier("textures/widget/left_arm_enabled.png"));
		leftArmWidget.setDisabledTexture(BreakABone.identifier("textures/widget/left_arm_disabled.png"));
		
		addWidget(leftArmWidget);
		
		BodyPartWidget rightArmWidget = new BodyPartWidget();
		rightArmWidget.setSize(Size.of(12.5F, 37.5F));
		rightArmWidget.setPosition(Position.of(selectorCenterX - 12.5F - 12.5F - 2.5F, selectorCenterY - 37.5F));
		rightArmWidget.setPart(BodyPart.RIGHT_ARM);
		rightArmWidget.setEnabled(bodyParts.hasRightArm());
		rightArmWidget.setEnabledTexture(BreakABone.identifier("textures/widget/right_arm_enabled.png"));
		rightArmWidget.setDisabledTexture(BreakABone.identifier("textures/widget/right_arm_disabled.png"));
		
		addWidget(rightArmWidget);
		
		BodyPartWidget leftLegWidget = new BodyPartWidget();
		leftLegWidget.setSize(Size.of(12.5F, 37.5F));
		leftLegWidget.setPosition(Position.of(selectorCenterX, selectorCenterY + 2.5F));
		leftLegWidget.setPart(BodyPart.LEFT_LEG);
		leftLegWidget.setEnabled(bodyParts.hasLeftLeg());
		leftLegWidget.setEnabledTexture(BreakABone.identifier("textures/widget/left_leg_enabled.png"));
		leftLegWidget.setDisabledTexture(BreakABone.identifier("textures/widget/left_leg_disabled.png"));
		
		addWidget(leftLegWidget);
		
		BodyPartWidget rightLegWidget = new BodyPartWidget();
		rightLegWidget.setSize(Size.of(12.5F, 37.5F));
		rightLegWidget.setPosition(Position.of(selectorCenterX - 12.5F, selectorCenterY + 2.5F));
		rightLegWidget.setPart(BodyPart.RIGHT_LEG);
		rightLegWidget.setEnabled(bodyParts.hasRightLeg());
		rightLegWidget.setEnabledTexture(BreakABone.identifier("textures/widget/right_leg_enabled.png"));
		rightLegWidget.setDisabledTexture(BreakABone.identifier("textures/widget/right_leg_disabled.png"));
		
		addWidget(rightLegWidget);
		
		PlayerWidget playerWidget = new PlayerWidget();
		playerWidget.setPosition(Position.of(modelCenterX, modelCenterY));
		playerWidget.setModelSize(64);
		
		addWidget(playerWidget);
		
		addToggle(Position.of(4F, 4F), BLUR, bodyParts.hasBlur() ? ENABLED : DISABLED, () -> {
			bodyParts.setBlur(!bodyParts.hasBlur());
			
			return bodyParts.hasBlur() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 1F + 4F), SPEED_INCREASE, bodyParts.hasSpeedIncrease() ? ENABLED : DISABLED, () -> {
			bodyParts.setSpeedIncrease(!bodyParts.hasSpeedIncrease());
			
			return bodyParts.hasSpeedIncrease() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 2F + 4F), SPEED_DECREASE, bodyParts.hasSpeedDecrease() ? ENABLED : DISABLED, () -> {
			bodyParts.setSpeedDecrease(!bodyParts.hasSpeedDecrease());
			
			return bodyParts.hasSpeedDecrease() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 3F + 4F), JUMP_INCREASE, bodyParts.hasJumpIncrease() ? ENABLED : DISABLED, () -> {
			bodyParts.setJumpIncrease(!bodyParts.hasJumpIncrease());
			
			return bodyParts.hasJumpIncrease() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 4F + 4F), JUMP_DECREASE, bodyParts.hasJumpDecrease() ? ENABLED : DISABLED, () -> {
			bodyParts.setJumpDecrease(!bodyParts.hasJumpDecrease());
			
			return bodyParts.hasJumpDecrease() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 5F + 4F), SINGLE_HAND_LIMITATIONS, bodyParts.hasSingleHandLimitations() ? ENABLED : DISABLED, () -> {
			bodyParts.setSingleHandLimitations(!bodyParts.hasSingleHandLimitations());
			
			return bodyParts.hasSingleHandLimitations() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 6F + 4F), NO_HAND_LIMITATIONS, bodyParts.hasNoHandLimitations() ? ENABLED : DISABLED, () -> {
			bodyParts.setNoHandLimitations(!bodyParts.hasNoHandLimitations());
			
			return bodyParts.hasNoHandLimitations() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 7F + 4F), HEALTH_LIMITATIONS, bodyParts.hasHealthLimitations() ? ENABLED : DISABLED, () -> {
			bodyParts.setHealthLimitations(!bodyParts.hasHealthLimitations());
			
			return bodyParts.hasHealthLimitations() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 8F + 4F), CLIMBING_LIMITATIONS, bodyParts.hasClimbingLimitations() ? ENABLED : DISABLED, () -> {
			bodyParts.setClimbingLimitations(!bodyParts.hasClimbingLimitations());
			
			return bodyParts.hasClimbingLimitations() ? ENABLED : DISABLED;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 9F + 4F + 4F), RANDOMIZE_HEAD, bodyParts.shouldRandomizeHead() ? YES : NO, () -> {
			bodyParts.setRandomizeHead(!bodyParts.shouldRandomizeHead());
			
			return bodyParts.shouldRandomizeHead() ? YES : NO;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 10F + 4F + 4F), RANDOMIZE_TORSO, bodyParts.shouldRandomizeTorso() ? YES : NO, () -> {
			bodyParts.setRandomizeTorso(!bodyParts.shouldRandomizeTorso());
			
			return bodyParts.shouldRandomizeTorso() ? YES : NO;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 11F + 4F + 4F), RANDOMIZE_RIGHT_ARM, bodyParts.shouldRandomizeRightArm() ? YES : NO, () -> {
			bodyParts.setRandomizeRightArm(!bodyParts.shouldRandomizeRightArm());
			
			return bodyParts.shouldRandomizeRightArm() ? YES : NO;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 12F + 4F + 4F), RANDOMIZE_LEFT_ARM, bodyParts.shouldRandomizeLeftArm() ? YES : NO, () -> {
			bodyParts.setRandomizeLeftArm(!bodyParts.shouldRandomizeLeftArm());
			
			return bodyParts.shouldRandomizeLeftArm() ? YES : NO;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 13F + 4F + 4F), RANDOMIZE_RIGHT_LEG, bodyParts.shouldRandomizeRightLeg() ? YES : NO, () -> {
			bodyParts.setRandomizeRightLeg(!bodyParts.shouldRandomizeRightLeg());
			
			return bodyParts.shouldRandomizeRightLeg() ? YES : NO;
		});
		
		addToggle(Position.of(4F, 4F + 16 * 14F + 4F + 4F), RANDOMIZE_LEFT_LEG, bodyParts.shouldRandomizeLeftLeg() ? YES : NO, () -> {
			bodyParts.setRandomizeLeftLeg(!bodyParts.shouldRandomizeLeftLeg());
			
			return bodyParts.shouldRandomizeLeftLeg() ? YES : NO;
		});
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return player.isAlive();
	}
	
	private void addToggle(Position position, Text option, Text label, Supplier<Text> action) {
		ToggleButton button = new ToggleButton();
		button.setPosition(position);
		button.setSize(Size.of(Texts.width(label) + 16F, 16F));
		button.setLabel(label);
		
		addWidget(button);
		
		TextWidget text = new TextWidget();
		text.setPosition(Position.of(button, button.getWidth() + 4F, 4F));
		text.setSize(Size.of(Texts.width(option), 16F));
		text.setText(option);
		text.setShadow(true);
		text.setColor(0xFFFFFFFF);
		
		addWidget(text);
		
		button.setAction(() -> {
			Text labelText = action.get();
			
			button.setLabel(labelText);
			button.setWidth(Texts.width(labelText) + 16F);
			
			text.setPosition(Position.of(button, button.getWidth() + 4F, 4F));
		});
	}
	
	private static class ToggleButton extends ButtonWidget {
		private Runnable action = () -> {};
		
		public ToggleButton() {
			getSynchronize().add(Networks.getMOUSE_CLICK());
		}
		
		@Override
		public void onMouseClicked(float x, float y, int button) {
			super.onMouseClicked(x, y, button);
			
			if (!getHandler().getClient() || getFocused()) {
				action.run();
			}
		}
		
		public Runnable getAction() {
			return action;
		}
		
		public void setAction(Runnable action) {
			this.action = action;
		}
	}
}
