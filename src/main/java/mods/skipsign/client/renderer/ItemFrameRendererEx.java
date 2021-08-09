package mods.skipsign.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.Config;
import mods.skipsign.ViewMode;

public class ItemFrameRendererEx<T extends ItemFrame> extends ItemFrameRenderer<T>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!Config.enableMod.get()) {
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getItem();
                entity.setItem(ItemStack.EMPTY);
            }
    
            if ((!Config.dropOffFrameBoard.get()) || (Config.dropOffFrameBoard.get() && visible)) {
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setItem(frameItemStack);
            }
        }
    }

    public boolean isVisible(ItemFrame entity)
    {
        if (Config.viewModeFrame.get() == ViewMode.FORCE)
            return true;
        if (Config.viewModeFrame.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputConstants.isKeyDown(mc.getWindow().getWindow(), Config.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToEntity(entity),
                Config.viewRangeFrame.get()))
            return true;

        return false;
    }
}
