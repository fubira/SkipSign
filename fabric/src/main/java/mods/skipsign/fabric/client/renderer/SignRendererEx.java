package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

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
            tempSignText[i] = entity.getMessage(i, true);
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
            entity.setMessage(i, new TextComponent(""));
        }
    }

    @Override
    public void render(SignBlockEntity entity, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!SkipSignMod.config.enableMod) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            Component [] temporaryText = null;
            boolean visible = isVisible(entity);

            if (!visible) {
                // Hide text/signboard
                temporaryText = getSignText(entity);
                emptySignMessage(entity);
            }

            if (!SkipSignMod.config.dropOffSignBoard || (SkipSignMod.config.dropOffSignBoard && visible)) {
                super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            if (temporaryText != null) {
                setSignMessage(entity, temporaryText);
            }
        }
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
}
