package mods.skipsign.client.gui.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class FrameDropOffModeButton extends Button {
    private Boolean value;

    public FrameDropOffModeButton(int x, int y, int width, int height, Boolean defaultValue) {
        super(x, y, width, height, TextComponent.EMPTY, button -> {});
        this.value = defaultValue;
        update();
    }

    @Override
    public void onPress() {
        this.value = !this.value;
        update();
        super.onPress();
    }

    public Boolean getValue() {
        return this.value;
    }

    public void update() {
        setMessage(this.value
            ? new TranslatableComponent("skipsign.setting.outofrange.frame.hide")
            : new TranslatableComponent("skipsign.setting.outofrange.frame.show")
        );
    }
}
