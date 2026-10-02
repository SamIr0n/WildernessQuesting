package io.github.samir0n.wquesting.gametests;

import com.google.common.graph.EndpointPair;
import io.github.samir0n.wquesting.model.definition.ChapterDefinition;
import io.github.samir0n.wquesting.model.definition.Icon;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.definition.StoryDefinition;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public final class StoryMermaid {
    private StoryMermaid() {
    }

    public static void printMermaid(final StoryDefinition story) {
        System.out.println(toMermaid(story));
    }

    public static String toMermaid(final StoryDefinition story) {
        final StringBuilder sb = new StringBuilder("flowchart TD\n");
        final Set<String> edges = new LinkedHashSet<>();
        final List<Holder<ChapterDefinition>> chapters = story.chapters();

        for (int i = 0; i < chapters.size(); i++) {
            final Holder<ChapterDefinition> holder = chapters.get(i);
            final ChapterDefinition chapter = holder.value();

            sb.append("  subgraph chapter_").append(i).append("[\"")
                .append(nodeLabel(holder, chapter.name(), chapter.description(), chapter.icon()))
                .append("\"]\n");

            for (final Holder<QuestDefinition> q : chapter.quests().nodes()) {
                final QuestDefinition def = q.value();
                sb.append("    ").append(id(q)).append("[\"")
                    .append(nodeLabel(q, def.name(), def.description(), def.icon()))
                    .append("\"]\n");
            }
            sb.append("  end\n");

            for (final EndpointPair<Holder<QuestDefinition>> e : chapter.quests().edges()) {
                edges.add("  " + id(e.source()) + " --> " + id(e.target()));
            }
        }

        edges.forEach(e -> sb.append(e).append('\n'));

        for (int i = 0; i + 1 < chapters.size(); i++) {
            sb.append("  chapter_").append(i).append(" ==> chapter_").append(i + 1).append('\n');
        }
        return sb.toString();
    }

    private static String nodeLabel(final Holder<?> holder, final Component name,
                                    final Component description, final Icon<?> icon) {
        final String title = name.getString().isBlank() ? key(holder) : name.getString();
        final StringBuilder l = new StringBuilder();
        l.append("<b>").append(esc(title)).append("</b>");
        l.append("<br/><i>").append(esc(key(holder))).append("</i>");
        if (!description.getString().isBlank()) {
            l.append("<br/>").append(esc(description.getString()));
        }
        l.append("<br/>icon: ").append(esc(describe(icon)));
        return l.toString();
    }

    private static String describe(final Icon<?> icon) {
        return switch (icon) {
            case final Icon.ItemIcon i -> "item " + i.path();
            case final Icon.TextureIcon t -> "texture " + t.path();
            default -> icon.toString();
        };
    }

    private static String key(final Holder<?> h) {
        return h.unwrapKey().map(k -> k.location().toString()).orElse("unknown");
    }

    private static String id(final Holder<?> h) {
        return "q_" + key(h).replaceAll("[^A-Za-z0-9_]", "_");
    }

    /**
     * Escape text for use inside a quoted Mermaid label that allows HTML.
     */
    private static String esc(final String s) {
        return s.replace("&", "#amp;")
            .replace("\"", "#quot;")
            .replace("<", "#lt;")
            .replace(">", "#gt;")
            .replaceAll("\\s*[\\r\\n]+\\s*", "<br/>");
    }
}