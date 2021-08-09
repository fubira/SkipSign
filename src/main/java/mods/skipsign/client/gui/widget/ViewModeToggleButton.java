package mods.skipsign.client.gui.widget;

import mods.skipsign.ViewMode;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ViewModeToggleButton extends Button {
    private ViewMode value;

    public ViewModeToggleButton(int x, int y, int width, int height, ViewMode defaultValue) {
        super(x, y, width, height, TextComponent.EMPTY, button -> {});
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
            case NORMAL: setMessage(new TranslatableComponent("skipsign.setting.viewmode.normal")); break;
            case FORCE:  setMessage(new TranslatableComponent("skipsign.setting.viewmode.force")); break;
            case NONE:   setMessage(new TranslatableComponent("skipsign.setting.viewmode.none")); break;
        }
    }
}
