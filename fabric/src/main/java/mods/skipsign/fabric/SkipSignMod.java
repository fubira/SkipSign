package mods.skipsign.fabric;

import net.fabricmc.api.ClientModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.fabric.client.SkipSignClient;

public class SkipSignMod implements ClientModInitializer
{
    public static Logger logger = LogManager.getLogger("SkipSign");
    public static SkipSignConfig config;
    public static SkipSignClient client;

    @Override
    public void onInitializeClient() {
        logger.info("info log!!!");
        logger.debug("debug log!!!");
        logger.warn("warn log!!!");
        logger.error("error log!!!");
        logger.fatal("fatal log!!!");

        config = SkipSignConfig.register();
        client = new SkipSignClient();
    }
}
