package mods.skipsign.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "skipsign")
public class FabricConfig implements ConfigData {
    private static ConfigHolder<FabricConfig> configHolder;

    public static FabricConfig register(){
		FabricConfig.configHolder = AutoConfig.register(
            FabricConfig.class,
            GsonConfigSerializer::new
        );

        return FabricConfig.configHolder.getConfig();
	}

    public void save() {
        FabricConfig.configHolder.save();
    }

    public void load() {
        FabricConfig.configHolder.load();
    }

    public ViewMode viewModeSign = ViewMode.NORMAL;
    public ViewMode viewModeFrame = ViewMode.NORMAL;
    public ViewMode viewModeChest = ViewMode.NORMAL;
    public ViewMode viewModeSkull = ViewMode.NORMAL;

    public int viewRangeSign = 20;
    public int viewRangeFrame = 20;
    public int viewRangeChest = 64;
    public int viewRangeSkull = 64;

    public boolean dropOffSignBoard = false;
    public boolean dropOffFrameBoard = false;

    public boolean enableMod = true;
}
