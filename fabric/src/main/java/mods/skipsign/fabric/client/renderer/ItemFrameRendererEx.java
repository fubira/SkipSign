package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

public class ItemFrameRendererEx extends ItemFrameRenderer<ItemFrame>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void extractRenderState(ItemFrame entity, ItemFrameRenderState renderState, float partialTicks)
    {
        super.extractRenderState(entity, renderState, partialTicks);

        if (SkipSignMod.config.enableMod && !isVisible(renderState)) {
            renderState.item.clear();
            renderState.mapId = null;
            renderState.mapRenderState.texture = null;
        }
    }

    @Override
    public void submit(ItemFrameRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (SkipSignMod.config.enableMod && SkipSignMod.config.dropOffFrameBoard && !isVisible(renderState)) {
            return;
        }

        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public boolean isVisible(EntityRenderState renderState)
    {
        if (SkipSignMod.config.viewModeFrame == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignMod.config.viewModeFrame == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }
    
        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToEntityRenderState(renderState), SkipSignMod.config.viewRangeFrame)) {
            return true;
        }

        return false;
    }
}
