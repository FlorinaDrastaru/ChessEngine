package com.commands;

import com.game.ChessGame;
import com.main.Solver;

// force the engine to execute a move
public class Go implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setForce(false);
        // only moves if it is its turn to do so
        if (ChessGame.getInstance().getTeamToMove().equals(ChessGame.getInstance().getColour())) {
            ChessGame.getInstance().move();
            Solver.changeTeamToMove();
        }
    }
}
