package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
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
    public void render(ItemFrameRenderState renderState, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!NeoForgeConfig.enableMod.get()) {
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
    
            if ((!NeoForgeConfig.dropOffFrameBoard.get()) || (NeoForgeConfig.dropOffFrameBoard.get() && visible)) {
                super.render(targetRenderState, matrixStackIn, bufferIn, packedLightIn);
            }
        }
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

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToEntityRenderState(renderState), NeoForgeConfig.viewRangeFrame.get())) {
            return true;
        }

        return false;
    }
}
