package mods.skipsign.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;
import mods.skipsign.SkipSignMod;

public class TileEntitySignRendererEx extends TileEntitySignRenderer
{
    public TileEntitySignRendererEx()
    {
        super();
    }

    public ITextComponent [] getSignText(TileEntitySign entity)
    {
        ITextComponent [] tempSignText = new ITextComponent[entity.signText.length];

        for (int i = 0; i < entity.signText.length; i++)
            tempSignText[i] = entity.func_212366_a(i);  // TileEntitySign.getText(i)

        return tempSignText;
    }

    public void setSignText(TileEntitySign entity, ITextComponent [] text)
    {
        for (int i = 0; i < entity.signText.length; i++) {
            entity.func_212365_a(i, text[i]);   // TileEntitySign.setText(i)
        }
    }

    public void deleteSignText(TileEntitySign entity)
    {
        for (int i = 0; i < entity.signText.length; i++) {
            entity.func_212365_a(i, null);      // TileEntitySign.getText(i)
        }
    }

    @Override
    public void render(TileEntitySign entity, double x, double y, double z, float partialTicks, int destroyStage)
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
                SkipSignMod.logger.info("invisible:" + temporaryText);
            }

            if (!Config.dropOffSignBoard.get() || (Config.dropOffSignBoard.get() && visible)) {
                SkipSignMod.logger.info("render:" + entity.signText[0]);
                super.render(entity, x, y, z, partialTicks, destroyStage);
            }

            if (temporaryText != null) {
                SkipSignMod.logger.info("reset:" + temporaryText);
                setSignText(entity, temporaryText);
            }
        }
    }

    public boolean isVisible(TileEntitySign tileEntitySign)
    {
        if (Config.viewModeSign.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSign.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToTileEntity(tileEntitySign),
                Config.viewRangeSign.get()))
            return true;

        return false;
    }
}
