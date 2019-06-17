package mods.skipsign.client.renderer;

import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class RenderItemFrameEx extends RenderItemFrame
{
    public RenderItemFrameEx(RenderManager renderManager, ItemRenderer itemRenderer)
    {
        super(renderManager, itemRenderer);
    }

    @Override
    public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (!Config.enableMod.get()) {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getDisplayedItem();
                entity.setDisplayedItem(ItemStack.EMPTY);
            }
    
            if ((!Config.dropOffFrameBoard.get()) || (Config.dropOffFrameBoard.get() && visible)) {
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setDisplayedItem(frameItemStack);
            }
        }


    }

    public boolean isVisible(EntityItemFrame entityItemFrame)
    {
        if (Config.viewModeFrame.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeFrame.get() == ViewMode.NONE)
            return false;

        if (InputMappings.isKeyDown(Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToEntity(entityItemFrame),
                Config.viewRangeFrame.get()))
            return true;

        return false;
    }
}
