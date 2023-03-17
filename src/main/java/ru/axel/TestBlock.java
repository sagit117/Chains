package ru.axel;

import org.jetbrains.annotations.NotNull;
import ru.axel.chains.Block;

public class TestBlock extends Block<String, String> {
    public TestBlock(String externalParameter) {
        super(externalParameter);
    }

    @Override
    public @NotNull String execute() {
        return externalParameter + " chains";
    }
}
