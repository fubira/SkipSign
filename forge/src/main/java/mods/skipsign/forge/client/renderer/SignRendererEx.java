package mods.skipsign.forge.client.renderer;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.forge.ForgeConfig;
import mods.skipsign.forge.SkipSignMod;
import mods.skipsign.forge.ViewMode;

public class SignRendererEx extends SignRenderer
{
    public SignRendererEx(BlockEntityRendererProvider.Context context)
    {
        super(context);
    }

    public Component [] getSignText(SignBlockEntity entity)
    {
        Component [] tempSignText = new Component[4];

        for (int i = 0; i < 4; i++) {
            tempSignText[i] = entity.getMessage(i, true).copy();
        }
        return tempSignText;
    }

    public void setSignMessage(SignBlockEntity entity, Component [] text)
    {
        for (int i = 0; i < 4; i++) {
            entity.setMessage(i, text[i]);
        }
    }

    public void emptySignMessage(SignBlockEntity entity)
    {
        for (int i = 0; i < 4; i++) {
            entity.setMessage(i, TextComponent.EMPTY);
        }
    }

    @Override
    public void render(SignBlockEntity entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!ForgeConfig.enableMod.get()) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            boolean visible = isVisible(entity);
            SignBlockEntity entityForRender = entity;

            if (!visible) {
                // Create empty SignBlockEntity for render
                entityForRender = new SignBlockEntity(entity.getBlockPos(), entity.getBlockState());
            }

            if (!ForgeConfig.dropOffSignBoard.get() || (ForgeConfig.dropOffSignBoard.get() && visible)) {
                super.render(entityForRender, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }
        }
    }

    public boolean isVisible(SignBlockEntity entity)
    {
        if (ForgeConfig.viewModeSign.get() == ViewMode.FORCE) {
            return true;
        }
        if (ForgeConfig.viewModeSign.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }
    
        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), ForgeConfig.viewRangeSign.get())) {
            return true;
        }

        return false;
    }
}
