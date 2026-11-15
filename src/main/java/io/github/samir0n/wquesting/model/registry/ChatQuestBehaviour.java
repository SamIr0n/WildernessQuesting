package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.MapCodec;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.ChatQuestEvent;
import io.github.samir0n.wquesting.model.instance.ChatQuestInstance;
import io.github.samir0n.wquesting.model.instance.QuestEvent;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;

/**
 * The behaviour for a {@link ChatQuestInstance}, wherein a player must send some a total of {@link #messages()} messages in order to complete the quest.
 * */
public class ChatQuestBehaviour extends QuestBehaviour<ChatQuestInstance> {

    public static final MapCodec<ChatQuestBehaviour> CODEC = ExtraCodecs.POSITIVE_INT.optionalFieldOf("messages", 1)
        .xmap(ChatQuestBehaviour::new, ChatQuestBehaviour::messages);

    private final int messages;

    public ChatQuestBehaviour(final int messages) {
        this.messages = messages;
    }

    public int messages() {
        return messages;
    }

    @Override
    public BehaviourType<?> getType() {
        return BehaviourType.CHAT.get();
    }

    @Override
    public MapCodec<ChatQuestInstance> instanceCodec(Holder<QuestDefinition> definition) {
        return ChatQuestInstance.codec(definition);
    }

    @Override
    public ChatQuestInstance createInstance(Holder<QuestDefinition> definition) {
        return new ChatQuestInstance(definition);
    }

    @Override
    public void handle(ChatQuestInstance instance, QuestEvent event) {
        if (!(event instanceof ChatQuestEvent) || instance.state() != BaseQuestInstance.State.IN_PROGRESS) {
            return;
        }

        instance.messageCount++;
        if (instance.messageCount >= messages) {
            instance.setState(BaseQuestInstance.State.COMPLETED);
        }
    }
}
