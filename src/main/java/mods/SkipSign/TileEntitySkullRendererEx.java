package mods.SkipSign;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mods.SkipSign.SkipSignCore;
import mods.SkipSign.SkipSignHelper;
import mods.SkipSign.DrawableApi;

@SideOnly(Side.CLIENT)
public class TileEntitySkullRendererEx extends TileEntitySkullRenderer
{
    public TileEntitySkullRendererEx()
    {
        super();
    }

    @Override
    public void render(TileEntitySkull entity, double x, double y, double z, float partialTicks, int destroyStage, float partial)
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

    public boolean CheckVisibleState(TileEntitySkull tileEntitySkull)
    {
        if (SkipSignCore.ModSetting.SkullVisible.Int() == 1)
            return true;
        if (SkipSignCore.ModSetting.SkullVisible.Int() == 2)
            return false;

        if (Keyboard.isKeyDown(SkipSignCore.ModSetting.Zoom_Key.Int()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntitySkull),
                SkipSignCore.ModSetting.SkullRange.Int()))
            return true;

        return false;
    }
}
