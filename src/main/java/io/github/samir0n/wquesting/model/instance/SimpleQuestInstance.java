package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.core.Holder;

public class SimpleQuestInstance implements QuestInstance {

    @Override
    public QuestState state() {
        return null;
    }

    @Override
    public void update(QuestEvent event) {

    }

    @Override
    public Holder<QuestDefinition> definition() {
        return null;
    }

    public int messageCount = 0;

    public SimpleQuestInstance(final int messageCount) {
        this.messageCount = messageCount;
    }
    
    public SimpleQuestInstance() {
    }   

    public static final Codec<SimpleQuestInstance> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.INT.fieldOf("message_count").forGetter((instance) -> instance.messageCount)
    ).apply(i, SimpleQuestInstance::new));
}
