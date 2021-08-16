package mods.skipsign.fabric;

import net.fabricmc.api.ClientModInitializer;
import mods.skipsign.fabric.client.SkipSignClient;

public class SkipSignMod implements ClientModInitializer
{
    public static FabricConfig config;
    public static SkipSignClient client;

    @Override
    public void onInitializeClient() {
        config = FabricConfig.register();
        client = new SkipSignClient();
    }
}
