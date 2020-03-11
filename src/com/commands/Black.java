package com.commands;

import com.game.ChessGame;
import com.pieces.TeamColour;

public class Black implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setWhite(false);
        ChessGame.getInstance().setSign(-1);
        ChessGame.getInstance().setColour(TeamColour.Black);
        ChessGame.getInstance().move();
    }
}
