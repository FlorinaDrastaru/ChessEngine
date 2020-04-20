package com.main;

import com.commands.*;
import com.constants.Constants;
import com.game.ChessGame;
import com.pieces.TeamColour;

public class Solver {
    public static void solver(String command) {
        switch(command) {
            case Constants.protover:
                System.out.print("feature sigint=0\n");
                System.out.print("feature sigterm=0\n");
                System.out.print("feature usermove=1\n");
                System.out.print("feature san=0\n");
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
            case Constants.quit:new Quit().executeCommand();
            case Constants.resign: new Resign().executeCommand();
                break;
            case Constants.xboard: new Xboard().executeCommand();
                break;
            default:
                if (command.startsWith(Constants.usermove)) {
                    String opponentMove = command.substring(9);
                    userMove(opponentMove);
                }
                break;

        }
    }

    public static void userMove(String opponentMove) {
        if (ChessGame.getInstance().isForce()) {
            ChessGame.getInstance().oppMove(opponentMove);
            changeTeamToMove();
        } else {
            ChessGame.getInstance().oppMove(opponentMove);
            ChessGame.getInstance().move();
        }
    }

    public static void changeTeamToMove() {
        if (ChessGame.getInstance().getTeamToMove().equals(TeamColour.White))
            ChessGame.getInstance().setTeamToMove(TeamColour.Black);
        else
            ChessGame.getInstance().setTeamToMove(TeamColour.White);
    }
}
