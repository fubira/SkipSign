package mods.skipsign.forge.client.renderer;

import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.forge.SkipSignConfig;
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
        if (!SkipSignConfig.enableMod.get()) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            Component [] temporaryText = null;
            boolean visible = isVisible(entity);

            if (!visible) {
                // Hide text/signboard
                temporaryText = getSignText(entity);
                emptySignMessage(entity);
            }

            if (!SkipSignConfig.dropOffSignBoard.get() || (SkipSignConfig.dropOffSignBoard.get() && visible)) {
                super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            if (temporaryText != null) {
                setSignMessage(entity, temporaryText);
            }
        }
    }

    public boolean isVisible(SignBlockEntity entity)
    {
        if (SkipSignConfig.viewModeSign.get() == ViewMode.FORCE) {
            return true;
        }
        if (SkipSignConfig.viewModeSign.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }
    
        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToBlockEntity(entity), SkipSignConfig.viewRangeSign.get())) {
            return true;
        }

        return false;
    }
}
