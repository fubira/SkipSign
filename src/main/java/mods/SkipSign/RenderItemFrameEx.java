package mods.skipsign;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mods.skipsign.SkipSignCore;
import mods.skipsign.SkipSignHelper;

@SideOnly(Side.CLIENT)
public class RenderItemFrameEx extends RenderItemFrame
{
    public RenderItemFrameEx(RenderManager renderManager, RenderItem renderItem)
    {
        super(renderManager, renderItem);
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

        if ((!SkipSignCore.ModSetting.HideBoard.Bool()) ||
            (SkipSignCore.ModSetting.HideBoard.Bool() && CheckVisibleState(entity)))
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
        if (SkipSignCore.ModSetting.FrameVisible.Int() == 1)
            return true;
        if (SkipSignCore.ModSetting.FrameVisible.Int() == 2)
            return false;

        if (Keyboard.isKeyDown(SkipSignCore.ModSetting.Zoom_Key.Int()))
            return true;

        if (SkipSignHelper.IsInRangeToRenderDist(
                SkipSignHelper.GetDistancePlayerToEntity(entityItemFrame),
                SkipSignCore.ModSetting.FrameRange.Int()))
            return true;
        return false;
    }
}
