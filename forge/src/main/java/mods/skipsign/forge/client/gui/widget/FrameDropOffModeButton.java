package mods.skipsign.forge.client.gui.widget;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class FrameDropOffModeButton extends Button {
    private Boolean value;

    public FrameDropOffModeButton(int x, int y, int width, int height, Boolean defaultValue) {
        super(x, y, width, height, Component.empty(), button -> {}, Button.DEFAULT_NARRATION);
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
            ? Component.translatable("skipsign.setting.outofrange.frame.hide")
            : Component.translatable("skipsign.setting.outofrange.frame.show")
        );
    }
}
