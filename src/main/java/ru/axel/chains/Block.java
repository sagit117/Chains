package ru.axel.chains;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public abstract class Block<P, R> {
    final protected P externalParameter;
    final protected R result;

    public Block(P externalParameter) {
        this.externalParameter = externalParameter;
        result = execute();
    }

    @SuppressWarnings("unchecked")
    public <NextBlock extends Block<?,?>> NextBlock next(
            @NotNull Class<NextBlock> nextBlockClass
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
                .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
                .findFirst();
        final Constructor<NextBlock> constructor = optionalConstructor.orElseThrow();

        return constructor.newInstance(result);
    }

    public R getResult() {
        return result;
    }

    protected abstract @NotNull R execute();
}
