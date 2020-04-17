package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.game.ChessGame;
import java.util.LinkedList;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, boolean eliminated, int worth, int rating[][]) {
        super(colour, eliminated, worth, rating);
        idx = 0;
    }

    public void addMove(LinkedList<Move> moves, Position src, Position dest) {
        if (ChessBoard.getInstance().getBoard()[dest.getRow()][dest.getColumn()] != null)
            if (!ChessBoard.getInstance().getBoard()[dest.getRow()][dest.getColumn()].getColour()
                                .equals(ChessGame.getInstance().getColour())) {
                moves.add(new Move(src, dest));
            }
    }

    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();
        if (pos.getRow() - 1 >= 0) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) {
                if (ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn()] == null) {
                    moves.add(new Move (pos, new Position(pos.getRow() - 1, pos.getColumn())));
                }
                if (pos.getColumn() - 1 >= 0)
                    addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() - 1));
                
                if (pos.getColumn() + 1 < 8)
                    addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() + 1));
            }
        }
        if (pos.getRow() + 1 < 8) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.White)) {
                if (ChessBoard.getInstance().getBoard()[pos.getRow() + 1][pos.getColumn()] == null) {
                        moves.add(new Move (pos, new Position(pos.getRow() + 1, pos.getColumn())));
                }
                if (pos.getColumn() - 1 >= 0) {
                    addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() - 1));
                }
                if (pos.getColumn() + 1 < 8) {
                    addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() + 1));
                }
            }
        }

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }

}

