package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.MapCodec;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.instance.ChatQuestInstance;
import net.minecraft.core.Holder;

public class ChatQuestBehaviour extends QuestBehaviour {

    public static final MapCodec<ChatQuestBehaviour> CODEC = MapCodec.unit(ChatQuestBehaviour::new);

    @Override
    public BehaviourType<?> getType() {
        return BehaviourType.CHAT.get();
    }

    @Override
    public MapCodec<ChatQuestInstance> instanceCodec(Holder<QuestDefinition> definition) {
        return ChatQuestInstance.codec(definition);
    }
}
