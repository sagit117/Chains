package ru.axel;

import org.jetbrains.annotations.NotNull;
import ru.axel.chains.Block;

public class PrintTest extends Block<String, String> {
    public PrintTest(String externalParameter) {
        super(externalParameter);
    }

    @Override
    public @NotNull String execute() {
        System.out.println(externalParameter);
        return externalParameter;
    }
}
