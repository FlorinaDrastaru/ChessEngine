package com.commands;

import com.game.ChessGame;

// force the engine to execute a move
public class Go implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setForce(false);
        ChessGame.getInstance().move();
    }
}
