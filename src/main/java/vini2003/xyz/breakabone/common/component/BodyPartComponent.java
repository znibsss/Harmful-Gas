package vini2003.xyz.breakabone.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import vini2003.xyz.breakabone.common.miscellaneous.BodyPart;

public class BodyPartComponent implements PlayerComponent<BodyPartComponent>, AutoSyncedComponent {
	private boolean leftArm;
	private boolean rightArm;
	private boolean leftLeg;
	private boolean rightLeg;
	private boolean head;
	private boolean torso;
	
	private boolean blur;
	
	private boolean randomizeLeftArm = true;
	private boolean randomizeRightArm = true;
	private boolean randomizeLeftLeg = true;
	private boolean randomizeRightLeg = true;
	private boolean randomizeHead = true;
	private boolean randomizeTorso = true;
	
	private PlayerEntity player;
	
	public BodyPartComponent(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void readFromNbt(CompoundTag compoundTag) {
		setLeftArm(compoundTag.getBoolean("LeftArm"));
		setRightArm(compoundTag.getBoolean("RightArm"));
		setLeftLeg(compoundTag.getBoolean("LeftLeg"));
		setRightLeg(compoundTag.getBoolean("RightLeg"));
		setHead(compoundTag.getBoolean("Head"));
		setTorso(compoundTag.getBoolean("Torso"));
		
		setBlur(compoundTag.getBoolean("Blur"));
		
		setRandomizeLeftArm(compoundTag.getBoolean("RandomizeLeftArm"));
		setRandomizeRightArm(compoundTag.getBoolean("RandomizeRightArm"));
		setRandomizeLeftLeg(compoundTag.getBoolean("RandomizeLeftLeg"));
		setRandomizeRightLeg(compoundTag.getBoolean("RandomizeRightLeg"));
		setRandomizeHead(compoundTag.getBoolean("RandomizeHead"));
		setRandomizeTorso(compoundTag.getBoolean("RandomizeTorso"));
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		compoundTag.putBoolean("LeftArm", leftArm);
		compoundTag.putBoolean("RightArm", rightArm);
		compoundTag.putBoolean("LeftLeg", leftLeg);
		compoundTag.putBoolean("RightLeg", rightLeg);
		compoundTag.putBoolean("Head", head);
		compoundTag.putBoolean("Torso", torso);
		
		compoundTag.putBoolean("Blur", blur);
		
		compoundTag.putBoolean("RandomizeLeftArm", randomizeLeftArm);
		compoundTag.putBoolean("RandomizeRightArm", randomizeRightArm);
		compoundTag.putBoolean("RandomizeLeftLeg", randomizeLeftLeg);
		compoundTag.putBoolean("RandomizeRightLeg", randomizeRightLeg);
		compoundTag.putBoolean("RandomizeHead", randomizeHead);
		compoundTag.putBoolean("RandomizeTorso", randomizeTorso);
	}
	
	public void updateBoundingBox() {
		player.calculateDimensions();
		player.updatePosition(player.getX(), player.getY(), player.getZ());
	}
	
	public boolean hasLeftArm() {
		return leftArm;
	}
	
	public void setLeftArm(boolean leftArm) {
		this.leftArm = leftArm;
		updateBoundingBox();
	}
	
	public boolean hasRightArm() {
		return rightArm;
	}
	
	public void setRightArm(boolean rightArm) {
		this.rightArm = rightArm;
		updateBoundingBox();
	}

	public boolean hasAnyArm() {
		return hasLeftArm() || hasRightArm();
	}
	
	public boolean hasLeftLeg() {
		return leftLeg;
	}
	
	public void setLeftLeg(boolean leftLeg) {
		this.leftLeg = leftLeg;
		updateBoundingBox();
	}
	
	public boolean hasRightLeg() {
		return rightLeg;
	}
	
	public void setRightLeg(boolean rightLeg) {
		this.rightLeg = rightLeg;
		updateBoundingBox();
	}
	
	public boolean hasAnyLeg() {
		return hasLeftLeg() || hasRightLeg();
	}
	
	public boolean hasHead() {
		return head;
	}
	
	public void setHead(boolean head) {
		this.head = head;
		updateBoundingBox();
	}
	
	public boolean hasTorso() {
		return torso;
	}
	
	public void setTorso(boolean torso) {
		this.torso = torso;
		updateBoundingBox();
	}
	
	public boolean hasBlur() {
		return blur;
	}
	
	public void setBlur(boolean blur) {
		this.blur = blur;
	}
	
	public boolean shouldRandomizeLeftArm() {
		return randomizeLeftArm;
	}
	
	public void setRandomizeLeftArm(boolean randomizeLeftArm) {
		this.randomizeLeftArm = randomizeLeftArm;
	}
	
	public boolean shouldRandomizeRightArm() {
		return randomizeRightArm;
	}
	
	public void setRandomizeRightArm(boolean randomizeRightArm) {
		this.randomizeRightArm = randomizeRightArm;
	}
	
	public boolean shouldRandomizeLeftLeg() {
		return randomizeLeftLeg;
	}
	
	public void setRandomizeLeftLeg(boolean randomizeLeftLeg) {
		this.randomizeLeftLeg = randomizeLeftLeg;
	}
	
	public boolean shouldRandomizeRightLeg() {
		return randomizeRightLeg;
	}
	
	public void setRandomizeRightLeg(boolean randomizeRightLeg) {
		this.randomizeRightLeg = randomizeRightLeg;
	}
	
	public boolean shouldRandomizeHead() {
		return randomizeHead;
	}
	
	public void setRandomizeHead(boolean randomizeHead) {
		this.randomizeHead = randomizeHead;
	}
	
	public boolean shouldRandomizeTorso() {
		return randomizeTorso;
	}
	
	public void setRandomizeTorso(boolean randomizeTorso) {
		this.randomizeTorso = randomizeTorso;
	}
	
	public float getHeight(EntityDimensions dimensions) {
		float newHeight = dimensions.height;
		
		float headHeight = 0.25F * dimensions.height;
		float armHeight = 0.375F * dimensions.height;
		float legHeight = 0.368F * dimensions.height;
		
		if (!hasHead()) {
			newHeight -= headHeight;
		}
		
		if (!hasAnyArm() && !hasTorso() && !hasAnyLeg()) {
			newHeight -= armHeight;
		} else if (!hasHead() && !hasAnyArm() && !hasTorso() && hasAnyLeg()) {
			newHeight -= armHeight;
		}
		
		if (!hasAnyLeg()) {
			newHeight -= legHeight;
		}
		
		return Math.max(0.125F, newHeight);
	}
	
	public void toggle(BodyPart part, boolean enabled) {
		switch (part) {
			case HEAD: {
				head = enabled;
				break;
			}
			
			case TORSO: {
				torso = enabled;
				break;
			}
			
			case LEFT_ARM: {
				leftArm = enabled;
				break;
			}
			
			case RIGHT_ARM: {
				rightArm = enabled;
				break;
			}
			
			case LEFT_LEG: {
				leftLeg = enabled;
				break;
			}
			
			case RIGHT_LEG: {
				rightLeg = enabled;
				break;
			}
		}
	}
}
