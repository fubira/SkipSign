package mods.skipsign.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import mods.skipsign.Config;
import mods.skipsign.SkipSignMod;
import mods.skipsign.ViewMode;

@OnlyIn(Dist.CLIENT)
public class GuiConfigScreen extends Screen
{
    private final Screen parentScreen;

    public GuiConfigScreen(Screen parentScreen, ITextComponent titleIn){
        super(titleIn);
        this.parentScreen = parentScreen;
    }

    private ExtendedButton btnEnableMod;
    private ExtendedButton btnOutofRangeSign;
    private ExtendedButton btnOutofRangeFrame;
    private ExtendedButton btnViewModeSign;
    private ExtendedButton btnViewModeFrame;
    private ExtendedButton btnViewModeChest;
    private ExtendedButton btnViewModeSkull;

    private Slider sliderRangeSign;
    private Slider sliderRangeFrame;
    private Slider sliderRangeSkull;
    private Slider sliderRangeChest;

    private static final int maxRange = 64;

    @Override
    public void init()
    {
        super.init();
        this.buttons.clear();

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;

        btnViewModeSign     = addButton(new ExtendedButton(left +  55, top +   25, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeFrame    = addButton(new ExtendedButton(left +  55, top +   50, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeChest    = addButton(new ExtendedButton(left +  55, top +   75, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeSkull    = addButton(new ExtendedButton(left +  55, top +  100, 60, 20, I18n.format("skipsign.setting.viewmode.normal"), this::onPress));

        sliderRangeSign     = addButton(new Slider(left + 120, top +  25, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSign.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeFrame    = addButton(new Slider(left + 120, top +  50, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeFrame.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeChest    = addButton(new Slider(left + 120, top +  75, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeChest.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeSkull    = addButton(new Slider(left + 120, top + 100, 128, 20, I18n.format("skipsign.setting.slider.range.prefix"), I18n.format("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSkull.get(), false, true, this::onPress, this::onChangeSliderValue));

        btnOutofRangeSign   = addButton(new ExtendedButton(left + 253, top +  25, 120, 20, I18n.format("skipsign.setting.outofrange.sign.show"), this::onPress));
        btnOutofRangeFrame  = addButton(new ExtendedButton(left + 253, top +  50, 120, 20, I18n.format("skipsign.setting.outofrange.frame.show"), this::onPress));

        btnEnableMod        = addButton(new ExtendedButton(left, top + 140, 100, 20, I18n.format("skipsign.setting.mod.enable"), this::onPress));
        update();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;

        int left = (this.width - 380) / 2;
        int top = (this.height - 200) / 2;
        
        this.renderBackground();

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
    
    public void onPress(Button btn) {
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

    public void onChangeSliderValue(Button slider) {
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
            case NORMAL: btnViewModeSign.setMessage(I18n.format("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeSign.setMessage(I18n.format("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeSign.setMessage(I18n.format("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeFrame.get()) {
            case NORMAL: btnViewModeFrame.setMessage(I18n.format("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeFrame.setMessage(I18n.format("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeFrame.setMessage(I18n.format("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeChest.get()) {
            case NORMAL: btnViewModeChest.setMessage(I18n.format("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeChest.setMessage(I18n.format("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeChest.setMessage(I18n.format("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeSkull.get()) {
            case NORMAL: btnViewModeSkull.setMessage(I18n.format("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeSkull.setMessage(I18n.format("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeSkull.setMessage(I18n.format("skipsign.setting.viewmode.none")); break;
        }
        
        btnOutofRangeSign.setMessage(Config.dropOffSignBoard.get() ? I18n.format("skipsign.setting.outofrange.sign.hide") : I18n.format("skipsign.setting.outofrange.sign.show"));
        btnOutofRangeFrame.setMessage(Config.dropOffFrameBoard.get() ? I18n.format("skipsign.setting.outofrange.frame.hide") : I18n.format("skipsign.setting.outofrange.frame.show"));

        btnEnableMod.setMessage(Config.enableMod.get() ? I18n.format("skipsign.setting.mod.isenabled") : I18n.format("skipsign.setting.mod.isdisabled"));
    }
}
