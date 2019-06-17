package mods.skipsign.client.gui;

import java.io.IOException;
import java.util.function.Consumer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import mods.skipsign.Config;
import mods.skipsign.SkipSignConfig;
import mods.skipsign.SkipSignMod;
import mods.skipsign.ViewMode;

@OnlyIn(Dist.CLIENT)
public class GuiConfigScreen extends GuiScreen
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

    private OptionButton ShowBoard, ScrGui;
    private OptionButton ApplySign, ApplyItemFrame, ApplyChest, ApplySkull;
    private OptionButton SignDO, ChestDO, SkullDO;

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttons.clear();

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;
        
        SignDO          = addButton(new OptionButton(0, left + 225, top +  25, 120, 20, "範囲外を描画しない", this::onButtonClicked));
        ChestDO         = addButton(new OptionButton(0, left + 225, top +  75, 120, 20, "範囲外を描画しない", this::onButtonClicked));
        SkullDO         = addButton(new OptionButton(0, left + 225, top + 100, 120, 20, "範囲外を描画しない", this::onButtonClicked));

        ApplySign       = addButton(new OptionButton(0, left +  55, top +   25, 60, 20, "範囲描画", this::onButtonClicked));
        ApplyItemFrame  = addButton(new OptionButton(0, left +  55, top +   50, 60, 20, "範囲描画", this::onButtonClicked));
        ApplyChest      = addButton(new OptionButton(0, left +  55, top +   75, 60, 20, "範囲描画", this::onButtonClicked));
        ApplySkull      = addButton(new OptionButton(0, left +  55, top +  100, 60, 20, "範囲描画", this::onButtonClicked));

        signRange       = addButton(new GuiOptionSliderEx(5, left + 120, top +  25, "描画範囲", Config.viewRangeSign.get(), (float)maxRange));
        frameRange      = addButton(new GuiOptionSliderEx(5, left + 120, top +  50, "描画範囲", Config.viewRangeFrame.get(), (float)maxRange));
        chestRange      = addButton(new GuiOptionSliderEx(5, left + 120, top +  75, "描画範囲", Config.viewRangeChest.get(), (float)maxRange));
        skullRange      = addButton(new GuiOptionSliderEx(5, left + 120, top + 100, "描画範囲", Config.viewRangeSkull.get(), (float)maxRange));

        ShowBoard       = addButton(new OptionButton(4, left +   0, top + 140, 100, 20, "本体を表示", this::onButtonClicked));
        update();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        FontRenderer fontRenderer = this.fontRenderer;

        int left = (this.width - 360) / 2;
        int top = (this.height - 200) / 2;
        
        this.drawDefaultBackground();

        this.drawString(fontRenderer, "SkipSign config", left, top          , 0xffffff);
        this.drawString(fontRenderer, "看板"           , left, top +  25 + 5, 0xffffff);
        this.drawString(fontRenderer, "フレーム"       , left, top +  50 + 5, 0xffffff);
        this.drawString(fontRenderer, "チェスト"       , left, top +  75 + 5, 0xffffff);
        this.drawString(fontRenderer, "ヘッド"         , left, top + 100 + 5, 0xffffff);
        
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
            SkipSignMod.config.set(Config.viewRangeSkull, skullRange.getValue());
        }
        if (btn == signRange) {
            SkipSignMod.config.set(Config.viewRangeSign, signRange.getValue());
        }
        if (btn == frameRange) {
            SkipSignMod.config.set(Config.viewRangeFrame, frameRange.getValue());
        }
        if (btn == chestRange) {
            SkipSignMod.config.set(Config.viewRangeChest, chestRange.getValue());
        }
        update();

    }
    

    private GuiOptionSliderEx signRange, frameRange, skullRange, chestRange;
    private static int maxRange = 128;

    private void update()
    {
        switch (Config.viewModeSign.get()) {
            case NORMAL: ApplySign.displayString = "範囲描画"; break;
            case FORCE:  ApplySign.displayString = "すべて描画"; break;
            case NONE:   ApplySign.displayString = "描画しない"; break;
        }
        switch (Config.viewModeFrame.get()) {
            case NORMAL: ApplyItemFrame.displayString = "範囲描画"; break;
            case FORCE:  ApplyItemFrame.displayString = "すべて描画"; break;
            case NONE:   ApplyItemFrame.displayString = "描画しない"; break;
        }
        switch (Config.viewModeChest.get()) {
            case NORMAL: ApplyChest.displayString = "範囲描画"; break;
            case FORCE:  ApplyChest.displayString = "すべて描画"; break;
            case NONE:   ApplyChest.displayString = "描画しない"; break;
        }
        switch (Config.viewModeSkull.get()) {
            case NORMAL: ApplySkull.displayString = "範囲描画"; break;
            case FORCE:  ApplySkull.displayString = "すべて描画"; break;
            case NONE:   ApplySkull.displayString = "描画しない"; break;
        }
        
        SignDO.displayString = Config.dropOffSign.get() ? "範囲外を描画しない" : "範囲外を描画する";
        ChestDO.displayString = Config.dropOffChest.get() ? "範囲外を描画しない" : "範囲外を描画する";
        SkullDO.displayString = Config.dropOffSkull.get() ? "範囲外を描画しない" : "範囲外を描画する";
        ShowBoard.displayString = Config.dropOffFrameBase.get() ? "背景を非表示" : "背景を表示";
    }
}
