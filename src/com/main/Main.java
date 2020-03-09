package com.main;

import com.commands.*;
import com.constants.Constants;
import com.game.ChessGame;

import java.util.Scanner;




public class Main {
    public static void main(final String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 4; i++){
            String command = sc.nextLine();
            switch(command) {
                case Constants.protover:
                    System.out.println("feature sigint=0");
                    System.out.println("feature sigterm=0");
                    break;
                case Constants.go: new Go().executeCommand();
                break;
                case Constants.newGame: new NewGame().executeCommand();
                break;
                case Constants.white: new White().executeCommand();
                break;
                case Constants.black: new Black().executeCommand();
                break;
                case Constants.force: new Force().executeCommand();
                break;
                case Constants.quit: System.exit(1);
                case Constants.resign: new Resign().executeCommand();
                break;
                case Constants.xboard: new Xboard().executeCommand();
                break;
                case Constants.move: new Move().executeCommand();
                break;
                default:break;

            }
        }
    }

}
