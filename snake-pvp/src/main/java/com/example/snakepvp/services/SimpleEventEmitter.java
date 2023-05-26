package com.example.snakepvp.services;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleEventEmitter<E extends Event> implements Emitter<E> {
    private final Set<Consumer<E>> subscriptions = new HashSet<>();

    @Override
    public synchronized Subscription subscribe(Consumer<E> c) {
        subscriptions.add(c);
        return () -> {
            synchronized (subscriptions) {
                subscriptions.remove(c);
            }
        };
    }

    public synchronized void emit(E event) {
        for (Consumer<E> s : subscriptions) {
            s.accept(event);
        }
    }
}
