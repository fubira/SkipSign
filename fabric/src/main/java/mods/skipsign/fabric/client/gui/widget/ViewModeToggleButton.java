package mods.skipsign.fabric.client.gui.widget;

import mods.skipsign.fabric.ViewMode;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ViewModeToggleButton extends Button {
    private ViewMode value;

    public ViewModeToggleButton(int x, int y, int width, int height, ViewMode defaultValue) {
        super(x, y, width, height, Component.empty(), button -> {});
        this.value = defaultValue;
        update();
    }

    private ViewMode toggleViewMode(final ViewMode current) {
        ViewMode next;
        switch(current) {
            case NORMAL:
                next = ViewMode.FORCE;
                break;
            case FORCE:
                next = ViewMode.NONE;
                break;
            default:
                next = ViewMode.NORMAL;
                break;
        }
        return next;
    }  

    public void onPress() {
        this.value = toggleViewMode(this.value);
        update();
    }

    public ViewMode getValue() {
        return this.value;
    }

    public void update() {
        switch(this.value) {
            case NORMAL: setMessage(Component.translatable("skipsign.setting.viewmode.normal")); break;
            case FORCE:  setMessage(Component.translatable("skipsign.setting.viewmode.force")); break;
            case NONE:   setMessage(Component.translatable("skipsign.setting.viewmode.none")); break;
        }
    }
}
