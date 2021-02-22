package vini2003.xyz.breakabone.common.screenhandler;

import com.github.vini2003.blade.common.handler.BaseScreenHandler;
import com.github.vini2003.blade.common.miscellaneous.Position;
import com.github.vini2003.blade.common.miscellaneous.Size;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.BreakABone;
import vini2003.xyz.breakabone.client.screen.widget.BodyPartWidget;
import vini2003.xyz.breakabone.client.screen.widget.PlayerWidget;
import vini2003.xyz.breakabone.common.component.BodyPartComponent;
import vini2003.xyz.breakabone.common.miscellaneous.BodyPart;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;
import vini2003.xyz.breakabone.registry.common.BreakABoneScreenHandlers;

public class BodyPartSelectorScreenHandler extends BaseScreenHandler {
	public BodyPartSelectorScreenHandler(int syncId, @NotNull PlayerEntity player) {
		super(BreakABoneScreenHandlers.BODY_PART_SELECTOR, syncId, player);
	}
	
	@Override
	public void initialize(int width, int height) {
		BodyPartComponent bodyParts = BreakABoneComponents.BODY_PARTS.get(getPlayer());
		
		int selectorCenterX = width / 2 + width / 4 - 64;
		int selectorCenterY = height / 2;
		
		int modelCenterX = width / 2 - width / 4 + 64;
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
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return player.isAlive();
	}
}
