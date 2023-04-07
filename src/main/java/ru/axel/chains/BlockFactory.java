package ru.axel.chains;

import org.jetbrains.annotations.NotNull;
import ru.axel.chains.exceptions.FindFirstConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class BlockFactory {
    private BlockFactory() { }

    @SuppressWarnings("unchecked")
    public static <NextBlock extends Block<?,?>, P> @NotNull NextBlock first(
        @NotNull Class<NextBlock> nextBlockClass,
        P externalParameter
    ) throws FindFirstConstructorException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
            .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
            .findFirst();
        final Constructor<NextBlock> constructor = optionalConstructor.orElseThrow(
                () -> new FindFirstConstructorException("Первичный конструктор у блока цепочки не найден, либо он не публичный")
        );

        return constructor.newInstance(externalParameter);
    }
}
