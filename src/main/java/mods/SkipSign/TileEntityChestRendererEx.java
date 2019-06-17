package mods.skipsign;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import mods.skipsign.SkipSignCore;
import mods.skipsign.SkipSignHelper;

public class TileEntityChestRendererEx extends TileEntityChestRenderer
{
    public TileEntityChestRendererEx()
    {
        super();
    }

    @Override
    public void render(TileEntityChest entity, double x, double y, double z, float partialTicks, int destroyStage, float partial)
    {
        if (!isDropOff(entity, x, y, z))
            return;

        if (Minecraft.getMinecraft().player == null || entity.getWorld() == null ||
            CheckVisibleState(entity))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage, partial);
        }
    }

    public boolean isDropOff(TileEntity tile, double x, double y, double z)
    {
        return true;
    }

    public boolean CheckVisibleState(TileEntityChest tileEntityChest)
    {
        if (SkipSignCore.ModSetting.ChestVisible.Int() == 1)
            return true;
        if (SkipSignCore.ModSetting.ChestVisible.Int() == 2)
            return false;

        if (Keyboard.isKeyDown(SkipSignCore.ModSetting.Zoom_Key.Int()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntityChest),
                SkipSignCore.ModSetting.ChestRange.Int()))
            return true;

        return false;
    }
}
