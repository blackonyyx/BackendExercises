package org.example;

public sealed interface Currency permits USD, EUR, GBP {}

record USD() implements Currency {
    @Override
    public final String toString() {
        return "USD";
    }
}
record EUR() implements Currency {
    @Override
    public final String toString() {
        return "EUR";
    }
}
record GBP() implements Currency {
    @Override
    public final String toString() {
        return "GBP";
    }
}

