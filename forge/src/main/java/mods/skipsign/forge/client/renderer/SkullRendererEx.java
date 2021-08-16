package mods.skipsign.forge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.forge.ForgeConfig;
import mods.skipsign.forge.SkipSignMod;
import mods.skipsign.forge.ViewMode;

public class SkullRendererEx extends SkullBlockRenderer
{
    public SkullRendererEx(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SkullBlockEntity entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!ForgeConfig.enableMod.get() || entity.getBlockPos() == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(entity))
        {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(SkullBlockEntity entity)
    {
        if (ForgeConfig.viewModeSkull.get() == ViewMode.FORCE) {
            return true;
        }
        if (ForgeConfig.viewModeSkull.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), ForgeConfig.viewRangeSkull.get())) {
            return true;
        }

        return false;
    }
}
