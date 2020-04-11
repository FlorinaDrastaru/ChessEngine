package com.pieces;

import com.board.Position;

import java.util.LinkedList;

public class King extends ChessPiece {
    public King(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    @Override
    public void move(Position position) {

    }

    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<>();

        // in fata
        if (pos.getRow() + 1 < 8) {
            // drept
            moves.add(new Position(pos.getRow() + 1, pos.getColumn()));
            //la dreapta
            if (pos.getColumn() + 1 < 8) {
                moves.add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
            }
            // in stanga
            if (pos.getColumn() - 1 >= 0) {
                moves.add(new Position(pos.getRow() + 1, pos.getColumn() - 1));
            }
        }

        // in spate
        if (pos.getRow() - 1 >= 0) {
            // drept
            moves.add(new Position(pos.getRow() - 1, pos.getColumn()));
            // stanga
            if (pos.getColumn() - 1 >= 0) {
                moves.add(new Position(pos.getRow() - 1, pos.getColumn() - 1));
            }
            // dreapta
            if (pos.getColumn() + 1 < 8) {
                moves.add(new Position(pos.getRow() - 1, pos.getColumn() + 1));
            }
        }

        // in dreapta
        if (pos.getColumn() + 1 < 8) {
            moves.add(new Position(pos.getRow(), pos.getColumn() + 1));
        }
        // in stanga
        if (pos.getColumn() - 1 >= 0) {
            moves.add(new Position(pos.getRow(), pos.getColumn() - 1));
        }

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
