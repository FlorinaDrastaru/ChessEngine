package com.pieces;
import com.board.ChessBoard;
import com.board.Position;
import java.util.LinkedList;


public class Knight extends ChessPiece {
    public Knight(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<Position>();


        // aici ma duc in 2 locuri posibile, in stanga pe inaltime cum ar veni
        // ma duc una in stanga
            // ma duc 2 unitati in sus
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 2,
                                                    pos.getColumn() - 1)))
            moves.add(new Position(pos.getRow() + 2, pos.getColumn() - 1));

            // ma duc 2 unitati in jos
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 2,
                                                    pos.getColumn() - 1)))
            moves.add(new Position(pos.getRow() - 2, pos.getColumn() - 1));

        // ma duc in dreapta pe inaltime
            // dreapta sus pe verticala
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 2,
                                                    pos.getColumn() + 1)))
            moves.add(new Position(pos.getRow() + 2, pos.getColumn() + 1));

            // dreapta jos
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 2,
                                                    pos.getColumn() + 1)))
            moves.add(new Position(pos.getRow() - 2, pos.getColumn() + 1));

        // aici o sa verific miscarile pe latime, adica cand se deplaseaza pe orizontala cu 2 unitati

        //ma mut in stanga cu 2
            // sus
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 1,
                                             pos.getColumn() - 2)))
            moves.add(new Position(pos.getRow() + 1, pos.getColumn() - 2));

            // jos
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 1,
                                             pos.getColumn() - 2)))
            moves.add(new Position(pos.getRow() - 1, pos.getColumn() - 2));

        // ma mut in partea dreapta
            //sus
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() + 1,
                                            pos.getColumn() + 2)))
            moves.add(new Position(pos.getRow() + 1, pos.getColumn() + 2));

            //jos
        if (!ChessBoard.getInstance().verifyPosition(new Position(pos.getRow() - 1,
                                             pos.getColumn() + 2)))
                moves.add(new Position(pos.getRow() - 1, pos.getColumn() + 2));

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
