package com.commands;

import com.game.ChessGame;

public class Go implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setForce(false);
        ChessGame.getInstance().move();
    }
}
