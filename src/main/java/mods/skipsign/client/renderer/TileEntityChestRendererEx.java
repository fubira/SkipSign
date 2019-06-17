package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import mods.skipsign.Config;
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
        if (!Config.enableMod.get() || Minecraft.getInstance().player == null || entity.getWorld() == null || isVisible(entity))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        }
    }

    public boolean isVisible(T tileEntityChest)
    {
        if (Config.viewModeChest.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeChest.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntityChest),
                Config.viewRangeChest.get()))
            return true;

        return false;
    }
}
