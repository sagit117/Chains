package ru.axel.chains;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class BlockFactory {
    @SuppressWarnings("unchecked")
    public static <NextBlock extends Block<?,?>, P> @NotNull NextBlock next(
            @NotNull Class<NextBlock> nextBlockClass,
            P externalParameter
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
                .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
                .findFirst();
        final Constructor<NextBlock> constructor = optionalConstructor.orElseThrow();

        return constructor.newInstance(externalParameter);
    }
}
