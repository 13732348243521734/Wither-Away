package com.tweak.witheraway;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_WITHER_BOSSBAR = BUILDER
            .comment("Disable the Wither boss bar from being shown to players")
            .define("disableWitherBossbar", false);

    public static final ModConfigSpec.BooleanValue DISABLE_WITHER_DARKENING = BUILDER
            .comment("Disable the screen darkening effect caused by the Wither boss bar")
            .define("disableWitherDarkening", false);

    public static final ModConfigSpec.BooleanValue MUTE_WITHER_SPAWN_EXPLOSION = BUILDER
            .comment("Mute the global explosion sound when the Wither finishes spawning")
            .define("muteWitherSpawnExplosion", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
