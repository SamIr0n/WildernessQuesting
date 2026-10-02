package io.github.samir0n.wquesting.model.definition;

import com.mojang.serialization.MapCodec;
import io.github.samir0n.wquesting.WildernessQuesting;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModIcons {
    public static final DeferredRegister<MapCodec<? extends Icon<?>>> ICON_TYPES =
        DeferredRegister.create(Icon.TYPES, WildernessQuesting.MOD_ID);

    public static final Supplier<MapCodec<Icon.TextureIcon>> TEXTURE = ICON_TYPES.register("texture", () -> Icon.TextureIcon.CODEC);
    public static final Supplier<MapCodec<Icon.ItemIcon>> ITEM = ICON_TYPES.register("item", () -> Icon.ItemIcon.CODEC);
}
