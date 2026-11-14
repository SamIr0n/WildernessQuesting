package io.github.samir0n.wquesting.gametests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.github.samir0n.wquesting.model.definition.ChapterDefinition;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.definition.StoryDefinition;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.ChatQuestInstance;
import io.github.samir0n.wquesting.model.registry.ChatQuestBehaviour;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Set;
import java.util.stream.Collectors;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

@GameTestHolder("wquesting")
@PrefixGameTestTemplate(false)
@SuppressWarnings("UnstableApiUsage")
public class QuestRegistryGameTests {
    private static ResourceLocation id(final String path) {
        return fromNamespaceAndPath(MOD_ID, path);
    }

    private static String name(final Holder<QuestDefinition> h) {
        return h.unwrapKey().orElseThrow().location().getPath();
    }

    @GameTest(template = "empty")
    public static void registriesLoad(final GameTestHelper helper) {
        final RegistryAccess access = helper.getLevel().registryAccess();
        final Registry<QuestDefinition> quests = access.registryOrThrow(QuestDefinition.REGISTRY_KEY);
        final Registry<ChapterDefinition> chapters = access.registryOrThrow(ChapterDefinition.REGISTRY_KEY);
        final Registry<StoryDefinition> stories = access.registryOrThrow(StoryDefinition.REGISTRY_KEY);

        helper.assertTrue(quests.containsKey(ResourceKey.create(QuestDefinition.REGISTRY_KEY, id("getting_started/gather_wood"))), "quest wquesting:getting_started/gather_wood was not loaded");
        helper.assertTrue(chapters.containsKey(ResourceKey.create(ChapterDefinition.REGISTRY_KEY, id("getting_started"))), "chapter wquesting:getting_started was not loaded");
        helper.assertTrue(stories.containsKey(ResourceKey.create(StoryDefinition.REGISTRY_KEY, id("wilderness"))), "story wquesting:wilderness was not loaded");

        final QuestDefinition gatherWood = quests.get(ResourceKey.create(QuestDefinition.REGISTRY_KEY, id("getting_started/gather_wood")));
        helper.assertTrue(gatherWood != null, "gather_wood quest missing");
        helper.assertTrue(gatherWood.behaviour() instanceof ChatQuestBehaviour, "gather_wood should have default ChatQuestBehaviour");

        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void chapterGraphIsBuilt(final GameTestHelper helper) {
        final ChapterDefinition chapter = helper.getLevel().registryAccess().registryOrThrow(ChapterDefinition.REGISTRY_KEY)
            .get(ResourceKey.create(ChapterDefinition.REGISTRY_KEY, id("getting_started")));
        helper.assertTrue(chapter != null, "chapter missing");

        //noinspection DataFlowIssue by assertion
        final Set<String> nodes = chapter.quests().nodes().stream().map(QuestRegistryGameTests::name).collect(Collectors.toSet());
        final Set<String> edges = chapter.quests().edges().stream()
            .map(e -> name(e.source()) + "->" + name(e.target())).collect(Collectors.toSet());

        helper.assertTrue(nodes.equals(Set.of("getting_started/craft_table", "getting_started/gather_wood", "getting_started/wooden_tools", "getting_started/welcome")), "unexpected nodes: " + nodes);
        helper.assertTrue(edges.equals(Set.of(
            "getting_started/welcome->getting_started/gather_wood",
                "getting_started/gather_wood->getting_started/craft_table",
                "getting_started/craft_table->getting_started/wooden_tools",
                "getting_started/gather_wood->getting_started/wooden_tools"
            )), "unexpected edges: " + edges);
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void danglingReferenceIsRejected(final GameTestHelper helper) {
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, helper.getLevel().registryAccess());
        final JsonObject json = new JsonObject();
        final JsonArray refs = new JsonArray();
        refs.add("wquesting:does_not_exist");
        json.add("quests", refs);

        helper.assertTrue(ChapterDefinition.CODEC.parse(ops, json).error().isPresent(),
            "a chapter with an unknown quest should fail to decode");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void questInstanceSerializesAndDeserializes(final GameTestHelper helper) {
        final RegistryAccess access = helper.getLevel().registryAccess();
        final Registry<QuestDefinition> quests = access.registryOrThrow(QuestDefinition.REGISTRY_KEY);
        final ResourceKey<QuestDefinition> key = ResourceKey.create(QuestDefinition.REGISTRY_KEY, id("getting_started/gather_wood"));
        final Holder<QuestDefinition> holder = quests.getHolderOrThrow(key);

        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, access);

        final ChatQuestInstance instance = new ChatQuestInstance(holder, BaseQuestInstance.State.IN_PROGRESS, 15);

        final DataResult<JsonElement> encoded = BaseQuestInstance.CODEC.encodeStart(ops, instance);
        helper.assertTrue(encoded.isSuccess(), "Serialization failed: " + encoded);

        final JsonElement json = encoded.getOrThrow();
        helper.assertTrue(json.isJsonObject(), "Expected JSON object");
        final JsonObject jsonObject = json.getAsJsonObject();

        helper.assertTrue(jsonObject.has("definition"), "Expected definition field");
        helper.assertTrue(jsonObject.get("definition").getAsString().equals("wquesting:getting_started/gather_wood"), "Unexpected definition field value");
        helper.assertTrue(jsonObject.has("message_count"), "Expected message_count field");
        helper.assertTrue(jsonObject.get("message_count").getAsInt() == 15, "Unexpected message_count");

        final DataResult<BaseQuestInstance> decoded = BaseQuestInstance.CODEC.parse(ops, json);
        helper.assertTrue(decoded.isSuccess(), "Deserialization failed: " + decoded);

        final BaseQuestInstance decodedInstance = decoded.getOrThrow();
        helper.assertTrue(decodedInstance instanceof ChatQuestInstance, "Decoded instance should be ChatQuestInstance");

        final ChatQuestInstance decodedChatInstance = (ChatQuestInstance) decodedInstance;
        helper.assertTrue(decodedChatInstance.messageCount == 15, "Expected messageCount 15, got " + decodedChatInstance.messageCount);
        helper.assertTrue(decodedChatInstance.state() == BaseQuestInstance.State.IN_PROGRESS, "Expected state IN_PROGRESS");
        helper.assertTrue(decodedChatInstance.definition().equals(holder), "Expected definition to match holder");

        helper.succeed();
    }
}