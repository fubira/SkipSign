package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class TileEntitySkullRendererEx extends TileEntitySkullRenderer
{
    public TileEntitySkullRendererEx()
    {
        super();
    }

    @Override
    public void render(TileEntitySkull entity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!isDropOff(entity, x, y, z))
            return;

        if (Minecraft.getInstance().player == null || entity.getWorld() == null ||
            CheckVisibleState(entity))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        }
    }

    public boolean isDropOff(TileEntity tile, double x, double y, double z)
    {
        return true;
    }

    public boolean CheckVisibleState(TileEntitySkull tileEntitySkull)
    {
        if (Config.viewModeSkull.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSkull.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySkull),
                Config.viewRangeSkull.get()))
            return true;

        return false;
    }
}
