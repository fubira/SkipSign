package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

public class SkullRendererEx extends SkullBlockRenderer
{
    public SkullRendererEx(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(SkullBlockRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState)
    {
        if (!SkipSignMod.config.enableMod || renderState.blockPos == BlockPos.ZERO || Minecraft.getInstance().player == null || isVisible(renderState.blockPos))
        {
            super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    public boolean isVisible(SkullBlockEntity entity)
    {
        if (SkipSignMod.config.viewModeSkull == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeSkull == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), SkipSignMod.config.viewRangeSkull)) {
            return true;
        }

        return false;
    }

    public boolean isVisible(BlockPos pos)
    {
        if (SkipSignMod.config.viewModeSkull == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeSkull == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            SkipSignMod.config.viewRangeSkull
        );
    }
}
