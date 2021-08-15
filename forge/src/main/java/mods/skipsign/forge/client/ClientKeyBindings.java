package mods.skipsign.forge.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import org.lwjgl.glfw.GLFW;

public class ClientKeyBindings {
    public static final KeyMapping keyMappingOption = new KeyMapping("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category");
    public static final KeyMapping keyMappingZoom = new KeyMapping("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.category");

    public static void register() {
        ClientRegistry.registerKeyBinding(keyMappingOption);
        ClientRegistry.registerKeyBinding(keyMappingZoom);
    }

}