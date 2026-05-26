package mods.skipsign.fabric.client.gui.widget;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

public class ModToggleButton extends Button {
  private boolean value;

  public ModToggleButton(int x, int y, int width, int height, boolean defaultValue) {
    super(x, y, width, height, Component.empty(), button -> {}, Button.DEFAULT_NARRATION);
    this.value = defaultValue;
    update();
  }

  @Override
  public void onPress(InputWithModifiers input) {
    this.value = !this.value;
    update();
    super.onPress(input);
  }

  @Override
  protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
    extractDefaultSprite(guiGraphics);
    extractDefaultLabel(guiGraphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.TOOLTIP_AND_CURSOR));
  }

  public boolean getValue() {
    return this.value;
  }

  public void update() {
    setMessage(value
        ? Component.translatable("skipsign.setting.mod.isenabled").withStyle(ChatFormatting.YELLOW)
        : Component.translatable("skipsign.setting.mod.isdisabled").withStyle(ChatFormatting.GRAY)
    );
  }
}
