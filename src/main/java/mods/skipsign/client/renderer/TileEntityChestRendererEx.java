package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import mods.skipsign.Config;
import mods.skipsign.SkipSignHelper;
import mods.skipsign.ViewMode;

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
        if (Config.viewModeChest.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeChest.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntityChest),
                Config.viewRangeChest.get()))
            return true;

        return false;
    }
}
