package com.pieces;

import com.board.Position;

public class Knight extends ChessPiece {
    public Knight(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
        idx = 2;
    }

    @Override
    public void move(Position position) {

    }
}
