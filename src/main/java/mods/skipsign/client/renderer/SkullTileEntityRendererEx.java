package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.SkullTileEntity;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class SkullTileEntityRendererEx extends SkullTileEntityRenderer
{
    public SkullTileEntityRendererEx()
    {
        super();
    }

    @Override
    public void render(SkullTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!Config.enableMod.get() || Minecraft.getInstance().player == null || entity.getWorld() == null || isVisible(entity))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        }
    }

    public boolean isVisible(SkullTileEntity tileEntitySkull)
    {
        if (Config.viewModeSkull.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSkull.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputMappings.isKeyDown(mc.mainWindow.getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySkull),
                Config.viewRangeSkull.get()))
            return true;

        return false;
    }
}
