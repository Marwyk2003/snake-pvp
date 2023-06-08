package com.example.snakepvp.services;

import java.util.function.Consumer;

public interface ViewerService {
    <E> Subscription subscribe(Consumer<E> consumer, Emitter<E> emitter);
}
