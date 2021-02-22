package vini2003.xyz.breakabone.client.screen.widget;

import com.github.vini2003.blade.common.utilities.Positions;
import com.github.vini2003.blade.common.widget.base.AbstractWidget;
import me.shedaniel.rei.api.widgets.BaseWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public class PlayerWidget extends AbstractWidget {
	private int modelSize = 1;
	
	@Override
	public void drawWidget(@NotNull MatrixStack matrices, @NotNull VertexConsumerProvider provider) {
		if (getHidden()) return;
		
		InventoryScreen.drawEntity((int) getX(), (int) getY(), getModelSize(), getX() - Positions.getMouseX(), getY() - Positions.getMouseY() - modelSize * 1.66F, getHandler().getPlayer());
	}
	
	public int getModelSize() {
		return modelSize;
	}
	
	public void setModelSize(int modelSize) {
		this.modelSize = modelSize;
	}
}
