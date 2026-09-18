package io.github.samir0n.wquesting;

import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE = BUILDER.translation("config.questing_enable").define("enable", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}


