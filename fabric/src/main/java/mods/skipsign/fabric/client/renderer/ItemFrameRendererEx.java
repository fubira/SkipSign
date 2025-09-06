package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
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
    public void render(ItemFrameRenderState renderState, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!SkipSignMod.config.enableMod) {
            super.render(renderState, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemFrameRenderState targetRenderState = renderState;
            boolean visible = isVisible(renderState);
    
            if (!visible) {
                targetRenderState = new ItemFrameRenderState();
                targetRenderState.isGlowFrame = renderState.isGlowFrame;
                targetRenderState.direction = renderState.direction;
                targetRenderState.rotation = renderState.rotation;
                targetRenderState.x = renderState.x;
                targetRenderState.y = renderState.y;
                targetRenderState.z = renderState.z;
            }
    
            if ((!SkipSignMod.config.dropOffFrameBoard) || (SkipSignMod.config.dropOffFrameBoard && visible)) {
                super.render(targetRenderState, matrixStackIn, bufferIn, packedLightIn);
            }
        }
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
