package io.github.samir0n.wquesting.model.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

public record StoryDefinition(List<Holder<ChapterDefinition>> chapters) {
    public static final ResourceKey<Registry<StoryDefinition>> REGISTRY_KEY = createRegistryKey(fromNamespaceAndPath(MOD_ID, "stories"));
    public static final Codec<StoryDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ChapterDefinition.REF_CODEC.listOf().fieldOf("chapters").forGetter(StoryDefinition::chapters)
    ).apply(instance, StoryDefinition::new));
}
