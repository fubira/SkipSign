package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.HangingSignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

public class HangingSignRendererEx extends HangingSignRenderer
{
    public HangingSignRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void extractRenderState(SignBlockEntity entity, HangingSignRenderState renderState, float partialTicks, Vec3 pos, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay)
    {
        super.extractRenderState(entity, renderState, partialTicks, pos, crumblingOverlay);

        if (SkipSignMod.config.enableMod && !isVisible(entity)) {
            renderState.frontText = new SignText();
            renderState.backText = new SignText();
        }
    }

    @Override
    public void submit(HangingSignRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (SkipSignMod.config.enableMod && SkipSignMod.config.dropOffSignBoard && !isVisible(renderState.blockPos)) {
            return;
        }

        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public boolean isVisible(SignBlockEntity entity)
    {
        if (SkipSignMod.config.viewModeSign == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeSign == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }
    
        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), SkipSignMod.config.viewRangeSign)) {
            return true;
        }

        return false;
    }

    public boolean isVisible(BlockPos pos)
    {
        if (SkipSignMod.config.viewModeSign == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeSign == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            SkipSignMod.config.viewRangeSign
        );
    }
}
