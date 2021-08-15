package mods.skipsign.fabric.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.fabric.SkipSignMod;
import mods.skipsign.fabric.ViewMode;

public class ItemFrameRendererEx extends ItemFrameRenderer<ItemFrame>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(ItemFrame entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!SkipSignMod.config.enableMod) {
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getItem();
                entity.setItem(ItemStack.EMPTY);
            }
    
            if ((!SkipSignMod.config.dropOffFrameBoard) || (SkipSignMod.config.dropOffFrameBoard && visible)) {
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setItem(frameItemStack);
            }
        }
    }

    public boolean isVisible(ItemFrame entity)
    {
        if (SkipSignMod.config.viewModeFrame == ViewMode.FORCE)
            return true;
        if (SkipSignMod.config.viewModeFrame == ViewMode.NONE)
            return false;

        Minecraft mc = Minecraft.getInstance();
        if (SkipSignMod.client.isZooming() || mc.player.isScoping()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(
                RendererHelper.GetDistancePlayerToEntity(entity),
                SkipSignMod.config.viewRangeFrame)) {
            return true;
        }

        return false;
    }
}
