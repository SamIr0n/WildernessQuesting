package io.github.samir0n.wquesting;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WQConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE = BUILDER.translation("config.questing_enable").define("enable", true);

    public static final ModConfigSpec.BooleanValue ENABLE_QUEST_BOOK_ITEM = BUILDER.translation("config.quest_book_item_enable").define("quest_book_item_enable", true);
    static final ModConfigSpec.BooleanValue ENABLE_QUEST_BOOK_ITEM_REQUIREMENT = BUILDER.translation("config.quest_book_item_requirement_enable").define("quest_book_item_requirement_enable", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}


