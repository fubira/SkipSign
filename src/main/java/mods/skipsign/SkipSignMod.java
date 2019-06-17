package mods.skipsign;

import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.client.ClientEventHandler;

@Mod(SkipSignMod.modId)
public class SkipSignMod
{
    public static Logger logger = LogManager.getLogger();

    public static final String modId = "skipsign";
    public static final String buildId = "2019-6";
    public static String modVersion;

    public static SkipSignConfig config;

    public SkipSignMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        new ClientEventHandler();

        Config.register(ModLoadingContext.get());
        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();

        SkipSignMod.logger.info("*** SkipSign " + modVersion + " initialized ***");
    }
    
	@SubscribeEvent
	public void onConfigLoading(final ModConfig.Loading event){
        config = new SkipSignConfig(event.getConfig());
        config.save();
	}
}
