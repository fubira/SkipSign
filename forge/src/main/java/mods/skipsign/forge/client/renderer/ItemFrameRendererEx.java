package mods.skipsign.forge.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.forge.ForgeConfig;
import mods.skipsign.forge.ViewMode;

public class ItemFrameRendererEx<T extends ItemFrame> extends ItemFrameRenderer<T>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!ForgeConfig.enableMod.get()) {
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getItem();
                entity.setItem(ItemStack.EMPTY);
            }
    
            if ((!ForgeConfig.dropOffFrameBoard.get()) || (ForgeConfig.dropOffFrameBoard.get() && visible)) {
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setItem(frameItemStack);
            }
        }
    }

    public boolean isVisible(ItemFrame entity)
    {
        if (ForgeConfig.viewModeFrame.get() == ViewMode.FORCE)
            return true;
        if (ForgeConfig.viewModeFrame.get() == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (InputConstants.isKeyDown(mc.getWindow().getWindow(), ForgeConfig.keyCodeZoom.get()))
            return true;

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToEntity(entity),
                ForgeConfig.viewRangeFrame.get()))
            return true;

        return false;
    }
}
