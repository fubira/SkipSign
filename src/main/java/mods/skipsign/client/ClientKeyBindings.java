package mods.skipsign.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.glfw.GLFW;

import mods.skipsign.SkipSignMod;

public class ClientKeyBindings {
    public static final KeyBinding keyBindingOption = new KeyBinding("keybinding.desc.option", GLFW.GLFW_KEY_F8, "keybinding.category.skipsign");
    public static final KeyBinding keyBindingZoom = new KeyBinding("keybinding.desc.zoom", GLFW.GLFW_KEY_RIGHT_ALT, "keybinding.category.skipsign");

    public static void register() {
        ClientRegistry.registerKeyBinding(keyBindingOption);
        ClientRegistry.registerKeyBinding(keyBindingZoom);
    }

}