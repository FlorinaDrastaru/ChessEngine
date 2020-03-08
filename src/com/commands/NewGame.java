package com.commands;

import com.board.ChessBoard;

public class NewGame implements Command {
    @Override
    public void executeCommand() {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.initiateBoard();
    }
}
