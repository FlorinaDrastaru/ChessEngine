package com.commands;

import com.game.ChessGame;
import com.pieces.TeamColour;

// reset the colour of the engine to black 
// and the direction of moving on the board 
public class Black implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setSign(-1);
        ChessGame.getInstance().setColour(TeamColour.Black);
    }
}


