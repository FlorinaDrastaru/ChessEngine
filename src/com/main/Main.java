package com.main;

import com.commands.*;
import com.constants.Constants;

import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            String command = sc.nextLine();
            switch(command) {
                case Constants.go: new Go().executeCommand();
                case Constants.newGame: new NewGame().executeCommand();
                case Constants.white: new White().executeCommand();
                case Constants.black: new Black().executeCommand();
                case Constants.force: new Force().executeCommand();
                case Constants.quit: break;
                case Constants.resign: new Resign().executeCommand();
                case Constants.xboard: new Xboard().executeCommand();
            }
        }
    }

}
