package com.commands;

import com.game.ChessGame;
import com.main.Solver;
import com.pieces.TeamColour;

// force the engine to play with White colour
// reset the direction of moving on board
public class White implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setSign(1);
        ChessGame.getInstance().setColour(TeamColour.White);
    }
}
