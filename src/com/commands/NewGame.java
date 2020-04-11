package com.commands;

import com.board.ChessBoard;
import com.game.ChessGame;
import com.pieces.TeamColour;

// initiate a new game
public class NewGame implements Command {
    @Override
    public void executeCommand() {
        ChessBoard chessBoard = ChessBoard.getInstance();
        // reinitiate the board
        chessBoard.initiateBoard();
        // set the default colour of the game
        ChessGame.getInstance().setColour(TeamColour.Black);
        // set the default way of moving on the table
        ChessGame.getInstance().setSign(-1);
        // set the default team to move first after force
        ChessGame.getInstance().setTeamToMove(TeamColour.White);
    }
}
