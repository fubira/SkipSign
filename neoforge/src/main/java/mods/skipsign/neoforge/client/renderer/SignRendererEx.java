package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class SignRendererEx extends SignRenderer
{
    public SignRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(SignBlockEntity entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!NeoForgeConfig.enableMod.get()) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            boolean visible = isVisible(entity);
            SignBlockEntity entityForRender = entity;

            if (!visible) {
                // Create empty SignBlockEntity for render
                entityForRender = new SignBlockEntity(entity.getBlockPos(), entity.getBlockState());
            }

            if (!NeoForgeConfig.dropOffSignBoard.get() || (NeoForgeConfig.dropOffSignBoard.get() && visible)) {
                super.render(entityForRender, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }
        }
    }

    public boolean isVisible(SignBlockEntity entity)
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
    
        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), NeoForgeConfig.viewRangeSign.get())) {
            return true;
        }

        return false;
    }
}
