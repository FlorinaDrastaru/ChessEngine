package com.pieces;

import com.board.Position;

public class Rook extends ChessPiece {
    public Rook(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
        idx = 1;
    }

    @Override
    public void move(Position position) {

    }
}
