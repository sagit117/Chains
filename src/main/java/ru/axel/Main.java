package ru.axel;

import ru.axel.chains.BlockFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("TEST START");
        BlockFactory
                .first(TestBlock.class, "test")
                .next(PrintTest.class, parameter -> parameter + 1)
                .next(PrintTest.class, parameter -> parameter + 2);
    }
}

