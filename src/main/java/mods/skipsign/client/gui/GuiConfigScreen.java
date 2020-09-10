package mods.skipsign.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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

    // protected void init()
    @Override
    protected void func_231160_c_()
    {
        super.func_231160_c_();
        this.field_230710_m_.clear();

        int left = (this.field_230708_k_ - 360) / 2;    // this.width
        int top = (this.field_230709_l_ - 200) / 2;     // this.height

        // this.addButton
        btnViewModeSign     = this.func_230480_a_(new ExtendedButton(left +  55, top +   25, 60, 20, new TranslationTextComponent("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeFrame    = this.func_230480_a_(new ExtendedButton(left +  55, top +   50, 60, 20, new TranslationTextComponent("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeChest    = this.func_230480_a_(new ExtendedButton(left +  55, top +   75, 60, 20, new TranslationTextComponent("skipsign.setting.viewmode.normal"), this::onPress));
        btnViewModeSkull    = this.func_230480_a_(new ExtendedButton(left +  55, top +  100, 60, 20, new TranslationTextComponent("skipsign.setting.viewmode.normal"), this::onPress));

        sliderRangeSign     = this.func_230480_a_(new Slider(left + 120, top +  25, 128, 20, new TranslationTextComponent("skipsign.setting.slider.range.prefix"), new TranslationTextComponent("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSign.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeFrame    = this.func_230480_a_(new Slider(left + 120, top +  50, 128, 20, new TranslationTextComponent("skipsign.setting.slider.range.prefix"), new TranslationTextComponent("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeFrame.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeChest    = this.func_230480_a_(new Slider(left + 120, top +  75, 128, 20, new TranslationTextComponent("skipsign.setting.slider.range.prefix"), new TranslationTextComponent("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeChest.get(), false, true, this::onPress, this::onChangeSliderValue));
        sliderRangeSkull    = this.func_230480_a_(new Slider(left + 120, top + 100, 128, 20, new TranslationTextComponent("skipsign.setting.slider.range.prefix"), new TranslationTextComponent("skipsign.setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSkull.get(), false, true, this::onPress, this::onChangeSliderValue));

        btnOutofRangeSign   = this.func_230480_a_(new ExtendedButton(left + 253, top +  25, 120, 20, new TranslationTextComponent("skipsign.setting.outofrange.sign.show"), this::onPress));
        btnOutofRangeFrame  = this.func_230480_a_(new ExtendedButton(left + 253, top +  50, 120, 20, new TranslationTextComponent("skipsign.setting.outofrange.frame.show"), this::onPress));

        btnEnableMod        = this.func_230480_a_(new ExtendedButton(left, top + 140, 100, 20, new TranslationTextComponent("skipsign.setting.mod.enable"), this::onPress));
        update();
    }

    // public void render()
    @Override
    public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontRenderer = mc.fontRenderer;

        int left = (this.field_230708_k_ - 380) / 2;    // this.width
        int top = (this.field_230709_l_ - 200) / 2;     // this.height
        
        // this.renderBackground
        this.func_230446_a_(matrixStack);

        // this.drawString
        this.func_238475_b_(matrixStack, fontRenderer, new TranslationTextComponent("skipsign.setting.title")             , left, top          , 0xffffff);
        this.func_238475_b_(matrixStack, fontRenderer, new TranslationTextComponent("skipsign.setting.description.sign")  , left, top +  25 + 5, 0xffffff);
        this.func_238475_b_(matrixStack, fontRenderer, new TranslationTextComponent("skipsign.setting.description.frame") , left, top +  50 + 5, 0xffffff);
        this.func_238475_b_(matrixStack, fontRenderer, new TranslationTextComponent("skipsign.setting.description.chest") , left, top +  75 + 5, 0xffffff);
        this.func_238475_b_(matrixStack, fontRenderer, new TranslationTextComponent("skipsign.setting.description.skull") , left, top + 100 + 5, 0xffffff);
        
        super.func_230430_a_(matrixStack, mouseX, mouseY, partialTicks);
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
            case NORMAL: btnViewModeSign.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeSign.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeSign.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeFrame.get()) {
            case NORMAL: btnViewModeFrame.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeFrame.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeFrame.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeChest.get()) {
            case NORMAL: btnViewModeChest.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeChest.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeChest.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.none")); break;
        }
        switch (Config.viewModeSkull.get()) {
            case NORMAL: btnViewModeSkull.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.normal")); break;
            case FORCE:  btnViewModeSkull.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.force")); break;
            case NONE:   btnViewModeSkull.func_238482_a_(new TranslationTextComponent("skipsign.setting.viewmode.none")); break;
        }
        
        btnOutofRangeSign.func_238482_a_(Config.dropOffSignBoard.get() ? new TranslationTextComponent("skipsign.setting.outofrange.sign.hide") : new TranslationTextComponent("skipsign.setting.outofrange.sign.show"));
        btnOutofRangeFrame.func_238482_a_(Config.dropOffFrameBoard.get() ? new TranslationTextComponent("skipsign.setting.outofrange.frame.hide") : new TranslationTextComponent("skipsign.setting.outofrange.frame.show"));

        btnEnableMod.func_238482_a_(Config.enableMod.get() ? new TranslationTextComponent("skipsign.setting.mod.isenabled") : new TranslationTextComponent("skipsign.setting.mod.isdisabled"));
    }
}
