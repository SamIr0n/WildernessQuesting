package io.github.samir0n.wquesting.model.instance;

import net.minecraft.core.Holder;

/**
 * An instance of something, the structure of which is defined via a registry value.
 * */
public interface Instance<T> {
    Holder<T> definition();
}
