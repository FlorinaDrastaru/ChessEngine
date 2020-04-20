package com.pieces;

import com.board.Position;
import com.board.ChessBoard;
import com.board.Move;

import java.util.LinkedList;


public class Bishop extends ChessPiece {
    public Bishop(TeamColour colour, int worth, int rating[][], boolean initialPos) {
        super(colour, worth, rating, initialPos);
        idx = 1;
    }
    // checks all possible moves until it reaches another piece
    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<Move>();
        int i = 1;
        while(pos.getRow() + i < 8 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() + i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(new Move(pos, newPos));
                if (ChessBoard.getInstance().getBoard()[pos.getRow()+i][pos.getColumn()+i] != null)
                    break;
                else
                    i++;
            }
        }
        i = 1;
        while (pos.getRow() + i < 8 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() - i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(new Move(pos, newPos));
                if (ChessBoard.getInstance().getBoard()[pos.getRow()+i][pos.getColumn()-i] != null)
                    break;
                else
                    i++;
            }
        }
        i = 1;
        while(pos.getRow() - i >= 0 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() - i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(new Move(pos, newPos));
                if (ChessBoard.getInstance().getBoard()[pos.getRow()-i][pos.getColumn()-i] != null)
                    break;
                else
                    i++;
            }
        }
        i = 1;
        while (pos.getRow() - i >= 0 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() + i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(new Move(pos, newPos));
                if (ChessBoard.getInstance().getBoard()[pos.getRow()-i][pos.getColumn()+i] != null)
                    break;
                else
                    i++;
            }
        }

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
