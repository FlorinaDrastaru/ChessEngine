package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.game.Check;

import java.util.LinkedList;


public class King extends ChessPiece {
    public King(TeamColour colour, boolean eliminated, int worth, int rating[][]) {
        super(colour, eliminated, worth, rating);
        idx = 5;
    }

    public void addMove(LinkedList<Move> moves, Position src, Position dest) {
        if (!ChessBoard.getInstance().verifyPosition(dest)) {
            ChessPiece piece =  Check.probeMove(src, dest);                                
            if (!Check.attackedPos(dest))
                moves.add(new Move(src, dest));
            Check.undoMove(src, dest, piece);
        }
    }

    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();
        // in fata
            // drept
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn()));
            //la dreapta
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() + 1));
            // in stanga
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() - 1));
        // in spate
            // drept
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn()));
            // stanga
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() - 1));
            // dreapta
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() + 1));
        // in dreapta
        addMove(moves, pos, new Position(pos.getRow(), pos.getColumn() + 1));
        // in stanga
        addMove(moves, pos, new Position(pos.getRow(), pos.getColumn() - 1));
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
