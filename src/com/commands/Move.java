package com.commands;


import com.board.ChessBoard;
import com.board.Position;
import com.pieces.Pawn;

public class Move implements Command {
    @Override
    public void executeCommand() {
        ChessBoard board = ChessBoard.getInstance();
        Pawn pawn = (Pawn) board.getChessPiece(new Position(1, 0));
        pawn.move();

    }
}
