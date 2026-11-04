package io.github.samir0n.wquesting.model.definition;

import io.github.samir0n.wquesting.WildernessQuesting;
import io.github.samir0n.wquesting.model.instance.QuestInstance;
import io.github.samir0n.wquesting.model.instance.SimpleQuestInstance;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

public class AttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<SimpleQuestInstance>> QUEST_INSTANCE = ATTACHMENT_TYPES.register("quest_instance",
            () -> AttachmentType.builder(() -> new SimpleQuestInstance()).serialize(SimpleQuestInstance.CODEC).copyOnDeath().build()
    );

}
