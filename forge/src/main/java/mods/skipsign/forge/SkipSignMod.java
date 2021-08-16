package mods.skipsign.forge;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.forge.client.SkipSignClient;

@Mod(SkipSignMod.modId)
public class SkipSignMod
{
    public static final String modId = "skipsign";
    public static SkipSignClient client;

    public static Logger logger = LogManager.getLogger();

    public SkipSignMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        ForgeConfig.register(ModLoadingContext.get());

        client = new SkipSignClient();
    }
}
