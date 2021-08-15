package mods.skipsign.forge.client;

import mods.skipsign.forge.client.renderer.ChestRendererEx;
import mods.skipsign.forge.client.renderer.ItemFrameRendererEx;
import mods.skipsign.forge.client.renderer.SignRendererEx;
import mods.skipsign.forge.client.renderer.SkullRendererEx;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ClientRenderer {

    public static void registerBlockEntityRenderer() {
        BlockEntityRenderers.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.SKULL, SkullRendererEx::new);
    }

    public static void registerItemFrameRenderer() {
        EntityRenderers.register(EntityType.ITEM_FRAME, ItemFrameRendererEx::new);
    }
}
