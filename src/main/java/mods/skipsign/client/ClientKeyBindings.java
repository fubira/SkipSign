package mods.skipsign.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.SkipSignMod;

public class ClientKeyBindings {
    public static final KeyBinding keyBindingOption = new KeyBinding("skipsign.keybinding.desc.option", GLFW.GLFW_KEY_F8, "skipsign.keybinding.category");
    public static final KeyBinding keyBindingZoom = new KeyBinding("skipsign.keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "skipsign.keybinding.category");

    public static void register() {
        ClientRegistry.registerKeyBinding(keyBindingOption);
        ClientRegistry.registerKeyBinding(keyBindingZoom);
    }

}