package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

/**
 * A kind of behaviour declared for a quest, i.e. that the player must collect some number of some item.
 * */
public record BehaviourType<T extends QuestBehaviour<?>>(MapCodec<T> codec) {
    public static final ResourceKey<Registry<BehaviourType<?>>> REGISTRY_KEY = createRegistryKey(fromNamespaceAndPath(MOD_ID, "behaviour_types"));

    public static final DeferredRegister<BehaviourType<?>> BEHAVIOUR_TYPES = DeferredRegister.create(REGISTRY_KEY, MOD_ID);
    public static final Registry<BehaviourType<?>> BEHAVIOUR_TYPE_REGISTRY = BEHAVIOUR_TYPES.makeRegistry(builder -> builder.sync(true));

    public static final Supplier<BehaviourType<ChatQuestBehaviour>> CHAT = BEHAVIOUR_TYPES.register("chat", () -> new BehaviourType<>(ChatQuestBehaviour.CODEC));
}
