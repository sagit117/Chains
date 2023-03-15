package ru.axel.doordie;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class DoOrDie {
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Do execute(@NotNull DoMethod method) {
        return new Do(method);
    }
}


