package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.core.Holder;

public class ChatQuestInstance extends BaseQuestInstance {

    public int messageCount = 0;

    public ChatQuestInstance(Holder<QuestDefinition> definition, State state, int messageCount) {
        super(definition, state);
        this.messageCount = messageCount;
    }

    public ChatQuestInstance(Holder<QuestDefinition> definition, int messageCount) {
        super(definition);
        this.messageCount = messageCount;
    }

    public ChatQuestInstance(Holder<QuestDefinition> definition) {
        super(definition);
    }

    public ChatQuestInstance() {
    }

    @Override
    public void update(QuestEvent event) {

    }

    public static MapCodec<ChatQuestInstance> codec(Holder<QuestDefinition> definition) {
        return RecordCodecBuilder.mapCodec(i -> i.group(
            State.CODEC.optionalFieldOf("state", State.IN_PROGRESS).forGetter(BaseQuestInstance::state),
            Codec.INT.optionalFieldOf("message_count", 0).forGetter(instance -> instance.messageCount)
        ).apply(i, (state, messageCount) -> new ChatQuestInstance(definition, state, messageCount)));
    }
}
