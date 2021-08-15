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


    /*
    public static final String modId = "skipsign";
    public static final String buildId = "2019-6";
    public static String modVersion;

    public SkipSignMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        new ClientEventHandler();

        Config.register(ModLoadingContext.get());
        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();

        SkipSignMod.logger.info("*** SkipSign " + modVersion + " initialized ***");
    }
    */
}
