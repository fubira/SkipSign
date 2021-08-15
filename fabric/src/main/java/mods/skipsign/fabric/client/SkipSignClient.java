package mods.skipsign.fabric.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.fabric.client.gui.GuiConfigScreen;
import mods.skipsign.fabric.client.renderer.ChestRendererEx;
import mods.skipsign.fabric.client.renderer.ItemFrameRendererEx;
import mods.skipsign.fabric.client.renderer.SignRendererEx;
import mods.skipsign.fabric.client.renderer.SkullRendererEx;

public final class SkipSignClient {
    private boolean key_down = false;
    private boolean is_zooming = false;

    public static final KeyMapping keyMappingOption = KeyBindingHelper.registerKeyBinding(
        new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category")
    );
    public static final KeyMapping keyMappingZoom = KeyBindingHelper.registerKeyBinding(
        new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.zoom")
    );

    public SkipSignClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRendererRegistry.INSTANCE.register(BlockEntityType.SKULL, SkullRendererEx::new);

        EntityRendererRegistry.INSTANCE.register(EntityType.ITEM_FRAME, ItemFrameRendererEx::new);
    }

    public boolean isZooming() {
        return is_zooming;
    } 

    public void onTick(Minecraft client) {
        if (client.screen != null) {
            return;
        }

        is_zooming = keyMappingZoom.isDown();

        if (!key_down && keyMappingOption.consumeClick()) {
            client.setScreen(new GuiConfigScreen(new TextComponent("SkipSign")));
            key_down = true;
        } else if (key_down && !keyMappingOption.consumeClick()) {
            key_down = false;
        }
    }
}
