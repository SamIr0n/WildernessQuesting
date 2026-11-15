package io.github.samir0n.wquesting.model.definition;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.registry.QuestBehaviour;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

import java.util.Set;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

/**
 * Defines a quest as part of the storyline: its description, placement in the storyline's quest graph, and specific behaviour, e.g. "collect all 900 eggs"
 * */
public record QuestDefinition(Component name, Component description, Icon<?> icon, Set<Holder<QuestDefinition>> requires, QuestBehaviour<?> behaviour) {
    public static final ResourceKey<Registry<QuestDefinition>> REGISTRY_KEY = createRegistryKey(fromNamespaceAndPath(MOD_ID, "quests"));
    public static final Codec<Holder<QuestDefinition>> REF_CODEC = RegistryFixedCodec.create(REGISTRY_KEY);

    public static final Codec<Set<Holder<QuestDefinition>>> REF_SET_CODEC =
        REF_CODEC.listOf().xmap(ImmutableSet::copyOf, ImmutableList::copyOf);

    public static final Codec<QuestDefinition> CODEC = RecordCodecBuilder.create(i -> i.group(
        ComponentSerialization.CODEC.optionalFieldOf("name", Component.empty()).forGetter(QuestDefinition::name),
        ComponentSerialization.CODEC.optionalFieldOf("description", Component.empty()).forGetter(QuestDefinition::description),
        Icon.CODEC.optionalFieldOf("icon", Icon.defaultIcon()).forGetter(QuestDefinition::icon),
        REF_SET_CODEC.optionalFieldOf("requires", Set.of()).forGetter(QuestDefinition::requires),
        QuestBehaviour.CODEC.fieldOf("behaviour").forGetter(QuestDefinition::behaviour)
    ).apply(i, QuestDefinition::new));
}



