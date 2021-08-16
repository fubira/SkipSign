package mods.skipsign.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "horseinforeloaded")
public class SkipSignConfig implements ConfigData {
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

	public static SkipSignConfig register(){
		var configHolder = AutoConfig.register(SkipSignConfig.class, GsonConfigSerializer::new);

        return configHolder.getConfig();
	}

}