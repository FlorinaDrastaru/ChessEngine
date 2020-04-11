package com.pieces;

import com.board.Position;

import java.util.LinkedList;

public class Queen extends ChessPiece {
    public Queen(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    @Override
    public void move(Position position) {

    }

    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<>();

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
