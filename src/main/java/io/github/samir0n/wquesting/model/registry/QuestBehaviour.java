package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import net.minecraft.core.Holder;

import static io.github.samir0n.wquesting.model.registry.BehaviourType.BEHAVIOUR_TYPE_REGISTRY;

/**
 * - Describe behaviour of a quest definition
 */
public abstract class QuestBehaviour {

    public abstract BehaviourType<?> getType();

    public abstract MapCodec<? extends BaseQuestInstance> instanceCodec(Holder<QuestDefinition> definition);

    public static final Codec<QuestBehaviour> CODEC = BEHAVIOUR_TYPE_REGISTRY.byNameCodec().dispatch("type", QuestBehaviour::getType, BehaviourType::codec);

}
