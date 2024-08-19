package mods.skipsign.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mods.skipsign.neoforge.client.SkipSignClient;

@Mod(SkipSignMod.modId)
public class SkipSignMod
{
    public static final String modId = "skipsign";
    public static SkipSignClient client;

    public static Logger logger = LogManager.getLogger();

    public SkipSignMod(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, NeoForgeConfig.SPEC);

        client = new SkipSignClient(modBus);
    }
}
