package com.pieces;

import com.board.Position;

public class Bishop extends ChessPiece {
    public Bishop(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
        idx = 3;
    }

    @Override
    public void move(Position position) {

    }
}
