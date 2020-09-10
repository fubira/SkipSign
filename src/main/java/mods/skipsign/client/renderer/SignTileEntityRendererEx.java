package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import com.mojang.blaze3d.matrix.MatrixStack;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;
import mods.skipsign.SkipSignMod;

public class SignTileEntityRendererEx extends SignTileEntityRenderer
{
    public SignTileEntityRendererEx(TileEntityRendererDispatcher rendererDispatcher)
    {
        super(rendererDispatcher);
    }

    public ITextComponent [] getSignText(SignTileEntity entity)
    {
        ITextComponent [] tempSignText = new ITextComponent[4];

        for (int i = 0; i < 4; i++)
            tempSignText[i] = entity.getText(i);  // TileEntitySign.getText(i)

        return tempSignText;
    }

    public void setSignText(SignTileEntity entity, ITextComponent [] text)
    {
        for (int i = 0; i < 4; i++) {
            entity.setText(i, text[i]);   // TileEntitySign.setText(i)
        }
    }

    public void deleteSignText(SignTileEntity entity)
    {
        for (int i = 0; i < 4; i++) {
            entity.setText(i, new StringTextComponent(""));
        }
    }

    @Override
    public void render(SignTileEntity entity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (!Config.enableMod.get()) {
            super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        } else {
            ITextComponent [] temporaryText = null;
            boolean visible = isVisible(entity);

            if (!visible) {
                // Hide text/signboard
                temporaryText = getSignText(entity);
                deleteSignText(entity);
            }

            if (!Config.dropOffSignBoard.get() || (Config.dropOffSignBoard.get() && visible)) {
                super.render(entity, partialTicks, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            if (temporaryText != null) {
                setSignText(entity, temporaryText);
            }
        }
    }

    public boolean isVisible(SignTileEntity tileEntitySign)
    {
        if (Config.viewModeSign.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSign.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySign),
                Config.viewRangeSign.get()))
            return true;

        return false;
    }
}
