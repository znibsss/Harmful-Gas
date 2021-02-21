package vini2003.xyz.breakabone.client.screen;

import com.github.vini2003.blade.client.handler.BaseHandledScreen;
import com.github.vini2003.blade.common.handler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import vini2003.xyz.breakabone.common.screenhandler.BodyPartScreenHandler;

public class BodyPartScreen extends BaseHandledScreen<BodyPartScreenHandler> {
	public BodyPartScreen(@NotNull BaseScreenHandler handler, @NotNull PlayerInventory inventory, @NotNull Text title) {
		super(handler, inventory, title);
	}
}
