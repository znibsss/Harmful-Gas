package vini2003.xyz.breakabone.client.screen.widget;

import com.github.vini2003.blade.client.utilities.Instances;
import com.github.vini2003.blade.client.utilities.Layers;
import com.github.vini2003.blade.common.miscellaneous.Color;
import com.github.vini2003.blade.common.utilities.Networks;
import com.github.vini2003.blade.common.widget.base.AbstractWidget;
import com.github.vini2003.blade.client.utilities.Drawings;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.common.miscellaneous.BodyPart;
import vini2003.xyz.breakabone.registry.common.BreakABoneComponents;

public class BodyPartWidget extends AbstractWidget {
	private Identifier enabledTexture;
	
	private Identifier disabledTexture;
	
	private BodyPart part;
	
	private boolean enabled;
	
	public BodyPartWidget() {
		getSynchronize().add(Networks.getMOUSE_RELEASE());
	}
	
	@Override
	public void onMouseReleased(float x, float y, int button) {
		super.onMouseReleased(x, y, button);
		
		if (!getHandler().getClient() || getFocused()) {
			if (getHandler().getClient()) {
				Instances.Companion.client().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			}
			
			setEnabled(!isEnabled());
			
			BreakABoneComponents.BODY_PARTS.get(getHandler().getPlayer()).toggle(part, enabled);
			BreakABoneComponents.BODY_PARTS.sync(getHandler().getPlayer());
		}
	}
	

	@Override
	public void drawWidget(@NotNull MatrixStack matrices, @NotNull VertexConsumerProvider provider) {
		if (getHidden()) return;
		
		if (enabled) {
			Drawings.drawTexturedQuad(matrices, provider, Layers.get(enabledTexture), getX(), getY(), getWidth(), getHeight(), enabledTexture);
		} else {
			Drawings.drawTexturedQuad(matrices, provider, Layers.get(disabledTexture), getX(), getY(), getWidth(), getHeight(), disabledTexture);
		}
	}
	
	public Identifier getEnabledTexture() {
		return enabledTexture;
	}
	
	public void setEnabledTexture(Identifier enabledTexture) {
		this.enabledTexture = enabledTexture;
	}
	
	public Identifier getDisabledTexture() {
		return disabledTexture;
	}
	
	public void setDisabledTexture(Identifier disabledTexture) {
		this.disabledTexture = disabledTexture;
	}
	
	public BodyPart getPart() {
		return part;
	}
	
	public void setPart(BodyPart part) {
		this.part = part;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
