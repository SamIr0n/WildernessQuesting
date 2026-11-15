package io.github.samir0n.wquesting.model.definition;

import io.github.samir0n.wquesting.model.instance.QuestAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

public class AttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<QuestAttachment>> QUEST_ATTACHMENT = ATTACHMENT_TYPES.register("quest_attachment",
            () -> AttachmentType.builder(() -> new QuestAttachment(null)).serialize(QuestAttachment.CODEC).copyOnDeath().build()
    );

}
