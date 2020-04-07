package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import mods.skipsign.Config;
import mods.skipsign.ViewMode;
import mods.skipsign.SkipSignMod;

public class SignTileEntityRendererEx extends SignTileEntityRenderer
{
    public SignTileEntityRendererEx()
    {
        super();
    }

    public ITextComponent [] getSignText(SignTileEntity entity)
    {
        ITextComponent [] tempSignText = new ITextComponent[entity.signText.length];

        for (int i = 0; i < entity.signText.length; i++)
            tempSignText[i] = entity.getText(i);  // TileEntitySign.getText(i)

        return tempSignText;
    }

    public void setSignText(SignTileEntity entity, ITextComponent [] text)
    {
        for (int i = 0; i < entity.signText.length; i++) {
            entity.setText(i, text[i]);   // TileEntitySign.setText(i)
        }
    }

    public void deleteSignText(SignTileEntity entity)
    {
        for (int i = 0; i < entity.signText.length; i++) {
            entity.setText(i, new StringTextComponent(""));
        }
    }

    @Override
    public void render(SignTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!Config.enableMod.get()) {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        } else {
            ITextComponent [] temporaryText = null;
            boolean visible = isVisible(entity);

            if (!visible) {
                // Hide text/signboard
                temporaryText = getSignText(entity);
                deleteSignText(entity);
            }

            if (!Config.dropOffSignBoard.get() || (Config.dropOffSignBoard.get() && visible)) {
                super.render(entity, x, y, z, partialTicks, destroyStage);
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
        if (InputMappings.isKeyDown(mc.mainWindow.getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySign),
                Config.viewRangeSign.get()))
            return true;

        return false;
    }
}
