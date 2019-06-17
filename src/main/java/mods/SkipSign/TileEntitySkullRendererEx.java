package mods.skipsign;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;

import mods.skipsign.SkipSignCore;
import mods.skipsign.SkipSignHelper;

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
        if (SkipSignConfig.GENERAL.skullViewMode.get() == 1)
            return true;
        if (SkipSignConfig.GENERAL.skullViewMode.get() == 2)
            return false;

        if (InputMappings.isKeyDown(SkipSignConfig.GENERAL.zoomKeyId.get()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntitySkull),
                SkipSignConfig.GENERAL.skullVisibleRange.get()))
            return true;

        return false;
    }
}
