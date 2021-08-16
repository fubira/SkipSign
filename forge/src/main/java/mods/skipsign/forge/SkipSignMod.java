package mods.skipsign.forge;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.forge.client.SkipSignClient;

@Mod(SkipSignMod.modId)
public class SkipSignMod
{
    public static final String modId = "skipsign";
    public static SkipSignClient client;
    public static ForgeConfig config;

    public static Logger logger = LogManager.getLogger();

    public SkipSignMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        SkipSignConfig.register(ModLoadingContext.get());

        client = new SkipSignClient();
    }

	@SubscribeEvent
	public void onConfigLoading(final ModConfigEvent event){
        config = new ForgeConfig(event.getConfig());
        config.save();
	}
}
