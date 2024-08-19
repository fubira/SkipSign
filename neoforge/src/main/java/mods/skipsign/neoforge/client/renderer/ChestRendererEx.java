package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class ChestRendererEx<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T>
{
    public ChestRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(T entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!NeoForgeConfig.enableMod.get() || entity.getBlockPos() == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(entity)) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }

    public boolean isVisible(T entity)
    {
        if (NeoForgeConfig.viewModeChest.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeChest.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), NeoForgeConfig.viewRangeChest.get())) {
            return true;
        }

        return false;
    }
}
