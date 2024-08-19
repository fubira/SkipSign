package mods.skipsign.fabric.client.gui;

// import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;
import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.client.gui.widget.FrameDropOffModeButton;
import mods.skipsign.fabric.client.gui.widget.ModToggleButton;
import mods.skipsign.fabric.client.gui.widget.SignDropOffModeButton;
import mods.skipsign.fabric.client.gui.widget.ViewModeToggleButton;
import mods.skipsign.fabric.client.gui.widget.ViewRangeSlider;

public class GuiConfigScreen extends Screen
{
    public GuiConfigScreen(Component titleIn){
        super(titleIn);
    }

    private ModToggleButton btnEnableMod;
    private SignDropOffModeButton btnOutofRangeSign;
    private FrameDropOffModeButton btnOutofRangeFrame;
    private ViewModeToggleButton btnViewModeSign;
    private ViewModeToggleButton btnViewModeFrame;
    private ViewModeToggleButton btnViewModeChest;
    private ViewModeToggleButton btnViewModeSkull;

    private ViewRangeSlider sliderRangeSign;
    private ViewRangeSlider sliderRangeFrame;
    private ViewRangeSlider sliderRangeSkull;
    private ViewRangeSlider sliderRangeChest;

    private static final int maxRange = 64;

    @Override
    protected void init()
    {
        super.init();
        this.clearWidgets();

        int left = (this.width - 380) / 2;
        int top = (this.height - 200) / 2;

        btnEnableMod        = addRenderableWidget(new ModToggleButton(left + 100, top, 100, 20, SkipSignMod.config.enableMod));
        btnViewModeSign     = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   50, 60, 20, SkipSignMod.config.viewModeSign));
        btnViewModeFrame    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   75, 60, 20, SkipSignMod.config.viewModeFrame));
        btnViewModeChest    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  100, 60, 20, SkipSignMod.config.viewModeChest));
        btnViewModeSkull    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  125, 60, 20, SkipSignMod.config.viewModeSkull));

        sliderRangeSign     = addRenderableWidget(new ViewRangeSlider(left + 120, top +  50, 128, 20, maxRange, SkipSignMod.config.viewRangeSign));
        sliderRangeFrame    = addRenderableWidget(new ViewRangeSlider(left + 120, top +  75, 128, 20, maxRange, SkipSignMod.config.viewRangeFrame));
        sliderRangeChest    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 100, 128, 20, maxRange, SkipSignMod.config.viewRangeChest));
        sliderRangeSkull    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 125, 128, 20, maxRange, SkipSignMod.config.viewRangeSkull));

        btnOutofRangeSign   = addRenderableWidget(new SignDropOffModeButton(left + 253, top +  50, 130, 20, SkipSignMod.config.dropOffSignBoard));
        btnOutofRangeFrame  = addRenderableWidget(new FrameDropOffModeButton(left + 253, top +  75, 130, 20, SkipSignMod.config.dropOffFrameBoard));
    }

    @Override
    public void onClose() {
        SkipSignMod.config.enableMod = btnEnableMod.getValue();

        SkipSignMod.config.viewModeSign = btnViewModeSign.getValue();
        SkipSignMod.config.viewModeChest = btnViewModeChest.getValue();
        SkipSignMod.config.viewModeFrame = btnViewModeFrame.getValue();
        SkipSignMod.config.viewModeSkull = btnViewModeSkull.getValue();

        SkipSignMod.config.viewRangeChest = sliderRangeChest.getValue();
        SkipSignMod.config.viewRangeSign = sliderRangeSign.getValue();
        SkipSignMod.config.viewRangeFrame = sliderRangeFrame.getValue();
        SkipSignMod.config.viewRangeSkull = sliderRangeSkull.getValue();

        SkipSignMod.config.dropOffFrameBoard = btnOutofRangeFrame.getValue();
        SkipSignMod.config.dropOffSignBoard = btnOutofRangeSign.getValue();
        SkipSignMod.config.save();
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        int left = (this.width - 380) / 2;
        int top = (this.height - 200) / 2;

        // renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        
        guiGraphics.drawString(font, Component.translatable("skipsign.setting.title")             , left, top          , 0xffffff);
        guiGraphics.drawString(font, Component.translatable("skipsign.setting.description.sign")  , left, top +  50 + 5, 0xffffff);
        guiGraphics.drawString(font, Component.translatable("skipsign.setting.description.frame") , left, top +  75 + 5, 0xffffff);
        guiGraphics.drawString(font, Component.translatable("skipsign.setting.description.chest") , left, top + 100 + 5, 0xffffff);
        guiGraphics.drawString(font, Component.translatable("skipsign.setting.description.skull") , left, top + 125 + 5, 0xffffff);
        
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}
