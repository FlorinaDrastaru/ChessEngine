package com.commands;

public class Resign implements Command {
    @Override
    public void executeCommand() {
        System.out.print("resign\n");
        System.exit(0);
    }
}
