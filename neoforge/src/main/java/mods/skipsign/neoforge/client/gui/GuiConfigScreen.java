package mods.skipsign.neoforge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.Screen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.client.gui.widget.FrameDropOffModeButton;
import mods.skipsign.neoforge.client.gui.widget.ModToggleButton;
import mods.skipsign.neoforge.client.gui.widget.SignDropOffModeButton;
import mods.skipsign.neoforge.client.gui.widget.ViewModeToggleButton;
import mods.skipsign.neoforge.client.gui.widget.ViewRangeSlider;

@OnlyIn(Dist.CLIENT)
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

        btnEnableMod        = addRenderableWidget(new ModToggleButton(left + 100, top, 100, 20, NeoForgeConfig.enableMod.get()));
        btnViewModeSign     = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   50, 60, 20, NeoForgeConfig.viewModeSign.get()));
        btnViewModeFrame    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   75, 60, 20, NeoForgeConfig.viewModeFrame.get()));
        btnViewModeChest    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  100, 60, 20, NeoForgeConfig.viewModeChest.get()));
        btnViewModeSkull    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  125, 60, 20, NeoForgeConfig.viewModeSkull.get()));

        sliderRangeSign     = addRenderableWidget(new ViewRangeSlider(left + 120, top +  50, 128, 20, maxRange, NeoForgeConfig.viewRangeSign.get()));
        sliderRangeFrame    = addRenderableWidget(new ViewRangeSlider(left + 120, top +  75, 128, 20, maxRange, NeoForgeConfig.viewRangeFrame.get()));
        sliderRangeChest    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 100, 128, 20, maxRange, NeoForgeConfig.viewRangeChest.get()));
        sliderRangeSkull    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 125, 128, 20, maxRange, NeoForgeConfig.viewRangeSkull.get()));

        btnOutofRangeSign   = addRenderableWidget(new SignDropOffModeButton(left + 253, top +  50, 130, 20, NeoForgeConfig.dropOffSignBoard.get()));
        btnOutofRangeFrame  = addRenderableWidget(new FrameDropOffModeButton(left + 253, top +  75, 130, 20, NeoForgeConfig.dropOffFrameBoard.get()));
    }

    @Override
    public void onClose() {
        NeoForgeConfig.enableMod.set(btnEnableMod.getValue());

        NeoForgeConfig.viewModeSign.set(btnViewModeSign.getValue());
        NeoForgeConfig.viewModeChest.set(btnViewModeChest.getValue());
        NeoForgeConfig.viewModeFrame.set(btnViewModeFrame.getValue());
        NeoForgeConfig.viewModeSkull.set(btnViewModeSkull.getValue());

        NeoForgeConfig.viewRangeChest.set(sliderRangeChest.getValue());
        NeoForgeConfig.viewRangeSign.set(sliderRangeSign.getValue());
        NeoForgeConfig.viewRangeFrame.set(sliderRangeFrame.getValue());
        NeoForgeConfig.viewRangeSkull.set(sliderRangeSkull.getValue());

        NeoForgeConfig.dropOffFrameBoard.set(btnOutofRangeFrame.getValue());
        NeoForgeConfig.dropOffSignBoard.set(btnOutofRangeSign.getValue());

        NeoForgeConfig.SPEC.save();
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

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
    }
}
