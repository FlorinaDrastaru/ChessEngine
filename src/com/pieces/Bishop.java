package com.pieces;

import com.board.ChessBoard;
import com.board.Position;

import java.util.LinkedList;

public class Bishop extends ChessPiece {
    public Bishop(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    @Override
    public void move(Position position) {

    }

    // acelasi principiu ca la Rook
    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<Position>();
        int i = 0;

        // ma duc in dreapta sus
        while(pos.getRow() + i < 8 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() + i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // ma duc stanga sus
        i = 0;
        while (pos.getRow() + i < 8 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() - i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }
        // ma duc in jos, spre stanga
        i = 0;
        while(pos.getRow() - i >= 0 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() - i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // ma duc dreapta jos
        i = 0;
        
        while (pos.getRow() - i >= 0 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() + i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
