package com.example.snakepvp.services;

import java.util.function.Consumer;

public interface Emitter<E> {
    Subscription subscribe(Consumer<E> c);
}
