package com.example.snakepvp.services;

import java.util.function.Consumer;

public interface Emitter<E extends Event> {
    Subscription subscribe(Consumer<E> c);

    void emit(E e);
}
