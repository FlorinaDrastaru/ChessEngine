package com.pieces;

import com.board.ChessBoard;
import com.board.Position;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
    }

    @Override
    public void move() {
        ChessBoard board = ChessBoard.getInstance();
        board.putChessPiece(this, new Position(5, 0));
        System.out.println("move a7a6");
    }
}
