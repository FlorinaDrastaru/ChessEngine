package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.game.Check;
import com.game.ChessGame;

import java.util.LinkedList;


public class King extends ChessPiece {
    public King(TeamColour colour, boolean eliminated, int worth, int rating[][], boolean initialPos) {
        super(colour, eliminated, worth, rating, initialPos);
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
        ChessBoard board = ChessBoard.getInstance();
        /*boolean freeSquareToLeft = true;
        boolean freeSquareToRight = true;

        if (getInitialPos()) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) {
                for (int j = getPosition().getColumn(); j >= 1; j--) {
                    if (board.getBoard()[7][j] != null) {
                        freeSquareToLeft = false;
                    }
                }
                if (freeSquareToLeft == true && board.getBoard()[7][0].getInitialPos() == true) {
                    Position posKing  = new Position(pos.getRow(), pos.getColumn() - 2);
                    Position posRook = new Position(pos.getRow(), pos.getColumn() + 3);
                }

                for(int j = getPosition().getColumn(); j < 8; j++) {
                    if (board.getBoard()[7][j] != null) {
                        freeSquareToRight = false;
                    }
                }

                if (freeSquareToRight == true && board.getBoard()[7][7].getInitialPos() == true) {
                    Position posKing  = new Position(pos.getRow(), pos.getColumn() + 2);
                    Position posRook = new Position(pos.getRow(), pos.getColumn() - 3);
                }

            }

        }*/
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
