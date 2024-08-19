package mods.skipsign.fabric;

import net.fabricmc.api.ClientModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.fabric.client.SkipSignClient;

public class SkipSignMod implements ClientModInitializer
{
    public static Logger logger = LogManager.getLogger("SkipSign");
    public static FabricConfig config;
    public static SkipSignClient client;

    @Override
    public void onInitializeClient() {
        config = FabricConfig.register();
        client = new SkipSignClient();
    }
}
