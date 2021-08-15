package mods.skipsign.fabric.client.gui.widget;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ModToggleButton extends Button {
  private boolean value;

  public ModToggleButton(int x, int y, int width, int height, boolean defaultValue) {
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

  public boolean getValue() {
      return this.value;
  }

  public void update() {
      setMessage(value
          ? new TranslatableComponent("skipsign.setting.mod.isenabled").withStyle(ChatFormatting.YELLOW)
          : new TranslatableComponent("skipsign.setting.mod.isdisabled").withStyle(ChatFormatting.GRAY)
      );
  }
}
