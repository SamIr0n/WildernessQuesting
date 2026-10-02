package io.github.samir0n.wquesting.model.instance;

import io.github.samir0n.wquesting.model.definition.StoryDefinition;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

/**
 * Represents progression through a story, i.e. through a {@link StoryDefinition}.
 * @see StoryDefinition
 * @see QuestInstance
 * */
public interface StoryInstance extends Instance<StoryDefinition> {
    /**
     * The UUID of the {@link Player} whose progression is being tracked
     * */
    UUID owner();
    /**
     * The currently active {@link QuestInstance}, i.e. the one that the player can work towards right now.
     * */
    QuestInstance active();
}
