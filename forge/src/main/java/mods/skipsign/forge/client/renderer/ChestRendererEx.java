package mods.skipsign.forge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.forge.ForgeConfig;
import mods.skipsign.forge.SkipSignMod;
import mods.skipsign.forge.ViewMode;

public class ChestRendererEx<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T>
{
    public ChestRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(T entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!ForgeConfig.enableMod.get() || entity.getBlockPos() == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(entity)) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(T entity)
    {
        if (ForgeConfig.viewModeChest.get() == ViewMode.FORCE) {
            return true;
        }
        if (ForgeConfig.viewModeChest.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), ForgeConfig.viewRangeChest.get())) {
            return true;
        }

        return false;
    }
}
