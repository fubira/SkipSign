package mods.skipsign.fabric.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.network.chat.Component;
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

    public static final KeyMapping keyMappingOption = KeyBindingHelper.registerKeyBinding(
        new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category")
    );
    public static final KeyMapping keyMappingZoom = KeyBindingHelper.registerKeyBinding(
        new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.category")
    );

    public SkipSignClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);

        BlockEntityRenderers.register(BlockEntityType.SIGN, SignRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.HANGING_SIGN, HangingSignRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.TRAPPED_CHEST, ChestRendererEx::new);
        BlockEntityRenderers.register(BlockEntityType.SKULL, SkullRendererEx::new);

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
            client.setScreen(new GuiConfigScreen(Component.Serializer.fromJson("{\"text\":\"SkipSign\"}", client.player.registryAccess())));
            key_down = true;
        } else if (key_down && !keyMappingOption.consumeClick()) {
            key_down = false;
        }
    }
}
