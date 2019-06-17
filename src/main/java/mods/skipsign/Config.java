package mods.skipsign;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import org.lwjgl.glfw.GLFW;

public class Config {
    public static final IntValue keyCodeVisible;
    public static final IntValue keyCodeZoom;
    
    public static final EnumValue<ViewMode> viewModeSign;
    public static final EnumValue<ViewMode> viewModeFrame;
    public static final EnumValue<ViewMode> viewModeChest;
    public static final EnumValue<ViewMode> viewModeSkull;

    public static final IntValue viewRangeSign;
    public static final IntValue viewRangeFrame;
    public static final IntValue viewRangeChest;
    public static final IntValue viewRangeSkull;

    public static final BooleanValue dropOffSign;
    public static final BooleanValue dropOffFrameBase;
    public static final BooleanValue dropOffChest;
    public static final BooleanValue dropOffSkull;
    public static final BooleanValue disableMod;

    public static final ForgeConfigSpec spec;

    public static void register(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, spec);
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("general");
    
        keyCodeVisible  = builder.defineInRange("keyCodeVisible", GLFW.GLFW_KEY_LEFT_CONTROL, Integer.MIN_VALUE, Integer.MAX_VALUE);
        keyCodeZoom     = builder.defineInRange("keyCodeZoom", GLFW.GLFW_KEY_RIGHT_ALT, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        viewModeSign    = builder.defineEnum("viewModeSign", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeFrame   = builder.defineEnum("viewModeFrame", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeChest   = builder.defineEnum("viewModeChest", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        viewModeSkull   = builder.defineEnum("viewModeSkull", () -> ViewMode.NORMAL, obj -> true, ViewMode.class);
        
        viewRangeSign   = builder.defineInRange("viewRangeSign", 20, 1, 128); 
        viewRangeFrame  = builder.defineInRange("viewRangeFrame", 20, 1, 128); 
        viewRangeChest  = builder.defineInRange("viewRangeChest", 20, 1, 128); 
        viewRangeSkull  = builder.defineInRange("viewRangeSkull", 20, 1, 128); 

        dropOffSign     = builder.define("dropOffSign", true);
        dropOffFrameBase= builder.define("dropOffFrameBase", true);
        dropOffChest    = builder.define("dropOffChest", true);
        dropOffSkull    = builder.define("dropOffSkull", true);
        disableMod      = builder.define("disableMod", false);

        builder.pop();
        spec = builder.build();
    }
}