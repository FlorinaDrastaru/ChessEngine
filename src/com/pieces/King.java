package com.pieces;
import com.board.ChessBoard;
import com.board.Position;
import com.game.Check;

import java.util.LinkedList;


public class King extends ChessPiece {
    public King(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<>();
        // in fata
            // drept
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 1,
                                            pos.getColumn()))) {
            if (!Check.attackedPos(new Position(pos.getRow() + 1, pos.getColumn())))
                moves.add(new Position(pos.getRow() + 1, pos.getColumn()));
        }
            //la dreapta
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 1,
                                             pos.getColumn() + 1))) {
            if (!Check.attackedPos(new Position(pos.getRow() + 1, pos.getColumn() + 1)))
                moves.add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
        }
            // in stanga
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 1,
                                            pos.getColumn() - 1))) {
            if (!Check.attackedPos(new Position(pos.getRow() + 1, pos.getColumn() - 1)))
                moves.add(new Position(pos.getRow() + 1, pos.getColumn() - 1));
        }
        // in spate
            // drept
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 1,
                                                   pos.getColumn()))) {
            if (!Check.attackedPos(new Position(pos.getRow() - 1, pos.getColumn())))
                moves.add(new Position(pos.getRow() - 1, pos.getColumn()));
        }
            // stanga
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 1,
                                             pos.getColumn() - 1))) {
            if (!Check.attackedPos(new Position(pos.getRow() - 1, pos.getColumn() - 1)))
                moves.add(new Position(pos.getRow() - 1, pos.getColumn() - 1));
        }
            // dreapta
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 1,
                                            pos.getColumn() + 1))) {
            if (!Check.attackedPos(new Position(pos.getRow() - 1, pos.getColumn() + 1)))
                moves.add(new Position(pos.getRow() - 1, pos.getColumn() + 1));
        }
        // in dreapta
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow(),
                                            pos.getColumn() + 1))) {
            if (!Check.attackedPos(new Position(pos.getRow(), pos.getColumn() + 1)))
                moves.add(new Position(pos.getRow(), pos.getColumn() + 1));
        }
        // in stanga
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow(),
                                             pos.getColumn() - 1))) {
            if (!Check.attackedPos(new Position(pos.getRow(), pos.getColumn() - 1)))
                moves.add(new Position(pos.getRow(), pos.getColumn() - 1));
        }
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
