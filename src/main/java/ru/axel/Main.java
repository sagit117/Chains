package ru.axel;

import org.jetbrains.annotations.NotNull;
import ru.axel.chains.Block;
import ru.axel.chains.BlockFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        System.out.println("TEST START");
        BlockFactory.next(TestBlock.class, "test").next(PrintTest.class).next(PrintTest.class);
    }
}

class TestBlock extends Block<String, String> {
    public TestBlock(String externalParameter) {
        super(externalParameter);
    }

    @Override
    public @NotNull String execute() {
        return externalParameter + " chains";
    }
}

class PrintTest extends Block<String, String> {
    public PrintTest(String externalParameter) {
        super(externalParameter);
    }

    @Override
    public @NotNull String execute() {
        System.out.println(externalParameter);
        return externalParameter;
    }
}