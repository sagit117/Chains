package ru.axel.chains;

import org.jetbrains.annotations.NotNull;
import ru.axel.chains.exceptions.FindFirstConstructorException;

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

    protected Block(Parameter externalParameter) {
        this.externalParameter = externalParameter;
    }

    public <NextBlock extends Block<?,?>> NextBlock next(
        @NotNull Class<NextBlock> nextBlockClass
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException, FindFirstConstructorException {
        final Constructor<NextBlock> constructor = getFirstConstructor(nextBlockClass);

        return constructor.newInstance(execute());
    }

    public <NextBlock extends Block<?,?>> NextBlock next(
        @NotNull Class<NextBlock> nextBlockClass,
        @NotNull PrepareResult<Result,?> prepareResult
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException, FindFirstConstructorException {
        final Constructor<NextBlock> constructor = getFirstConstructor(nextBlockClass);
        final Object res = prepareResult.prepare(execute());

        return constructor.newInstance(res);
    }

    @SuppressWarnings("unchecked")
    private <NextBlock extends Block<?,?>> Constructor<NextBlock> getFirstConstructor(
            @NotNull Class<NextBlock> nextBlockClass
    ) throws FindFirstConstructorException {
        final Optional<Constructor<NextBlock>> optionalConstructor = Arrays
                .stream((Constructor<NextBlock>[]) nextBlockClass.getConstructors())
                .findFirst();

        return optionalConstructor.orElseThrow(
                () -> new FindFirstConstructorException("Первичный конструктор у блока цепочки не найден, либо он не публичный")
        );
    }

    public Result getResult() {
        return execute();
    }

    protected abstract @NotNull Result execute();
}
