package com.commands;

import com.game.ChessGame;

public class Force implements Command {
    @Override
    public void executeCommand() {
        ChessGame.getInstance().setForce(true);
    }
}
