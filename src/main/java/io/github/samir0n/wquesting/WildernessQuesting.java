package io.github.samir0n.wquesting;

import io.github.samir0n.wquesting.model.definition.ChapterDefinition;
import io.github.samir0n.wquesting.model.definition.Icon;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.definition.StoryDefinition;
import io.github.samir0n.wquesting.model.definition.ModIcons;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

@Mod(MOD_ID)
public class WildernessQuesting {
    public static final String MOD_ID = "wquesting";

    public WildernessQuesting(final IEventBus eventBus, final ModContainer container) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerRegistries);
        eventBus.addListener(this::registerDatapackRegistries);

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
}



