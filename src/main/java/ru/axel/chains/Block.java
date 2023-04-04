package ru.axel.chains;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Блок цепи.
 * @param <Parameter> входящий параметр.
 * @param <Result> возвращаемый результат.
 */
public abstract class Block<Parameter, Result> {
    final protected Parameter externalParameter;
//    protected Result result;

    public Block(Parameter externalParameter) {
        this.externalParameter = externalParameter;
    }

    @SuppressWarnings("unchecked")
    public <NextBlock extends Block<?,?>> NextBlock next(
        @NotNull Class<NextBlock> nextBlockClass
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
            .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
            .findFirst();
        final Constructor<NextBlock> constructor = optionalConstructor.orElseThrow();
        final NextBlock nextBlock = constructor.newInstance(execute());
//        nextBlock.execute();

        return nextBlock;
    }

    @SuppressWarnings("unchecked")
    public <NextBlock extends Block<?,?>> NextBlock next(
        @NotNull Class<NextBlock> nextBlockClass,
        @NotNull PrepareResult<Result,?> prepareResult
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
            .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
            .findFirst();
        final Constructor<NextBlock> constructor = optionalConstructor.orElseThrow();
        final Object res = prepareResult.prepare(execute());
        final NextBlock nextBlock = constructor.newInstance(res);
//        nextBlock.execute();

        return nextBlock;
    }

    public Result getResult() {
        return execute();
    }

    protected abstract @NotNull Result execute();
}
