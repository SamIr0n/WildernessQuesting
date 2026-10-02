package io.github.samir0n.wquesting.model.definition;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

import java.util.Set;

import static com.mojang.serialization.DataResult.success;
import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@SuppressWarnings("UnstableApiUsage")
public record ChapterDefinition(
    Component name, Component description, Icon<?> icon,
    Graph<Holder<QuestDefinition>> quests
) {
    public static final ResourceKey<Registry<ChapterDefinition>> REGISTRY_KEY = createRegistryKey(fromNamespaceAndPath(MOD_ID, "chapters"));
    public static final Codec<Graph<Holder<QuestDefinition>>> GRAPH_CODEC = QuestDefinition.REF_SET_CODEC.flatXmap(ChapterDefinition::buildGraph, g -> success(g.nodes()));

    public static final Codec<ChapterDefinition> CODEC = RecordCodecBuilder.create(i -> i.group(
        ComponentSerialization.CODEC.optionalFieldOf("name", Component.empty()).forGetter(ChapterDefinition::name),
        ComponentSerialization.CODEC.optionalFieldOf("description", Component.empty()).forGetter(ChapterDefinition::description),
        Icon.CODEC.optionalFieldOf("icon", Icon.defaultIcon()).forGetter(ChapterDefinition::icon),
        GRAPH_CODEC.optionalFieldOf("quests", ImmutableGraph.copyOf(GraphBuilder.directed().build())).forGetter(ChapterDefinition::quests)
    ).apply(i, ChapterDefinition::new));

    public static final Codec<Holder<ChapterDefinition>> REF_CODEC = RegistryFixedCodec.create(REGISTRY_KEY);

    private static DataResult<Graph<Holder<QuestDefinition>>> buildGraph(final Set<Holder<QuestDefinition>> quests) {
        final MutableGraph<Holder<QuestDefinition>> g = GraphBuilder.directed().allowsSelfLoops(false).build();
        for (final Holder<QuestDefinition> q : quests) {
            if (!q.isBound()) return DataResult.error(() -> "Unknown quest " + q.unwrapKey().map(k -> k.location().toString()).orElse("?"));
            g.addNode(q);
        }
        for (final Holder<QuestDefinition> q : quests)
            for (final Holder<QuestDefinition> req : q.value().requires())
                g.putEdge(req, q);
        return DataResult.success(ImmutableGraph.copyOf(g));
    }
}
