package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.registry.ChatQuestBehaviour;
import net.minecraft.core.Holder;

/**
 * Represents the state of a chat quest instance, i.e. the number of messages the player has sent thus far.
 * @see ChatQuestBehaviour
 * */
public class ChatQuestInstance extends BaseQuestInstance {

    public int messageCount = 0;

    public ChatQuestInstance(Holder<QuestDefinition> definition, State state, int messageCount) {
        super(definition, state);
        this.messageCount = messageCount;
    }

    public ChatQuestInstance(Holder<QuestDefinition> definition) {
        super(definition);
    }

    public static MapCodec<ChatQuestInstance> codec(Holder<QuestDefinition> definition) {
        return RecordCodecBuilder.mapCodec(i -> commonFields(i).and(
            Codec.INT.optionalFieldOf("message_count", 0).forGetter(instance -> instance.messageCount)
        ).apply(i, (state, messageCount) -> new ChatQuestInstance(definition, state, messageCount)));
    }
}
