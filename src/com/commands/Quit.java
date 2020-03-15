package com.commands;

// end the game
public class Quit implements Command {
    @Override
    public void executeCommand() {
        System.exit(0);
    }
}
