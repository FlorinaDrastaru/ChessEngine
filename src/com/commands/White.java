package com.commands;

import com.game.ChessGame;
import com.pieces.TeamColour;

public class White implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setWhite(true);
        ChessGame.getInstance().setSign(1);
        ChessGame.getInstance().setColour(TeamColour.White);
        ChessGame.getInstance().move();
    }
}
