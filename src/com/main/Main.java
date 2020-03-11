package com.main;

import com.commands.*;
import com.constants.Constants;
import com.game.ChessGame;

import javax.swing.*;
import java.util.Scanner;


public class Main {
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(final String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            switch(command) {
                case Constants.protover:
                    System.out.print("feature sigint=0\n");
                    System.out.print("feature sigterm=0\n");
                    System.out.print("feature usermove=1\n");
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
                case Constants.quit: System.exit(0);
                case Constants.resign: new Resign().executeCommand();
                break;
                case Constants.xboard: new Xboard().executeCommand();
                break;
                default:
                    if (command.startsWith("usermove")) {
                        String opponentMove = command.substring(9);
                        if (ChessGame.getInstance().isForce() == true) {
                            ChessGame.getInstance().oppMove(opponentMove);
                        } else {
                            ChessGame.getInstance().oppMove(opponentMove);
                            ChessGame.getInstance().move();
                        }
                    }
                    break;

            }

        }


    }

}
