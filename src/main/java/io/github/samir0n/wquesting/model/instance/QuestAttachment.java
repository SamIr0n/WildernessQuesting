package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import io.github.samir0n.wquesting.model.definition.AttachmentTypes;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
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

    public BaseQuestInstance startQuest(final ServerPlayer player, final Holder<QuestDefinition> definition) {
        final BaseQuestInstance instance = definition.value().behaviour().createInstance(definition);
        this.instance = instance;
        instance.update(player, new QuestStartEvent());
        return instance;
    }

    public void cancelQuest(final ServerPlayer player) {
        player.removeData(AttachmentTypes.QUEST_ATTACHMENT);
    }
}
