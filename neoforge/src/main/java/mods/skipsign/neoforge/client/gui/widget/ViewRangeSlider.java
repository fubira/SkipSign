package mods.skipsign.neoforge.client.gui.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ViewRangeSlider extends AbstractSliderButton {
  private final Integer maxValue; 
  private final Integer minValue; 
  private Integer sliderValue;
  private final Component prefix;
  private final Component postfix;

  public ViewRangeSlider(int x, int y, int width, int height, Integer maxValue, Integer defaultValue) {
      super(x, y, width, height, Component.empty(), 0.0D);

      this.minValue = 0;
      this.maxValue = maxValue;
      this.sliderValue = defaultValue;
      this.value = (((double)Mth.clamp(defaultValue, this.minValue, this.maxValue) - this.minValue) / (this.maxValue - this.minValue));
      this.prefix = Component.translatable("skipsign.setting.slider.range.prefix");
      this.postfix = Component.translatable("skipsign.setting.slider.range.postfix");
      updateMessage();
  }

  public Integer getValue() {
      return this.sliderValue;
  }

  private void updateValue() {
    this.sliderValue = (int)Mth.lerp(Mth.clamp(this.value, 0.0D, 1.0D), this.minValue, this.maxValue);
    this.value = (((double)Mth.clamp(this.sliderValue, this.minValue, this.maxValue) - this.minValue) / (this.maxValue - this.minValue));
}

  public void applyValue() {
      updateValue();
  }

  @Override
  protected void updateMessage() {
      this.setMessage(Component.literal(prefix.getString()).append("" + sliderValue).append(postfix));
  }
}