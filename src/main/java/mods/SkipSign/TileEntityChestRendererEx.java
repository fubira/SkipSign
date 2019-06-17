package mods.skipsign;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import mods.skipsign.SkipSignCore;
import mods.skipsign.SkipSignHelper;

public class TileEntityChestRendererEx<T extends TileEntity & IChestLid> extends TileEntityChestRenderer<T>
{
    public TileEntityChestRendererEx()
    {
        super();
    }

    @Override
    public void render(T entity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!isDropOff(entity, x, y, z))
            return;

        if (Minecraft.getInstance().player == null || entity.getWorld() == null ||
            CheckVisibleState(entity))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        }
    }

    public boolean isDropOff(T tile, double x, double y, double z)
    {
        return true;
    }

    public boolean CheckVisibleState(T tileEntityChest)
    {
        if (SkipSignConfig.GENERAL.chestViewMode.get() == 1)
            return true;
        if (SkipSignConfig.GENERAL.chestViewMode.get() == 2)
            return false;

        if (InputMappings.isKeyDown(SkipSignConfig.GENERAL.zoomKeyId.get()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntityChest),
                SkipSignConfig.GENERAL.chestVisibleRange.get()))
            return true;

        return false;
    }
}
