package mods.skipsign.client.renderer;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import com.mojang.blaze3d.matrix.MatrixStack;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class ItemFrameRendererEx extends ItemFrameRenderer
{
    public ItemFrameRendererEx(EntityRendererManager rendererManager, ItemRenderer itemRenderer)
    {
        super(rendererManager, itemRenderer);
    }

    @Override
    public void render(ItemFrameEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!Config.enableMod.get()) {
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getDisplayedItem();
                entity.setDisplayedItem(ItemStack.EMPTY);
            }
    
            if ((!Config.dropOffFrameBoard.get()) || (Config.dropOffFrameBoard.get() && visible)) {
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setDisplayedItem(frameItemStack);
            }
        }
    }

    public boolean isVisible(ItemFrameEntity entityItemFrame)
    {
        if (Config.viewModeFrame.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeFrame.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputMappings.isKeyDown(mc.getMainWindow().getHandle(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToEntity(entityItemFrame),
                Config.viewRangeFrame.get()))
            return true;

        return false;
    }
}
