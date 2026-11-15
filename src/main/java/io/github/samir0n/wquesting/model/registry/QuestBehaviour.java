package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.QuestEvent;
import net.minecraft.core.Holder;

import static io.github.samir0n.wquesting.model.registry.BehaviourType.BEHAVIOUR_TYPE_REGISTRY;

/**
 * The behaviour for a quest definition, including the {@link BehaviourType} and the specific settings for that behaviour type, i.e. the number of items to be collected.
 */
public abstract class QuestBehaviour<I extends BaseQuestInstance> {

    public abstract BehaviourType<?> getType();

    public abstract MapCodec<I> instanceCodec(Holder<QuestDefinition> definition);

    public abstract I createInstance(Holder<QuestDefinition> definition);

    public abstract void handle(I instance, QuestEvent event);

    public static final Codec<QuestBehaviour<?>> CODEC = BEHAVIOUR_TYPE_REGISTRY.byNameCodec().dispatch("type", QuestBehaviour::getType, BehaviourType::codec);

}
