package com.example.snakepvp.services;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleViewerService implements ViewerService {
    private final Set<Subscription> allSubscriptions;
    private final Emitter<GameStatusEvent> statusEmitter;
    private final Emitter<CellEvent> cellEmitter;
    private final Emitter<EdibleEvent> edibleEmitter;

    public SimpleViewerService(Emitter<GameStatusEvent> statusEmitter, Emitter<CellEvent> cellEmitter, Emitter<EdibleEvent> edibleEmitter) {
        allSubscriptions = new HashSet<>();
        this.statusEmitter = statusEmitter;
        this.cellEmitter = cellEmitter;
        this.edibleEmitter = edibleEmitter;
    }

    public void closeAll() {
        for (Subscription sub : allSubscriptions)
            sub.close();
    }

    @Override
    public synchronized <E> Subscription subscribe(Consumer<E> consumer, Emitter<E> emitter) {
        Subscription sub = emitter.subscribe(consumer);
        allSubscriptions.add(emitter.subscribe(consumer));
        return sub;
    }

    public Emitter<CellEvent> cellEvents() {
        return (Consumer<CellEvent> consumer) -> subscribe(consumer, cellEmitter);
    }

    public Emitter<GameStatusEvent> statusEvents() {
        return (Consumer<GameStatusEvent> consumer) -> subscribe(consumer, statusEmitter);
    }

    public Emitter<EdibleEvent> edibleEvents() {
        return (Consumer<EdibleEvent> consumer) -> subscribe(consumer, edibleEmitter);
    }

}
