package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class QuestAttachment {
    public static final Codec<QuestAttachment> CODEC = Codec.optionalField("quest", BaseQuestInstance.CODEC, true).xmap(
            optional -> new QuestAttachment(optional.orElse(null)),
            attachment -> Optional.ofNullable(attachment.getQuest())
    ).codec();

    private @Nullable BaseQuestInstance instance;

    public QuestAttachment(@Nullable final BaseQuestInstance instance) {
        this.instance = instance;
    }

    public @Nullable BaseQuestInstance getQuest() {
        return instance;
    }

    public void setQuest(@Nullable final BaseQuestInstance instance) {
        this.instance = instance;
    }
}
