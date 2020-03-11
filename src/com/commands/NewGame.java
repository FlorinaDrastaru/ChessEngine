package com.commands;

import com.board.ChessBoard;
import com.game.ChessGame;
import com.pieces.TeamColour;

public class NewGame implements Command {
    @Override
    public void executeCommand() {
        ChessBoard chessBoard = ChessBoard.getInstance();
        chessBoard.initiateBoard();
        ChessGame.getInstance().setColour(TeamColour.Black);
        ChessGame.getInstance().setSign(-1);
    }
}
