package com.stout.day20;

public class PositionedNumber<T extends Number> {
    private T number;

    public PositionedNumber(T number) {
        this.number = number;
    }

    public T get() {
        return number;
    }

    @Override
    public String toString() {
        return number + "";
    }
}
