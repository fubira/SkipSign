package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

public class ChestRendererEx<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T>
{
    public ChestRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void submit(ChestRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Minecraft client = Minecraft.getInstance();
        if (!SkipSignMod.config.enableMod || renderState.blockPos == BlockPos.ZERO || client.player == null || isVisible(renderState.blockPos)) {
            super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
        }
    }

    public boolean isVisible(T entity)
    {
        if (SkipSignMod.config.viewModeChest == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeChest == ViewMode.NONE) {
            return false;
        }
        
        if (SkipSignMod.client.isZooming()) {
            return true;
        }
        
        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToBlockEntity(entity),
                SkipSignMod.config.viewRangeChest)) {
            return true;
        }
        
        return false;
    }

    public boolean isVisible(BlockPos pos)
    {
        if (SkipSignMod.config.viewModeChest == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeChest == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            SkipSignMod.config.viewRangeChest
        );
    }
}
