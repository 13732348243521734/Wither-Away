package com.tweak.witheraway;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_WITHER_BOSSBAR = BUILDER
            .comment("Disable the Wither boss bar from being shown to players")
            .translation("witheraway.configuration.disableWitherBossbar")
            .define("disableWitherBossbar", false);

    public static final ModConfigSpec.BooleanValue DISABLE_WITHER_DARKENING = BUILDER
            .comment("Disable the screen darkening effect caused by the Wither boss bar")
            .translation("witheraway.configuration.disableWitherDarkening")
            .define("disableWitherDarkening", false);

    public static final ModConfigSpec.BooleanValue MUTE_WITHER_SPAWN_SOUND = BUILDER
            .comment("Mute the global sound when the Wither finishes spawning")
            .translation("witheraway.configuration.muteWitherSpawnSound")
            .define("muteWitherSpawnSound", false);

    public static final ModConfigSpec.BooleanValue MUTE_ALL_OTHER_WITHER_SOUNDS = BUILDER
            .comment("Mute all other sounds caused by the Wither (ambient, hurt, death, and all Wither explosions)")
            .translation("witheraway.configuration.muteAllOtherWitherSounds")
            .define("muteAllOtherWitherSounds", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
