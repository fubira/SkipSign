package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.SkullTileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class SkullTileEntityRendererEx extends SkullTileEntityRenderer
{
    public SkullTileEntityRendererEx(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
    }

    @Override
    public void render(SkullTileEntity entity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!Config.enableMod.get() || Minecraft.getInstance().player == null || entity.getWorld() == null || isVisible(entity))
        {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(SkullTileEntity tileEntitySkull)
    {
        if (Config.viewModeSkull.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSkull.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySkull),
                Config.viewRangeSkull.get()))
            return true;

        return false;
    }
}
