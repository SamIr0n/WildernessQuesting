package io.github.samir0n.wquesting.model.instance;

import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.server.level.ServerPlayer;

/**
 * Represents progression in a {@link QuestDefinition} within a {@link StoryInstance}.
 * */
public interface QuestInstance extends Instance<QuestDefinition> {
    QuestState state();
    void update(final ServerPlayer player, final QuestEvent event);
}
