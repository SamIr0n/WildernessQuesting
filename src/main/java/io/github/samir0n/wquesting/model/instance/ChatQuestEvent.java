package io.github.samir0n.wquesting.model.instance;

import io.github.samir0n.wquesting.model.registry.ChatQuestBehaviour;

/**
 * An event that is sent when the player sends a chat message.
 * @see ChatQuestBehaviour
 * */
public record ChatQuestEvent() implements QuestEvent {
}
