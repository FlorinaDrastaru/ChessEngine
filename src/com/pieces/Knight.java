package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import java.util.LinkedList;


public class Knight extends ChessPiece {
    public Knight(TeamColour colour, int worth, int rating[][], boolean initialPos) {
        super(colour, worth, rating, initialPos);
        idx = 1;
    }

    public void addMove(LinkedList<Move> moves, Position pos, Position dest) {
        if (!ChessBoard.getInstance().verifyPosition(dest)) {
            moves.add(new Move(pos, dest));
        }
    }

    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();

        addMove(moves, pos, new Position(pos.getRow() + 2, pos.getColumn() - 1));
        addMove(moves, pos, new Position(pos.getRow() - 2, pos.getColumn() - 1));
        addMove(moves, pos, new Position(pos.getRow() + 2, pos.getColumn() + 1));
        addMove(moves, pos, new Position(pos.getRow() - 2, pos.getColumn() + 1));
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() - 2));
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() - 2));
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() + 2));
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() + 2));

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
