package mods.skipsign.fabric;

import net.fabricmc.api.ClientModInitializer;
import mods.skipsign.fabric.client.SkipSignClient;

public class SkipSignMod implements ClientModInitializer
{
    public static SkipSignConfig config;
    public static SkipSignClient client;

    @Override
    public void onInitializeClient() {
        config = SkipSignConfig.register();
        client = new SkipSignClient();
    }
}
