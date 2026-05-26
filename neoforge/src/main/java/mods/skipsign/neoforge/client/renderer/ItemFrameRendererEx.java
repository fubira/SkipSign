package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class ItemFrameRendererEx<T extends ItemFrame> extends ItemFrameRenderer<T>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void extractRenderState(T entity, ItemFrameRenderState renderState, float partialTicks)
    {
        super.extractRenderState(entity, renderState, partialTicks);

        if (NeoForgeConfig.enableMod.get() && !isVisible(renderState)) {
            renderState.item.clear();
            renderState.mapId = null;
            renderState.mapRenderState.texture = null;
        }
    }

    @Override
    public void submit(ItemFrameRenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (NeoForgeConfig.enableMod.get() && NeoForgeConfig.dropOffFrameBoard.get() && !isVisible(renderState)) {
            return;
        }

        super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public boolean isVisible(EntityRenderState renderState)
    {
        if (NeoForgeConfig.viewModeFrame.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeFrame.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        return RendererHelper.IsInRangeToRenderDist(
            RendererHelper.GetDistancePlayerToEntityRenderState(renderState),
            NeoForgeConfig.viewRangeFrame.get()
        );
    }
}
