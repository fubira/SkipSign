package mods.skipsign.forge.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.screens.Screen;
import mods.skipsign.forge.ForgeConfig;
import mods.skipsign.forge.client.gui.widget.FrameDropOffModeButton;
import mods.skipsign.forge.client.gui.widget.ModToggleButton;
import mods.skipsign.forge.client.gui.widget.SignDropOffModeButton;
import mods.skipsign.forge.client.gui.widget.ViewModeToggleButton;
import mods.skipsign.forge.client.gui.widget.ViewRangeSlider;

@OnlyIn(Dist.CLIENT)
public class GuiConfigScreen extends Screen
{
    public GuiConfigScreen(TextComponent titleIn){
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

        btnEnableMod        = addRenderableWidget(new ModToggleButton(left + 100, top, 100, 20, ForgeConfig.enableMod.get()));
        btnViewModeSign     = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   50, 60, 20, ForgeConfig.viewModeSign.get()));
        btnViewModeFrame    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +   75, 60, 20, ForgeConfig.viewModeFrame.get()));
        btnViewModeChest    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  100, 60, 20, ForgeConfig.viewModeChest.get()));
        btnViewModeSkull    = addRenderableWidget(new ViewModeToggleButton(left +  55, top +  125, 60, 20, ForgeConfig.viewModeSkull.get()));

        sliderRangeSign     = addRenderableWidget(new ViewRangeSlider(left + 120, top +  50, 128, 20, maxRange, ForgeConfig.viewRangeSign.get()));
        sliderRangeFrame    = addRenderableWidget(new ViewRangeSlider(left + 120, top +  75, 128, 20, maxRange, ForgeConfig.viewRangeFrame.get()));
        sliderRangeChest    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 100, 128, 20, maxRange, ForgeConfig.viewRangeChest.get()));
        sliderRangeSkull    = addRenderableWidget(new ViewRangeSlider(left + 120, top + 125, 128, 20, maxRange, ForgeConfig.viewRangeSkull.get()));

        btnOutofRangeSign   = addRenderableWidget(new SignDropOffModeButton(left + 253, top +  50, 130, 20, ForgeConfig.dropOffSignBoard.get()));
        btnOutofRangeFrame  = addRenderableWidget(new FrameDropOffModeButton(left + 253, top +  75, 130, 20, ForgeConfig.dropOffFrameBoard.get()));
    }

    @Override
    public void onClose() {
        ForgeConfig.enableMod.set(btnEnableMod.getValue());

        ForgeConfig.viewModeSign.set(btnViewModeSign.getValue());
        ForgeConfig.viewModeChest.set(btnViewModeChest.getValue());
        ForgeConfig.viewModeFrame.set(btnViewModeFrame.getValue());
        ForgeConfig.viewModeSkull.set(btnViewModeSkull.getValue());

        ForgeConfig.viewRangeChest.set(sliderRangeChest.getValue());
        ForgeConfig.viewRangeSign.set(sliderRangeSign.getValue());
        ForgeConfig.viewRangeFrame.set(sliderRangeFrame.getValue());
        ForgeConfig.viewRangeSkull.set(sliderRangeSkull.getValue());

        ForgeConfig.dropOffFrameBoard.set(btnOutofRangeFrame.getValue());
        ForgeConfig.dropOffSignBoard.set(btnOutofRangeSign.getValue());
        super.onClose();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        int left = (this.width - 380) / 2;
        int top = (this.height - 200) / 2;

        renderBackground(matrixStack);

        drawString(matrixStack, font, new TranslatableComponent("skipsign.setting.title")             , left, top          , 0xffffff);
        drawString(matrixStack, font, new TranslatableComponent("skipsign.setting.description.sign")  , left, top +  50 + 5, 0xffffff);
        drawString(matrixStack, font, new TranslatableComponent("skipsign.setting.description.frame") , left, top +  75 + 5, 0xffffff);
        drawString(matrixStack, font, new TranslatableComponent("skipsign.setting.description.chest") , left, top + 100 + 5, 0xffffff);
        drawString(matrixStack, font, new TranslatableComponent("skipsign.setting.description.skull") , left, top + 125 + 5, 0xffffff);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
