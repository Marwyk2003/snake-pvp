package com.example.snakepvp.viewmodels;

import com.example.snakepvp.Program;
import de.saxsys.mvvmfx.ViewModel;

public class HelloViewModel implements ViewModel {
    Program program;

    public HelloViewModel(Program program) {
        this.program = program; // TODO quite ugly but good for now
    }

    public void startGame() {
        program.loadGame();
    }
}
