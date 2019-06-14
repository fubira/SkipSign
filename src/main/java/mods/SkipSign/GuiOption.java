package mods.skipsign;

import java.io.IOException;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class GuiOption extends GuiScreen
{
    public void drawScreen(int par1, int par2, float par3)
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
        
        super.drawScreen(par1, par2, par3);
    }

    private GuiButton drawMode, ShowBoard, ChangeKey, ChangeMode, ZoomKey, ScrGui;
    private GuiButton ApplySign, ApplyItemFrame, ApplyChest, ApplySkull;
    private GuiButton SignDO, ChestDO, SkullDO;
    private boolean KeyChange_OpenSetting = false;
    private boolean KeyChange_ZoomKey = false;

    public void initGui()
    {
        FontRenderer fontRenderer = this.fontRenderer;
        
        this.buttonList.clear();

        int x = this.width / 2;
        int y = this.height / 2;
        
        SignDO = new GuiButton(0, x + 65, y - 75, 120, 20, "範囲外を描画しない");
        ChestDO = new GuiButton(0, x + 65, y - 25, 120, 20, "範囲外を描画しない");
        SkullDO = new GuiButton(0, x + 65, y , 120, 20, "範囲外を描画しない");
        
        ScrGui = new GuiButton(0, x - 172, y - 5, 195, 20, "画面外のチェスト・看板を非表示"); 

        drawMode = new GuiButton(0, x - 172, y - 75, 60, 20, "範囲描画");
        
        ApplySign = new GuiButton(0, x - 120, y - 75, 75, 20, "範囲描画");
        ApplyItemFrame = new GuiButton(0, x - 120, y - 50, 75, 20, "範囲描画");
        ApplyChest = new GuiButton(0, x - 120, y - 25, 75, 20, "範囲描画");
        ApplySkull = new GuiButton(0, x - 120, y , 75, 20, "範囲描画");
        
        GuiOptionSliderEx Srange = new GuiOptionSliderEx(5, x - 40, y - 75, "描画範囲", SkipSignCore.ModSetting.SignRange, (float)SkipSignCore.ModSetting.Hurihaba);
        GuiOptionSliderEx Frange = new GuiOptionSliderEx(5, x - 40, y - 50, "描画範囲", SkipSignCore.ModSetting.FrameRange, (float)SkipSignCore.ModSetting.Hurihaba);
        GuiOptionSliderEx Crange = new GuiOptionSliderEx(5, x - 40, y - 25, "描画範囲", SkipSignCore.ModSetting.ChestRange, (float)SkipSignCore.ModSetting.Hurihaba);
        GuiOptionSliderEx SKrange = new GuiOptionSliderEx(5, x - 40, y, "描画範囲", SkipSignCore.ModSetting.SkullRange, (float)SkipSignCore.ModSetting.Hurihaba);
        
        //AllDraw = new GuiButton(1, x - 107, y - 75, 60, 20, "すべて描画");
        //SkipDraw = new GuiButton(2, x - 42, y - 75, 60, 20, "描画しない");

        ChangeKey = new GuiButton(3, x - 77, y + 45, 100, 20, String.format("設定画面:%s", org.lwjgl.input.Keyboard.getKeyName((SkipSignCore.ModSetting.VisibleKey.Int()))));
        ShowBoard = new GuiButton(4, x - 172, y + 45, 90, 20, "本体を表示");

        ChangeMode = new GuiButton(6, x - 172, y + 70, 90, 20, "距離算出式:0");

        ZoomKey = new GuiButton(7, x - 77, y + 70, 100, 20, String.format("一時解除:%s", org.lwjgl.input.Keyboard.getKeyName(SkipSignCore.ModSetting.VisibleKey.Int())));


        Invalidate();

        this.buttonList.add(ApplySign);
        this.buttonList.add(ApplyItemFrame);
        this.buttonList.add(ApplyChest);
        this.buttonList.add(ApplySkull);
        
        this.buttonList.add(Srange);
        this.buttonList.add(Frange);
        this.buttonList.add(Crange);
        this.buttonList.add(SKrange);
        
        this.buttonList.add(SignDO);
        this.buttonList.add(ChestDO);
        this.buttonList.add(SkullDO);
        
        this.buttonList.add(ChangeKey);
        this.buttonList.add(ShowBoard);
        this.buttonList.add(ChangeMode);
        this.buttonList.add(ZoomKey);
    }
    
    

    protected void actionPerformed(GuiButton p_146284_1_)
    {
        KeyChange_OpenSetting = false;
        KeyChange_ZoomKey = false;
        
        if (ApplySign == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.SignVisible.Int();
            vis++; if (vis > 2) vis = 0;
            SkipSignCore.ModSetting.SignVisible.Value = vis;
        }
        
        if (ApplyItemFrame == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.FrameVisible.Int();
            vis++; if (vis > 2) vis = 0;
            SkipSignCore.ModSetting.FrameVisible.Value = vis;
        }
        
        if (ApplyChest == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.ChestVisible.Int();
            vis++; if (vis > 2) vis = 0;
            SkipSignCore.ModSetting.ChestVisible.Value = vis;
        }
        
        if (ApplySkull == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.SkullVisible.Int();
            vis++; if (vis > 2) vis = 0;
            SkipSignCore.ModSetting.SkullVisible.Value = vis;
        }
        
        if (SignDO == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.DropOffSign.Int();
            vis++; if (vis > 1) vis = 0;
            SkipSignCore.ModSetting.DropOffSign.Value = vis;
        }
        
        if (ChestDO == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.DropOffChest.Int();
            vis++; if (vis > 1) vis = 0;
            SkipSignCore.ModSetting.DropOffChest.Value = vis;
        }
        
        if (SkullDO == p_146284_1_)
        {
            int vis = SkipSignCore.ModSetting.DropOffSkull.Int();
            vis++; if (vis > 1) vis = 0;
            SkipSignCore.ModSetting.DropOffSkull.Value = vis;
        }
        
        if (p_146284_1_.id == 4)
        {
            SkipSignCore.ModSetting.HideBoard.Value = !SkipSignCore.ModSetting.HideBoard.Bool();
        }

        if (p_146284_1_.id == 3)
        {
            KeyChange_OpenSetting = !KeyChange_OpenSetting;
        }
        
        if (p_146284_1_.id == 7)
        {
            KeyChange_ZoomKey = !KeyChange_ZoomKey;
        }

        if (p_146284_1_.id == 6)
        {
            SkipSignCore.ModSetting.CheckDist.Value = ((SkipSignCore.ModSetting.CheckDist.Int() == 0)) ?  1 : 0;
        }

        Invalidate();
    }

    private void Invalidate()
    {
        switch (SkipSignCore.ModSetting.SignVisible.Int())
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
        
        switch (SkipSignCore.ModSetting.FrameVisible.Int())
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
        
        switch (SkipSignCore.ModSetting.ChestVisible.Int())
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
        
        switch (SkipSignCore.ModSetting.SkullVisible.Int())
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
        
        switch (SkipSignCore.ModSetting.DropOffSign.Int())
        {
        case 0:
            SignDO.displayString = "範囲外を描画する";
            break;
        case 1:
            SignDO.displayString = "範囲外を描画しない";
            break;
        }
        
        switch (SkipSignCore.ModSetting.DropOffChest.Int())
        {
        case 0:
            ChestDO.displayString = "範囲外を描画する";
            break;
        case 1:
            ChestDO.displayString = "範囲外を描画しない";
            break;
        }

        switch (SkipSignCore.ModSetting.DropOffSkull.Int())
        {
        case 0:
            SkullDO.displayString = "範囲外を描画する";
            break;
        case 1:
            SkullDO.displayString = "範囲外を描画しない";
            break;
        }

        if (SkipSignCore.ModSetting.HideBoard.Bool())
        {
            ShowBoard.displayString = "背景を非表示";
        }
        else
        {
            ShowBoard.displayString = "背景を表示";
        }

        if (KeyChange_OpenSetting)
        {
            ChangeKey.displayString = "キーを入力してください";
        }
        else
        {
            ChangeKey.displayString = String.format("設定画面:%s", org.lwjgl.input.Keyboard.getKeyName(SkipSignCore.ModSetting.VisibleKey.Int()));
        }
        
        if (KeyChange_ZoomKey)
        {
            ZoomKey.displayString = "キーを入力してください";
        }
        else
        {
            ZoomKey.displayString = String.format("一時解除:%s", org.lwjgl.input.Keyboard.getKeyName(SkipSignCore.ModSetting.Zoom_Key.Int()));
        }

        ChangeMode.displayString = String.format("距離算出式:%d", SkipSignCore.ModSetting.CheckDist.Int());
    }

    public void func_146281_b()
    {
        SkipSignCore.SaveConfig();
    }

    protected void keyTyped(char par1, int par2)
    {
        try {
            super.keyTyped(par1, par2);
        } catch(IOException e) {
            FMLLog.log(Level.FATAL, "keyTyped: %s", e.toString());
        }
        
        if (KeyChange_ZoomKey && par2 != 1)
        {
            SkipSignCore.ModSetting.Zoom_Key.Value = par2;

            KeyChange_ZoomKey = false;
        }

        if (KeyChange_OpenSetting && par2 != 1)
        {
            SkipSignCore.ModSetting.VisibleKey.Value = par2;

            KeyChange_OpenSetting = false;
        }

        Invalidate();
    }
}
