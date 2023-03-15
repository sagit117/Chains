package ru.axel.doordie;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public final class Do {
    private final DoMethod method;

    Do(@NotNull DoMethod method) {
        this.method = method;
    }

    public void orDie(@NotNull Class<? extends Exception> exceptionClass, String msg) throws Exception {
        boolean isSuccess;

        try {
            isSuccess = method.doMethod();
        } catch (Throwable throwable) {
            throw die(exceptionClass, msg, throwable);
        }

        if (!isSuccess) {
            throw die(exceptionClass, msg);
        }
    }

    private @NotNull Exception die(
        @NotNull Class<? extends Exception> exceptionClass,
        String msg
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<?>> optionalExceptionConstructor = Arrays.stream(
            exceptionClass.getDeclaredConstructors()
        ).findFirst();

        if (optionalExceptionConstructor.isPresent()) {
            final Constructor<?> exceptionConstructor = optionalExceptionConstructor.get();
            return (Exception) exceptionConstructor.newInstance(msg);
        } else {
            return new RuntimeException(msg);
        }
    }

    private @NotNull Exception die(
        @NotNull Class<? extends Exception> exceptionClass,
        String msg,
        Throwable throwable
    ) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Optional<Constructor<?>> optionalExceptionConstructor = Arrays.stream(
            exceptionClass.getDeclaredConstructors()
        ).findFirst();

        if (optionalExceptionConstructor.isPresent()) {
            final Constructor<?> exceptionConstructor = optionalExceptionConstructor.get();
            final Exception exception = (Exception) exceptionConstructor.newInstance(msg);
            exception.initCause(throwable);

            return exception;
        } else {
            return new RuntimeException(msg);
        }
    }
}
