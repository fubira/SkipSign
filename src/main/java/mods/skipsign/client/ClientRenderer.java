package mods.skipsign.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import mods.skipsign.SkipSignMod;
import mods.skipsign.client.renderer.RenderItemFrameEx;
import mods.skipsign.client.renderer.TileEntityChestRendererEx;
import mods.skipsign.client.renderer.TileEntitySignRendererEx;
import mods.skipsign.client.renderer.TileEntitySkullRendererEx;

public class ClientRenderer {

    public static void registerTileEntity() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySign.class, new TileEntitySignRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChest.class, new TileEntityChestRendererEx());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntitySkullRendererEx());
    }

    public static void registerItemFrame() {
        RenderManager renderManager = Minecraft.getInstance().getRenderManager();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        RenderItemFrameEx renderItemFrame = new RenderItemFrameEx(renderManager, itemRenderer);

        SkipSignMod.logger.info(renderManager);
        SkipSignMod.logger.info(renderManager.entityRenderMap);
        renderManager.entityRenderMap.remove(EntityItemFrame.class);
        renderManager.entityRenderMap.put(EntityItemFrame.class, renderItemFrame);
    }
}
