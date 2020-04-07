package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;

import com.mojang.blaze3d.matrix.MatrixStack;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class ChestTileEntityRendererEx<T extends TileEntity & IChestLid> extends ChestTileEntityRenderer<T>
{
    public ChestTileEntityRendererEx(TileEntityRendererDispatcher rendererDispatcher)
    {
        super(rendererDispatcher);
    }

    @Override
    public void render(T entity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!Config.enableMod.get() || Minecraft.getInstance().player == null || entity.getWorld() == null || isVisible(entity)) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(T tileEntityChest)
    {
        if (Config.viewModeChest.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeChest.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntityChest),
                Config.viewRangeChest.get())) {
            return true;
        }

        return false;
    }
}
