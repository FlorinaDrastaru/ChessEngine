package com.main;

import com.board.ChessBoard;
import com.commands.*;
import com.constants.Constants;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                case Constants.quit: System.exit(1);
                case Constants.resign: new Resign().executeCommand();
                break;
                case Constants.xboard: new Xboard().executeCommand();
                break;
                default:
                    if (command.startsWith("usermove"))
                        new Move().executeCommand();
                    break;

            }

        }

//        while (true) {
//            String command = sc.nextLine();
//            if("quit".equals(command)) {
//                System.exit(0);
//            } else if("xboard".equals(command)) {
//                ChessBoard.getInstance().initiateBoard();
//                infoBox("xboard hit", "still works");
//            } else if("new".equals(command)) {
//                ChessBoard.getInstance().initiateBoard();
//            } else if (command.equals("usermove")) {
//                new Move().executeCommand();
//            } else if(command.equals("protover 2")) {
//                System.out.println("feature sigint=0");
//            } else {
//                new Move().executeCommand();
//            }
//        }

    }

}
