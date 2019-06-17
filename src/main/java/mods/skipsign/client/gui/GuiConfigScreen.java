package mods.skipsign.client.gui;

import java.io.IOException;
import java.util.function.Consumer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import mods.skipsign.Config;
import mods.skipsign.SkipSignMod;

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

    private OptionButton DrawMode, ShowBoard, ChangeKey, ZoomKey, ScrGui;
    private OptionButton ApplySign, ApplyItemFrame, ApplyChest, ApplySkull;
    private OptionButton SignDO, ChestDO, SkullDO;

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttons.clear();

        int x = this.width / 2;
        int y = this.height / 2;
        
        SignDO = addButton(new OptionButton(0, x + 65, y - 75, 120, 20, "範囲外を描画しない", this::onButtonClicked));
        ChestDO = addButton(new OptionButton(0, x + 65, y - 25, 120, 20, "範囲外を描画しない", this::onButtonClicked));
        SkullDO = addButton(new OptionButton(0, x + 65, y , 120, 20, "範囲外を描画しない", this::onButtonClicked));
        ScrGui = addButton(new OptionButton(0, x - 172, y - 5, 195, 20, "画面外のチェスト・看板を非表示", this::onButtonClicked));
        DrawMode = addButton(new OptionButton(0, x - 172, y - 75, 60, 20, "範囲描画", this::onButtonClicked));
        ApplySign = addButton(new OptionButton(0, x - 120, y - 75, 75, 20, "範囲描画", this::onButtonClicked));
        ApplyItemFrame = addButton(new OptionButton(0, x - 120, y - 50, 75, 20, "範囲描画", this::onButtonClicked));
        ApplyChest = addButton(new OptionButton(0, x - 120, y - 25, 75, 20, "範囲描画", this::onButtonClicked));
        ApplySkull = addButton(new OptionButton(0, x - 120, y , 75, 20, "範囲描画", this::onButtonClicked));

        signRange = addButton(new GuiOptionSliderEx(5, x - 40, y - 75, "描画範囲", Config.viewRangeSign.get(), (float)maxRange));
        frameRange = addButton(new GuiOptionSliderEx(5, x - 40, y - 50, "描画範囲", Config.viewRangeFrame.get(), (float)maxRange));
        chestRange = addButton(new GuiOptionSliderEx(5, x - 40, y - 25, "描画範囲", Config.viewRangeChest.get(), (float)maxRange));
        skullRange = addButton(new GuiOptionSliderEx(5, x - 40, y, "描画範囲", Config.viewRangeSkull.get(), (float)maxRange));
        //AllDraw = new GuiButton(1, x - 107, y - 75, 60, 20, "すべて描画");
        //SkipDraw = new GuiButton(2, x - 42, y - 75, 60, 20, "描画しない");

        ChangeKey = addButton(new OptionButton(3, x - 77, y + 45, 100, 20, String.format("設定画面:%s", InputMappings.getInputByCode(Config.keyCodeVisible.get(), 0).getName()), this::onButtonClicked));
        ShowBoard = addButton(new OptionButton(4, x - 172, y + 45, 90, 20, "本体を表示", this::onButtonClicked));
        ZoomKey = addButton(new OptionButton(7, x - 77, y + 70, 100, 20, String.format("一時解除:%s", InputMappings.getInputByCode(Config.keyCodeZoom.get(), 0).getName()), this::onButtonClicked));

        update();
    }
    
	private void onButtonClicked(GuiButton btn) {
        SkipSignMod.logger.info(btn.id);
    }
    

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        FontRenderer fontRenderer = this.fontRenderer;
        int x = this.width / 2;
        int y = this.height / 2;
        
        this.drawDefaultBackground();

        this.drawString(fontRenderer, "環境設定", x - 172, y - 90, 16777215);
        
        this.drawString(fontRenderer, "看板", x - 172, y - 75 + 5, 16777215);
        this.drawString(fontRenderer, "フレーム", x - 172, y - 50 + 5, 16777215);
        this.drawString(fontRenderer, "チェスト", x - 172, y - 25 + 5, 16777215);
        this.drawString(fontRenderer, "ヘッド", x - 172, y + 5, 16777215);
        
        super.render(mouseX, mouseY, partialTicks);
    }

    private boolean KeyChange_OpenSetting = false;
    private boolean KeyChange_ZoomKey = false;
    private GuiOptionSliderEx signRange, frameRange, skullRange, chestRange;
    private static int maxRange = 128;

    
    protected void actionPerformed(GuiButton button)
    {
        KeyChange_OpenSetting = false;
        KeyChange_ZoomKey = false;
        /*
        if (ApplySign == button)
        {
            int vis = SkipSignConfig.GENERAL.signViewMode.get();
            vis++; if (vis > 2) vis = 0;
            SkipSignConfig.GENERAL.signViewMode = vis;
        }
        
        if (ApplyItemFrame == button)
        {
            int vis = SkipSignConfig.GENERAL.frameViewMode.get();
            vis++; if (vis > 2) vis = 0;
            SkipSignConfig.GENERAL.frameViewMode = vis;
        }
        
        if (ApplyChest == button)
        {
            int vis = SkipSignConfig.GENERAL.chestViewMode.get();
            vis++; if (vis > 2) vis = 0;
            SkipSignConfig.GENERAL.chestViewMode = vis;
        }
        
        if (ApplySkull == button)
        {
            int vis = SkipSignConfig.GENERAL.skullViewMode.get();
            vis++; if (vis > 2) vis = 0;
            SkipSignConfig.GENERAL.skullViewMode = vis;
        }
        
        if (SignDO == button)
        {
            SkipSignConfig.GENERAL.dropoffSign = !SkipSignConfig.GENERAL.dropoffSign;
        }
        
        if (ChestDO == button)
        {
            SkipSignConfig.GENERAL.dropoffChest = !SkipSignConfig.GENERAL.dropoffChest;
        }
        
        if (SkullDO == button)
        {
            SkipSignConfig.GENERAL.dropoffSkull = !SkipSignConfig.GENERAL.dropoffSkull;
        }
        
        if (button.id == 4)
        {
            SkipSignConfig.GENERAL.hideInvisibleFrameBoard = !SkipSignConfig.GENERAL.hideInvisibleFrameBoard;
        }

        if (button.id == 3)
        {
            KeyChange_OpenSetting = !KeyChange_OpenSetting;
        }
        
        if (button.id == 7)
        {
            KeyChange_ZoomKey = !KeyChange_ZoomKey;
        }

        if(button == skullRange) {
            SkipSignConfig.GENERAL.skullVisibleRange = skullRange.getValue();
        }
        if(button == signRange) {
            SkipSignConfig.GENERAL.signVisibleRange = signRange.getValue();
        }
        if(button == frameRange) {
            SkipSignConfig.GENERAL.frameVisibleRange = frameRange.getValue();
        }
        if(button == chestRange) {
            SkipSignConfig.GENERAL.chestVisibleRange = chestRange.getValue();
        }*/

        update();
    }

    private void update()
    {
        /*
        switch (SkipSignConfig.GENERAL.signViewMode.get())
        {
        case 0:
            ApplySign.displayString = "範囲描画";
            break;
        case 1:
            ApplySign.displayString = "すべて描画";
            break;
        case 2:
            ApplySign.displayString = "描画しない";
            break;
        }
        
        switch (SkipSignConfig.GENERAL.frameViewMode.get())
        {
        case 0:
            ApplyItemFrame.displayString = "範囲描画";
            break;
        case 1:
            ApplyItemFrame.displayString = "すべて描画";
            break;
        case 2:
            ApplyItemFrame.displayString = "描画しない";
            break;
        }
        
        switch (SkipSignConfig.GENERAL.chestViewMode.get())
        {
        case 0:
            ApplyChest.displayString = "範囲描画";
            break;
        case 1:
            ApplyChest.displayString = "すべて描画";
            break;
        case 2:
            ApplyChest.displayString = "描画しない";
            break;
        }
        
        switch (SkipSignConfig.GENERAL.skullViewMode.get())
        {
        case 0:
            ApplySkull.displayString = "範囲描画";
            break;
        case 1:
            ApplySkull.displayString = "すべて描画";
            break;
        case 2:
            ApplySkull.displayString = "描画しない";
            break;
        }
        
        if (SkipSignConfig.GENERAL.dropoffSign.get()) {
            SignDO.displayString = "範囲外を描画しない";
        } else {
            SignDO.displayString = "範囲外を描画する";
        }

        if (SkipSignConfig.GENERAL.dropoffChest.get()) {
            ChestDO.displayString = "範囲外を描画しない";
        } else {
            ChestDO.displayString = "範囲外を描画する";
        }

        if (SkipSignConfig.GENERAL.dropoffSkull.get()) {
            SkullDO.displayString = "範囲外を描画しない";
        } else {
            SkullDO.displayString = "範囲外を描画する";
        }

        if (SkipSignConfig.GENERAL.hideInvisibleFrameBoard.get()) {
            ShowBoard.displayString = "背景を非表示";
        } else {
            ShowBoard.displayString = "背景を表示";
        }

        if (KeyChange_OpenSetting) {
            ChangeKey.displayString = "キーを入力してください";
        } else {
            ChangeKey.displayString = String.format("設定画面:%s", InputMappings.getInputByCode(SkipSignConfig.GENERAL.visibleKeyId.get(), 0).getName());
        }
        
        if (KeyChange_ZoomKey) {
            ZoomKey.displayString = "キーを入力してください";
        } else {
            ZoomKey.displayString = String.format("一時解除:%s", InputMappings.getInputByCode(SkipSignConfig.GENERAL.zoomKeyId.get(), 0).getName());
        }
        */
    }

    public boolean charTyped(char par1, int par2)
    {
        boolean result = false;
        /*
        try {
            result = super.charTyped(par1, par2);
        } catch(IOException e) {
            ModLog.Error("charTyped: " + e.toString());
        }
        
        if (KeyChange_ZoomKey && par2 != 1)
        {
            SkipSignConfig.GENERAL.zoomKeyId = par2;
            KeyChange_ZoomKey = false;
        }

        if (KeyChange_OpenSetting && par2 != 1)
        {
            SkipSignConfig.GENERAL.visibleKeyId = par2;
            KeyChange_OpenSetting = false;
        }*/

        update();
        return result;
    }
}
