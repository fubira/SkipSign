package mods.skipsign.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class SkipSignConfig {
    public static final EnumValue<ViewMode> viewModeSign;
    public static final EnumValue<ViewMode> viewModeFrame;
    public static final EnumValue<ViewMode> viewModeChest;
    public static final EnumValue<ViewMode> viewModeSkull;

    public static final IntValue viewRangeSign;
    public static final IntValue viewRangeFrame;
    public static final IntValue viewRangeChest;
    public static final IntValue viewRangeSkull;

    public static final BooleanValue dropOffSignBoard;
    public static final BooleanValue dropOffFrameBoard;

    public static final BooleanValue enableMod;

    public static final ForgeConfigSpec spec;

    public static void register(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, spec);
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
    
        viewModeSign    = builder.defineEnum("viewModeSign", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeFrame   = builder.defineEnum("viewModeFrame", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeChest   = builder.defineEnum("viewModeChest", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeSkull   = builder.defineEnum("viewModeSkull", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        
        viewRangeSign   = builder.defineInRange("viewRangeSign", 20, 1, 64); 
        viewRangeFrame  = builder.defineInRange("viewRangeFrame", 20, 1, 64); 
        viewRangeChest  = builder.defineInRange("viewRangeChest", 64, 1, 64); 
        viewRangeSkull  = builder.defineInRange("viewRangeSkull", 64, 1, 64); 

        dropOffSignBoard  = builder.define("dropOffSignBoard", false);
        dropOffFrameBoard = builder.define("dropOffFrameBoard", false);
        enableMod      = builder.define("enableMod", true);

        builder.pop();
        spec = builder.build();
    }
}