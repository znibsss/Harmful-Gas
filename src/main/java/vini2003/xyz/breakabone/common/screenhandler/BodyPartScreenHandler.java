package vini2003.xyz.breakabone.common.screenhandler;

import com.github.vini2003.blade.common.handler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.registry.common.BreakABoneScreenHandlers;

public class BodyPartScreenHandler extends BaseScreenHandler {
	public BodyPartScreenHandler(int syncId, @NotNull PlayerEntity player) {
		super(BreakABoneScreenHandlers.BODY_PART, syncId, player);
	}
	
	@Override
	public void initialize(int width, int height) {
	
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return player.isAlive();
	}
}
