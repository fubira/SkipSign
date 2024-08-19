package mods.skipsign.neoforge.client.renderer;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.decoration.ItemFrame;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.skipsign.neoforge.NeoForgeConfig;
import mods.skipsign.neoforge.SkipSignMod;
import mods.skipsign.neoforge.ViewMode;

public class ItemFrameRendererEx<T extends ItemFrame> extends ItemFrameRenderer<T>
{
    public ItemFrameRendererEx(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        if (!NeoForgeConfig.enableMod.get()) {
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else {
            ItemStack frameItemStack = ItemStack.EMPTY;
            boolean visible = isVisible(entity);
    
            if (!visible) {
                frameItemStack = entity.getItem();
                entity.setItem(ItemStack.EMPTY);
            }
    
            if ((!NeoForgeConfig.dropOffFrameBoard.get()) || (NeoForgeConfig.dropOffFrameBoard.get() && visible)) {
                super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
    
            if (!frameItemStack.isEmpty()) {
                entity.setItem(frameItemStack);
            }
        }
    }

    public boolean isVisible(ItemFrame entity)
    {
        if (NeoForgeConfig.viewModeFrame.get() == ViewMode.FORCE) {
            return true;
        }
        if (NeoForgeConfig.viewModeFrame.get() == ViewMode.NONE) {
            return false;
        }

        if (SkipSignMod.client.isZooming()) {
            return true;
        }

        if (RendererHelper.IsInRangeToRenderDist(RendererHelper.GetDistancePlayerToEntity(entity), NeoForgeConfig.viewRangeFrame.get())) {
            return true;
        }

        return false;
    }
}
