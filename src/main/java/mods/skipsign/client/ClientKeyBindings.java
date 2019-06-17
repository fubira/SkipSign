package mods.skipsign.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientKeyBindings {
    public static final KeyBinding keyBindingOption = new KeyBinding("ss.option", -1, "key.categories.skipsign");
    public static final KeyBinding keyBindingZoom = new KeyBinding("ss.zoom", -1, "key.categories.skipsign");

    public static void register() {
        ClientRegistry.registerKeyBinding(keyBindingOption);
        ClientRegistry.registerKeyBinding(keyBindingZoom);
    }

}