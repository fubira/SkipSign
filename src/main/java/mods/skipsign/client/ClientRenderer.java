package mods.skipsign.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import mods.skipsign.client.renderer.ItemFrameRendererEx;
import mods.skipsign.client.renderer.ChestTileEntityRendererEx;
import mods.skipsign.client.renderer.SignTileEntityRendererEx;
import mods.skipsign.client.renderer.SkullTileEntityRendererEx;

public class ClientRenderer {

    public static void registerTileEntity() {
        ClientRegistry.bindTileEntitySpecialRenderer(SignTileEntity.class, new SignTileEntityRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(ChestTileEntity.class, new ChestTileEntityRendererEx<ChestTileEntity>());
        ClientRegistry.bindTileEntitySpecialRenderer(SkullTileEntity.class, new SkullTileEntityRendererEx());
    }

    public static void registerItemFrame() {
        EntityRendererManager rendererManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemFrameRendererEx itemFrameRenderer = new ItemFrameRendererEx(rendererManager, itemRenderer);

        rendererManager.renderers.remove(ItemFrameEntity.class);
        rendererManager.renderers.put(ItemFrameEntity.class, itemFrameRenderer);
    }
}
