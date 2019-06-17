package mods.skipsign.client.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.util.InputMappings;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;

import mods.skipsign.Config;
import mods.skipsign.SkipSignHelper;
import mods.skipsign.ViewMode;

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
            tempSignText[i] = entity.signText[i];

        return tempSignText;
    }

    public void setSignText(TileEntitySign entity, ITextComponent [] text)
    {
        for (int i = 0; i < entity.signText.length; i++)
            entity.signText[i] = text[i];
    }

    public void deleteSignText(TileEntitySign entity)
    {
        for (int i = 0; i < entity.signText.length; i++)
            entity.signText[i] = null;
    }

    @Override
    public void render(TileEntitySign entity, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if (!isDropOff(entity, x, y, z))
            return;

        ITextComponent [] temporaryText = null;
        if (!CheckVisibleState(entity))
        {
            temporaryText = getSignText(entity);
            deleteSignText(entity);
        }

        if ((!Config.dropOffFrameBase.get()) ||
            (Config.dropOffFrameBase.get() && CheckVisibleState(entity)))
        {
            super.render(entity, x, y, z, partialTicks, destroyStage);
        }

        if (temporaryText != null)
            setSignText(entity, temporaryText);
    }

    public boolean isDropOff(TileEntity tile, double x, double y, double z)
    {
        return true;
    }

    public boolean CheckVisibleState(TileEntitySign tileEntitySign)
    {
        if (Config.viewModeSign.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeSign.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToTileEntity(tileEntitySign),
                Config.viewRangeSign.get()))
            return true;

        return false;
    }
}
