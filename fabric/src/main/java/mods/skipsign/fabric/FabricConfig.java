package mods.skipsign.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "horseinforeloaded")
public class FabricConfig implements ConfigData {
    public ViewMode viewModeSign;
    public ViewMode viewModeFrame;
    public ViewMode viewModeChest;
    public ViewMode viewModeSkull;

    public int viewRangeSign;
    public int viewRangeFrame;
    public int viewRangeChest;
    public int viewRangeSkull;

    public boolean dropOffSignBoard;
    public boolean dropOffFrameBoard;

    public boolean enableMod;

	public static FabricConfig register(){
		var configHolder = AutoConfig.register(FabricConfig.class, GsonConfigSerializer::new);

        return configHolder.getConfig();
	}

}