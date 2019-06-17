package mods.skipsign;

import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import mods.skipsign.SkipSignCore;
import mods.skipsign.SkipSignHelper;

public class RenderItemFrameEx extends RenderItemFrame
{
    public RenderItemFrameEx(RenderManager renderManager, ItemRenderer itemRenderer)
    {
        super(renderManager, itemRenderer);
    }

    @Override
    public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ItemStack frameItemStack = ItemStack.EMPTY;

        if (!CheckVisibleState(entity))
        {
            frameItemStack = entity.getDisplayedItem();
            entity.setDisplayedItem(ItemStack.EMPTY);
        }

        if ((!SkipSignConfig.GENERAL.hideInvisibleFrameBoard.get()) ||
            (SkipSignConfig.GENERAL.hideInvisibleFrameBoard.get() && CheckVisibleState(entity)))
        {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }

        if (!frameItemStack.isEmpty())
        {
            entity.setDisplayedItem(frameItemStack);
        }
    }

    public boolean CheckVisibleState(EntityItemFrame entityItemFrame)
    {
        if (SkipSignConfig.GENERAL.frameViewMode.get() == 1)
            return true;
        if (SkipSignConfig.GENERAL.frameViewMode.get() == 2)
            return false;

        if (InputMappings.isKeyDown(SkipSignConfig.GENERAL.zoomKeyId.get()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToEntity(entityItemFrame),
                SkipSignConfig.GENERAL.frameVisibleRange.get()))
            return true;
        return false;
    }
}
