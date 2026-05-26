package mods.skipsign.fabric.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.fabric.client.gui.GuiConfigScreen;
import mods.skipsign.fabric.client.renderer.ChestRendererEx;
import mods.skipsign.fabric.client.renderer.HangingSignRendererEx;
import mods.skipsign.fabric.client.renderer.ItemFrameRendererEx;
import mods.skipsign.fabric.client.renderer.SignRendererEx;
import mods.skipsign.fabric.client.renderer.SkullRendererEx;

public final class SkipSignClient {
    private boolean key_down = false;
    private boolean is_zooming = false;
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(
        Identifier.fromNamespaceAndPath("skipsign", "keybinding")
    );

    public static final KeyMapping keyMappingOption = KeyMappingHelper.registerKeyMapping(
        new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, CATEGORY)
    );
    public static final KeyMapping keyMappingZoom = KeyMappingHelper.registerKeyMapping(
        new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, CATEGORY)
    );

    public SkipSignClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        BlockEntityRendererRegistry.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRendererRegistry.register(BlockEntityType.HANGING_SIGN, context -> (BlockEntityRenderer) new HangingSignRendererEx(context));
        BlockEntityRendererRegistry.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRendererRegistry.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRendererRegistry.register(BlockEntityType.SKULL, SkullRendererEx::new);

        EntityRendererRegistry.register(EntityType.GLOW_ITEM_FRAME, ItemFrameRendererEx::new);
        EntityRendererRegistry.register(EntityType.ITEM_FRAME, ItemFrameRendererEx::new);
    }

    public boolean isZooming() {
        Minecraft client = Minecraft.getInstance();
        return is_zooming || client.player.isScoping();
   }

    public void onTick(Minecraft client) {
        if (client.screen != null) {
            return;
        }

        is_zooming = keyMappingZoom.isDown();

        if (!key_down && keyMappingOption.consumeClick()) {
            client.setScreen(new GuiConfigScreen(Component.literal("SkipSign")));
            key_down = true;
        } else if (key_down && !keyMappingOption.consumeClick()) {
            key_down = false;
        }
    }
}
