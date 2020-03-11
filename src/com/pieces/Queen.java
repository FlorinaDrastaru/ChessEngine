package com.pieces;

import com.board.Position;

public class Queen extends ChessPiece {
    public Queen(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
        idx = 6;
    }

    @Override
    public void move(Position position) {

    }
}
