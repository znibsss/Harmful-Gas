package vini2003.xyz.breakabone.common.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class BodyPartComponent implements PlayerComponent<BodyPartComponent>, AutoSyncedComponent {
	private boolean leftArm;
	private boolean rightArm;
	private boolean leftLeg;
	private boolean rightLeg;
	private boolean head;
	private boolean torso;
	
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
	}
	
	@Override
	public void writeToNbt(CompoundTag compoundTag) {
		compoundTag.putBoolean("LeftArm", leftArm);
		compoundTag.putBoolean("RightArm", rightArm);
		compoundTag.putBoolean("LeftLeg", leftLeg);
		compoundTag.putBoolean("RightLeg", rightLeg);
		compoundTag.putBoolean("Head", head);
		compoundTag.putBoolean("Torso", torso);
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
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	public void setPlayer(PlayerEntity player) {
		this.player = player;
	}
}
