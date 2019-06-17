package mods.skipsign;
import net.minecraftforge.common.ForgeConfigSpec;

// @Config(modid = SkipSignCore.modId, type = Type.INSTANCE, name = SkipSignCore.modId + "_config")
public class SkipSignConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> modEnabled;
        public final ForgeConfigSpec.ConfigValue<Integer> visibleKeyId;
        public final ForgeConfigSpec.ConfigValue<Integer> zoomKeyId;
        public final ForgeConfigSpec.ConfigValue<Integer> signViewMode;
        public final ForgeConfigSpec.ConfigValue<Integer> frameViewMode;
        public final ForgeConfigSpec.ConfigValue<Integer> chestViewMode;
        public final ForgeConfigSpec.ConfigValue<Integer> skullViewMode;
        public final ForgeConfigSpec.ConfigValue<Integer> signVisibleRange;
        public final ForgeConfigSpec.ConfigValue<Integer> frameVisibleRange;
        public final ForgeConfigSpec.ConfigValue<Integer> chestVisibleRange;
        public final ForgeConfigSpec.ConfigValue<Integer> skullVisibleRange;

        public final ForgeConfigSpec.ConfigValue<Boolean> hideInvisibleFrameBoard;
        public final ForgeConfigSpec.ConfigValue<Boolean> dropoffSign;
        public final ForgeConfigSpec.ConfigValue<Boolean> dropoffChest;
        public final ForgeConfigSpec.ConfigValue<Boolean> dropoffSkull;

        public General(ForgeConfigSpec.Builder builder) {
            BUILDER.push("General");

            modEnabled = BUILDER
                .comment("SkipSignの有効/無効を切り替える [false/true|default:true]")
                .translation("enable.skipsign.config")
                .define("modEnabled", true);

            visibleKeyId = BUILDER
                .comment("押している間非表示になるキー [0..66|default:66]")
                .translation("visible_key_id.skipsign.config")
                .define("visibleKeyId", 66);

            zoomKeyId = BUILDER
                .comment("押している間ズーム中とするキー [0..20|default:20]")
                .translation("zoom_key_id.skipsign.config")
                .define("zoomKeyId", 22);

            signViewMode = BUILDER
                .comment("看板の描画モード [0..2|default:0]")
                .translation("sign_view_mode.skipsign.config")
                .defineInRange("signViewMode", 0, 0, 2);

            signVisibleRange = BUILDER
                .comment("看板の見える範囲 [0..128|default:20]")
                .translation("sign_visible_range.skipsign.config")
                .defineInRange("signVisibleRange", 20, 0, 128);

            frameViewMode = BUILDER
                .comment("額の描画モード [0..2|default:0]")
                .translation("frame_view_mode.skipsign.config")
                .defineInRange("frameViewMode", 0, 0, 2);

            frameVisibleRange = BUILDER
                .comment("看板の見える範囲 [0..128|default:20]")
                .translation("frame_visible_range.skipsign.config")
                .defineInRange("frameVisibleRange", 20, 0, 128);

            chestViewMode = BUILDER
                .comment("チェストの描画モード [0..2|default:0]")
                .translation("chest_view_mode.skipsign.config")
                .defineInRange("chestViewMode", 0, 0, 2);

            chestVisibleRange = BUILDER
                .comment("チェストの見える範囲 [0..128|default:20]")
                .translation("chest_visible_range.skipsign.config")
                .defineInRange("chestVisibleRange", 20, 0, 128);

            skullViewMode = BUILDER
                .comment("ヘッドの描画モード [0..2|default:0]")
                .translation("skull_view_mode.skipsign.config")
                .defineInRange("skullViewMode", 0, 0, 2);

            skullVisibleRange = BUILDER
                .comment("ヘッドの見える範囲 [0..128|default:20]")
                .translation("skull_visible_range.skipsign.config")
                .defineInRange("skullVisibleRange", 20, 0, 128);

            hideInvisibleFrameBoard = BUILDER
                .comment("非表示範囲にある額そのものを非表示にする [false/true|default:false]")
                .translation("hide_invisible_frame_board.skipsign.config")
                .define("hideInvisibleFrameBoard", true);

            dropoffSign = BUILDER
                .comment("看板を非表示にする [false/true|default:false]")
                .translation("sign_dropoff.skipsign.config")
                .define("dropoffSign", true);

            dropoffChest = BUILDER
                .comment("チェストを非表示にする [false/true|default:false]")
                .translation("chest_dropoff.skipsign.config")
                .define("dropoffSign", true);

            dropoffSkull = BUILDER
                .comment("ヘッドを非表示にする [false/true|default:false]")
                .translation("skull_dropoff.skipsign.config")
                .define("dropoffSkull", true);

            BUILDER.pop();
        }
    }
}
