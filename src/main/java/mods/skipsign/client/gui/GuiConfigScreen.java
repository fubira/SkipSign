package mods.skipsign.client.gui;

import java.io.IOException;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.config.GuiSlider;

import mods.skipsign.Config;
import mods.skipsign.SkipSignConfig;
import mods.skipsign.SkipSignMod;
import mods.skipsign.ViewMode;

@OnlyIn(Dist.CLIENT)
public class GuiConfigScreen extends GuiScreen implements GuiSlider.ISlider
{
    private class OptionButton extends GuiButton {
        private final Consumer<OptionButton> clickHandler;

        public OptionButton(int id, int x, int y, int w, int h, String label, Consumer<OptionButton> clickHandler) {
            super(id, x, y, w, h, label);
            this.clickHandler = clickHandler;
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            super.onClick(mouseX, mouseY);
            clickHandler.accept(this);
        }
    }

    private final GuiScreen parentScreen;
	
	public GuiConfigScreen(GuiScreen parentScreen){
		this.parentScreen = parentScreen;
    }

    private OptionButton btnEnableMod;
    private OptionButton btnOutofRangeSign;
    private OptionButton btnOutofRangeFrame;
    private OptionButton btnViewModeSign;
    private OptionButton btnViewModeFrame;
    private OptionButton btnViewModeChest;
    private OptionButton btnViewModeSkull;

    private GuiSlider sliderRangeSign;
    private GuiSlider sliderRangeFrame;
    private GuiSlider sliderRangeSkull;
    private GuiSlider sliderRangeChest;

    private static final int maxRange = 128;

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttons.clear();

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;

        btnViewModeSign     = addButton(new OptionButton(0, left +  55, top +   25, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onButtonClicked));
        btnViewModeFrame    = addButton(new OptionButton(0, left +  55, top +   50, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onButtonClicked));
        btnViewModeChest    = addButton(new OptionButton(0, left +  55, top +   75, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onButtonClicked));
        btnViewModeSkull    = addButton(new OptionButton(0, left +  55, top +  100, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onButtonClicked));

        sliderRangeSign     = addButton(new GuiSlider(5, left + 120, top +  25, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSign.get(), false, true, this));
        sliderRangeFrame    = addButton(new GuiSlider(5, left + 120, top +  50, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeFrame.get(), false, true, this));
        sliderRangeChest    = addButton(new GuiSlider(5, left + 120, top +  75, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeChest.get(), false, true, this));
        sliderRangeSkull    = addButton(new GuiSlider(5, left + 120, top + 100, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSkull.get(), false, true, this));

        btnOutofRangeSign   = addButton(new OptionButton(0, left + 253, top +  25, 120, 20, I18n.format("skipsign.setting.outofrange.sign.show"), this::onButtonClicked));
        btnOutofRangeFrame  = addButton(new OptionButton(0, left + 253, top +  50, 120, 20, I18n.format("skipsign.setting.outofrange.frame.show"), this::onButtonClicked));

        btnEnableMod        = addButton(new OptionButton(0, left, top + 140, 100, 20, I18n.format("skipsign.setting.mod.enable"), this::onButtonClicked));
        update();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        FontRenderer fontRenderer = this.fontRenderer;

        int left = (this.width - 380) / 2;
        int top = (this.height - 200) / 2;
        
        this.drawDefaultBackground();

        this.drawString(fontRenderer, I18n.format("skipsign.setting.title")             , left, top          , 0xffffff);
        this.drawString(fontRenderer, I18n.format("skipsign.setting.description.sign")  , left, top +  25 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("skipsign.setting.description.frame") , left, top +  50 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("skipsign.setting.description.chest") , left, top +  75 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("skipsign.setting.description.skull") , left, top + 100 + 5, 0xffffff);
        
        super.render(mouseX, mouseY, partialTicks);
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
    
	private void onButtonClicked(GuiButton btn) {
        if (btn == btnViewModeSign) {
            SkipSignMod.config.set(Config.viewModeSign, toggleViewMode(Config.viewModeSign.get()));
        }        
        if (btn == btnViewModeFrame) {
            SkipSignMod.config.set(Config.viewModeFrame, toggleViewMode(Config.viewModeFrame.get()));
        }
        if (btn == btnViewModeChest) {
            SkipSignMod.config.set(Config.viewModeChest, toggleViewMode(Config.viewModeChest.get()));
        }
        if (btn == btnViewModeSkull) {
            SkipSignMod.config.set(Config.viewModeSkull, toggleViewMode(Config.viewModeSkull.get()));
        }
        
        if (btn == btnOutofRangeSign) {
            SkipSignMod.config.set(Config.dropOffSignBoard, !Config.dropOffSignBoard.get());
        }
        if (btn == btnOutofRangeFrame) {
            SkipSignMod.config.set(Config.dropOffFrameBoard, !Config.dropOffFrameBoard.get());
        }

        if (btn == btnEnableMod) {
            SkipSignMod.config.set(Config.enableMod, !Config.enableMod.get());
        }
        update();

    }

    public void onChangeSliderValue(GuiSlider slider) {
        if (slider == sliderRangeSkull && Config.viewRangeSkull.get() != sliderRangeSkull.getValueInt()) {
            SkipSignMod.config.set(Config.viewRangeSkull, sliderRangeSkull.getValueInt());
        }
        if (slider == sliderRangeSign && Config.viewRangeSign.get() != sliderRangeSign.getValueInt()) {
            SkipSignMod.config.set(Config.viewRangeSign, sliderRangeSign.getValueInt());
        }
        if (slider == sliderRangeFrame && Config.viewRangeFrame.get() != sliderRangeFrame.getValueInt()) {
            SkipSignMod.config.set(Config.viewRangeFrame, sliderRangeFrame.getValueInt());
        }
        if (slider == sliderRangeChest && Config.viewRangeChest.get() != sliderRangeChest.getValueInt()) {
            SkipSignMod.config.set(Config.viewRangeChest, sliderRangeChest.getValueInt());
        }
        update();
    }

    private void update()
    {
        switch (Config.viewModeSign.get()) {
            case NORMAL: btnViewModeSign.displayString = I18n.format("skipsign.setting.viewmode.normal"); break;
            case FORCE:  btnViewModeSign.displayString = I18n.format("skipsign.setting.viewmode.force"); break;
            case NONE:   btnViewModeSign.displayString = I18n.format("skipsign.setting.viewmode.none"); break;
        }
        switch (Config.viewModeFrame.get()) {
            case NORMAL: btnViewModeFrame.displayString = I18n.format("skipsign.setting.viewmode.normal"); break;
            case FORCE:  btnViewModeFrame.displayString = I18n.format("skipsign.setting.viewmode.force"); break;
            case NONE:   btnViewModeFrame.displayString = I18n.format("skipsign.setting.viewmode.none"); break;
        }
        switch (Config.viewModeChest.get()) {
            case NORMAL: btnViewModeChest.displayString = I18n.format("skipsign.setting.viewmode.normal"); break;
            case FORCE:  btnViewModeChest.displayString = I18n.format("skipsign.setting.viewmode.force"); break;
            case NONE:   btnViewModeChest.displayString = I18n.format("skipsign.setting.viewmode.none"); break;
        }
        switch (Config.viewModeSkull.get()) {
            case NORMAL: btnViewModeSkull.displayString = I18n.format("skipsign.setting.viewmode.normal"); break;
            case FORCE:  btnViewModeSkull.displayString = I18n.format("skipsign.setting.viewmode.force"); break;
            case NONE:   btnViewModeSkull.displayString = I18n.format("skipsign.setting.viewmode.none"); break;
        }
        
        btnOutofRangeSign.displayString = Config.dropOffSignBoard.get() ? I18n.format("skipsign.setting.outofrange.sign.hide") : I18n.format("skipsign.setting.outofrange.sign.show");
        btnOutofRangeFrame.displayString = Config.dropOffFrameBoard.get() ? I18n.format("skipsign.setting.outofrange.frame.hide") : I18n.format("skipsign.setting.outofrange.frame.show");

        btnEnableMod.displayString = Config.enableMod.get() ? I18n.format("skipsign.setting.mod.isenabled") : I18n.format("skipsign.setting.mod.isdisabled");
    }
}
