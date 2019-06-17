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

    private class OptionSlider extends GuiSlider {
        public OptionSlider(int id, int x, int y, int w, int h, String prefix, float minValue, float maxValue, float value, @Nullable GuiSlider.ISlider par) {
            super(id, x, y, w, h, prefix, "", minValue, maxValue, value, true, true, par);
        }
    }

    private final GuiScreen parentScreen;
	
	public GuiConfigScreen(GuiScreen parentScreen){
		this.parentScreen = parentScreen;
    }

    private OptionButton ShowBoard, ScrGui;
    private OptionButton ApplySign, ApplyItemFrame, ApplyChest, ApplySkull;
    private OptionButton SignDO, ChestDO, SkullDO;
    private GuiSlider signRange, frameRange, skullRange, chestRange;
    private static int maxRange = 128;


    @Override
    public void initGui()
    {
        super.initGui();
        this.buttons.clear();

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;
    
        SignDO          = addButton(new OptionButton(0, left + 225, top +  25, 120, 20, I18n.format("setting.renderarea.range"), this::onButtonClicked));
        ChestDO         = addButton(new OptionButton(0, left + 225, top +  75, 120, 20, I18n.format("setting.renderarea.range"), this::onButtonClicked));
        SkullDO         = addButton(new OptionButton(0, left + 225, top + 100, 120, 20, I18n.format("setting.renderarea.range"), this::onButtonClicked));

        ApplySign       = addButton(new OptionButton(0, left +  55, top +   25, 60, 20, I18n.format("setting.viewmode.normal"), this::onButtonClicked));
        ApplyItemFrame  = addButton(new OptionButton(0, left +  55, top +   50, 60, 20, I18n.format("setting.viewmode.normal"), this::onButtonClicked));
        ApplyChest      = addButton(new OptionButton(0, left +  55, top +   75, 60, 20, I18n.format("setting.viewmode.normal"), this::onButtonClicked));
        ApplySkull      = addButton(new OptionButton(0, left +  55, top +  100, 60, 20, I18n.format("setting.viewmode.normal"), this::onButtonClicked));

        /*
        signRange       = addButton(new GuiOptionSliderEx(5, left + 120, top +  25, Config.viewRangeSign.get(), (float)maxRange));
        frameRange      = addButton(new GuiOptionSliderEx(5, left + 120, top +  50, Config.viewRangeFrame.get(), (float)maxRange));
        chestRange      = addButton(new GuiOptionSliderEx(5, left + 120, top +  75, Config.viewRangeChest.get(), (float)maxRange));
        skullRange      = addButton(new GuiOptionSliderEx(5, left + 120, top + 100, Config.viewRangeSkull.get(), (float)maxRange));
        */
        signRange       = addButton(new GuiSlider(5, left + 120, top +  25, 100, 20, I18n.format("setting.slider.range.prefix"), I18n.format("setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSign.get(), true, true, this));
        frameRange      = addButton(new GuiSlider(5, left + 120, top +  50, 100, 20, I18n.format("setting.slider.range.prefix"), I18n.format("setting.slider.range.postfix"), 0, maxRange, Config.viewRangeFrame.get(), true, true, this));
        chestRange      = addButton(new GuiSlider(5, left + 120, top +  75, 100, 20, I18n.format("setting.slider.range.prefix"), I18n.format("setting.slider.range.postfix"), 0, maxRange, Config.viewRangeChest.get(), true, true, this));
        skullRange      = addButton(new GuiSlider(5, left + 120, top + 100, 100, 20, I18n.format("setting.slider.range.prefix"), I18n.format("setting.slider.range.postfix"), 0, maxRange, Config.viewRangeSkull.get(), true, true, this));

        ShowBoard       = addButton(new OptionButton(4, left +   0, top + 140, 100, 20, I18n.format("setting.framebase.show"), this::onButtonClicked));
        update();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        FontRenderer fontRenderer = this.fontRenderer;

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;
        
        this.drawDefaultBackground();

        this.drawString(fontRenderer, I18n.format("setting.title")             , left, top          , 0xffffff);
        this.drawString(fontRenderer, I18n.format("setting.description.sign")  , left, top +  25 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("setting.description.frame") , left, top +  50 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("setting.description.chest") , left, top +  75 + 5, 0xffffff);
        this.drawString(fontRenderer, I18n.format("setting.description.skull") , left, top + 100 + 5, 0xffffff);
        
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
        if (btn == ApplySign) {
            SkipSignMod.config.set(Config.viewModeSign, toggleViewMode(Config.viewModeSign.get()));
        }        
        if (btn == ApplyItemFrame) {
            SkipSignMod.config.set(Config.viewModeFrame, toggleViewMode(Config.viewModeFrame.get()));
        }
        if (btn == ApplyChest) {
            SkipSignMod.config.set(Config.viewModeChest, toggleViewMode(Config.viewModeChest.get()));
        }
        if (btn == ApplySkull) {
            SkipSignMod.config.set(Config.viewModeSkull, toggleViewMode(Config.viewModeSkull.get()));
        }
        
        if (btn == SignDO) {
            SkipSignMod.config.set(Config.dropOffSign, !Config.dropOffSign.get());
        }
        if (btn == ChestDO) {
            SkipSignMod.config.set(Config.dropOffChest, !Config.dropOffChest.get());
        }
        if (btn == SkullDO) {
            SkipSignMod.config.set(Config.dropOffSkull, !Config.dropOffSkull.get());
        }
        
        if (btn.id == 4) {
            SkipSignMod.config.set(Config.dropOffFrameBase, !Config.dropOffFrameBase.get());
        }

        if (btn == skullRange) {
            SkipSignMod.config.set(Config.viewRangeSkull, skullRange.getValueInt());
        }
        if (btn == signRange) {
            SkipSignMod.config.set(Config.viewRangeSign, signRange.getValueInt());
        }
        if (btn == frameRange) {
            SkipSignMod.config.set(Config.viewRangeFrame, frameRange.getValueInt());
        }
        if (btn == chestRange) {
            SkipSignMod.config.set(Config.viewRangeChest, chestRange.getValueInt());
        }
        update();

    }

    public void onChangeSliderValue(GuiSlider slider) {
        SkipSignMod.logger.info(slider.getValue());
    }

    private void update()
    {
        switch (Config.viewModeSign.get()) {
            case NORMAL: ApplySign.displayString = I18n.format("setting.viewmode.normal"); break;
            case FORCE:  ApplySign.displayString = I18n.format("setting.viewmode.force"); break;
            case NONE:   ApplySign.displayString = I18n.format("setting.viewmode.none"); break;
        }
        switch (Config.viewModeFrame.get()) {
            case NORMAL: ApplyItemFrame.displayString = I18n.format("setting.viewmode.normal"); break;
            case FORCE:  ApplyItemFrame.displayString = I18n.format("setting.viewmode.force"); break;
            case NONE:   ApplyItemFrame.displayString = I18n.format("setting.viewmode.none"); break;
        }
        switch (Config.viewModeChest.get()) {
            case NORMAL: ApplyChest.displayString = I18n.format("setting.viewmode.normal"); break;
            case FORCE:  ApplyChest.displayString = I18n.format("setting.viewmode.force"); break;
            case NONE:   ApplyChest.displayString = I18n.format("setting.viewmode.none"); break;
        }
        switch (Config.viewModeSkull.get()) {
            case NORMAL: ApplySkull.displayString = I18n.format("setting.viewmode.normal"); break;
            case FORCE:  ApplySkull.displayString = I18n.format("setting.viewmode.force"); break;
            case NONE:   ApplySkull.displayString = I18n.format("setting.viewmode.none"); break;
        }
        
        SignDO.displayString = Config.dropOffSign.get() ? I18n.format("setting.renderarea.range") : I18n.format("setting.renderarea.full");
        ChestDO.displayString = Config.dropOffChest.get() ? I18n.format("setting.renderarea.range") : I18n.format("setting.renderarea.full");
        SkullDO.displayString = Config.dropOffSkull.get() ? I18n.format("setting.renderarea.range") : I18n.format("setting.renderarea.full");
        ShowBoard.displayString = Config.dropOffFrameBase.get() ? I18n.format("setting.framebase.show") : I18n.format("setting.framebase.hide");
    }
}
