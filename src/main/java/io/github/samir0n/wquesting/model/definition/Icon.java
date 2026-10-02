package io.github.samir0n.wquesting.model.definition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Function;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;
import static net.minecraft.resources.ResourceKey.createRegistryKey;
import static net.minecraft.resources.ResourceLocation.fromNamespaceAndPath;

public interface Icon<Self extends Icon<Self>> {
    ResourceKey<Registry<MapCodec<? extends Icon<?>>>> TYPE_KEY = createRegistryKey(fromNamespaceAndPath(MOD_ID, "icon_type"));
    Registry<MapCodec<? extends Icon<?>>> TYPES = new RegistryBuilder<>(TYPE_KEY).create();
    Codec<Icon<?>> CODEC = TYPES.byNameCodec().dispatch(Icon::codec, Function.identity());
    MapCodec<Self> codec();

    record ItemIcon(ResourceLocation path) implements Icon<ItemIcon> {
        public static final MapCodec<ItemIcon> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceLocation.CODEC.fieldOf("value").forGetter(ItemIcon::path)
        ).apply(i, ItemIcon::new));

        @Override public MapCodec<ItemIcon> codec() { return CODEC; }
    }

    record TextureIcon(ResourceLocation path) implements Icon<TextureIcon> {
        public static final MapCodec<TextureIcon> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ResourceLocation.CODEC.fieldOf("value").forGetter(TextureIcon::path)
        ).apply(i, TextureIcon::new));

        @Override public MapCodec<TextureIcon> codec() { return CODEC; }
    }

    static ItemIcon item(final ResourceLocation path) {
        return new ItemIcon(path);
    }

    static TextureIcon texture(final ResourceLocation path) {
        return new TextureIcon(path);
    }

    static Icon<?> defaultIcon() {
        return new ItemIcon(ResourceLocation.withDefaultNamespace("book"));
    }
}
