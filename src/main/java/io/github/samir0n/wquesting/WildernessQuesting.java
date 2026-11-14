package io.github.samir0n.wquesting;

import com.mojang.logging.LogUtils;
import io.github.samir0n.wquesting.model.definition.*;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.ChatQuestInstance;
import io.github.samir0n.wquesting.model.registry.BehaviourType;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

@Mod(MOD_ID)
public class WildernessQuesting {
    public static final String MOD_ID = "wquesting";
    public static final Logger LOGGER = LogUtils.getLogger();


    public WildernessQuesting(final IEventBus eventBus, final ModContainer container) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerRegistries);
        eventBus.addListener(this::registerDatapackRegistries);

        AttachmentTypes.ATTACHMENT_TYPES.register(eventBus);
        BehaviourType.BEHAVIOUR_TYPES.register(eventBus);
        NeoForge.EVENT_BUS.addListener(this::onServerChat);

        ModIcons.ICON_TYPES.register(eventBus);
        container.registerConfig(ModConfig.Type.COMMON, WQConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void registerRegistries(final NewRegistryEvent event) {
        event.register(Icon.TYPES);
    }

    private void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(QuestDefinition.REGISTRY_KEY, QuestDefinition.CODEC, QuestDefinition.CODEC);
        event.dataPackRegistry(ChapterDefinition.REGISTRY_KEY, ChapterDefinition.CODEC, ChapterDefinition.CODEC);
        event.dataPackRegistry(StoryDefinition.REGISTRY_KEY, StoryDefinition.CODEC, StoryDefinition.CODEC);
    }

    private void onServerChat(final ServerChatEvent event) {
        final ServerPlayer player = event.getPlayer();
        final BaseQuestInstance questInstance = player.getData(AttachmentTypes.QUEST_INSTANCE);
        if (questInstance instanceof ChatQuestInstance chatQuestInstance) {
            chatQuestInstance.messageCount++;

            LOGGER.info("Player {} has sent {} message/s", player.getGameProfile().getName(), chatQuestInstance.messageCount);
        }
    }
}



