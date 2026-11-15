package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.registry.CollectionQuestBehaviour;
import net.minecraft.core.Holder;

/**
 * Represents the state of a chat quest instance, i.e. the number of messages the player has sent thus far.
 * @see CollectionQuestBehaviour
 * */
public class CollectionQuestInstance extends BaseQuestInstance {

    public CollectionQuestInstance(Holder<QuestDefinition> definition, State state) {
        super(definition, state);
    }

    public CollectionQuestInstance(Holder<QuestDefinition> definition) {
        super(definition);
    }

    public static MapCodec<CollectionQuestInstance> codec(Holder<QuestDefinition> definition) {
        return RecordCodecBuilder.mapCodec(i -> commonFields(i).apply(i, (state) -> new CollectionQuestInstance(definition, state)));
    }
}
