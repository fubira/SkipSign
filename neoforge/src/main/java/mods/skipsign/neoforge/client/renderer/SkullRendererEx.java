package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class SkullRendererEx extends SkullBlockRenderer
{
    public SkullRendererEx(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SkullBlockEntity entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!NeoForgeConfig.enableMod.get() || entity.getBlockPos() == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(entity))
        {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(SkullBlockEntity entity)
    {
        if (NeoForgeConfig.viewModeSkull.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeSkull.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), NeoForgeConfig.viewRangeSkull.get())) {
            return true;
        }

        return false;
    }
}
