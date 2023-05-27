package com.example.snakepvp.services;

import com.example.snakepvp.core.Edible;

public class EdibleEvent {
    GameService owner;
    Edible edible;

    EdibleEvent(Edible edible, GameService owner) {
        this.edible = edible;
        this.owner = owner;
    }

    public Edible getEdible() {
        return edible;
    }

    public GameService getOwner() {
        return owner;
    }
}
