package com.commands;

import com.board.ChessBoard;

public class NewGame implements Command {
    @Override
    public void executeCommand() {
        ChessBoard chessBoard = ChessBoard.getInstance();
        chessBoard.initiateBoard();
    }
}
