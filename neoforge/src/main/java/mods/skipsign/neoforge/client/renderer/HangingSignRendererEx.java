package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.state.HangingSignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.phys.Vec3;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

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

        if (NeoForgeConfig.enableMod.get() && !isVisible(entity)) {
            renderState.frontText = new SignText();
            renderState.backText = new SignText();
        }
    }

    @Override
    public void submit(HangingSignRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (NeoForgeConfig.enableMod.get() && NeoForgeConfig.dropOffSignBoard.get() && !isVisible(renderState.blockPos)) {
            return;
        }

        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public boolean isVisible(SignBlockEntity entity)
    {
        return isVisible(entity.getBlockPos());
    }

    public boolean isVisible(BlockPos pos)
    {
        if (NeoForgeConfig.viewModeSign.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeSign.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToBlockPos(pos),
            NeoForgeConfig.viewRangeSign.get()
        );
    }
}
