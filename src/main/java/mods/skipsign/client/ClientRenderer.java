package mods.skipsign.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;

import mods.skipsign.client.renderer.ItemFrameRendererEx;
import mods.skipsign.client.renderer.ChestTileEntityRendererEx;
import mods.skipsign.client.renderer.SignTileEntityRendererEx;
import mods.skipsign.client.renderer.SkullTileEntityRendererEx;

public class ClientRenderer {

    public static void registerTileEntity() {
        TileEntityRendererDispatcher rendererDispatcher = TileEntityRendererDispatcher.instance;
        rendererDispatcher.setSpecialRendererInternal(TileEntityType.SIGN, new SignTileEntityRendererEx(rendererDispatcher));
        rendererDispatcher.setSpecialRendererInternal(TileEntityType.CHEST, new ChestTileEntityRendererEx<ChestTileEntity>(rendererDispatcher));
        rendererDispatcher.setSpecialRendererInternal(TileEntityType.TRAPPED_CHEST, new ChestTileEntityRendererEx<ChestTileEntity>(rendererDispatcher));
        rendererDispatcher.setSpecialRendererInternal(TileEntityType.SKULL, new SkullTileEntityRendererEx(rendererDispatcher));
    }

    public static void registerItemFrame() {
        EntityRendererManager rendererManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemFrameRendererEx itemFrameRenderer = new ItemFrameRendererEx(rendererManager, itemRenderer);

        rendererManager.renderers.remove(EntityType.ITEM_FRAME);
        rendererManager.renderers.put(EntityType.ITEM_FRAME, itemFrameRenderer);
    }
}
