package ru.axel.chains;

public interface PrepareResult<P, R> {
    R prepare(P parameter);
}
