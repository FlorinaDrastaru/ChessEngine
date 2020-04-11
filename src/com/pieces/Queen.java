package com.pieces;

import com.board.ChessBoard;
import com.board.Position;
import java.util.LinkedList;

public class Queen extends ChessPiece {
    public Queen(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }


    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<Position>();

        int i = 1;
        // se duce in fata
        while(pos.getRow() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn());
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()+i][pos.getColumn()] != null)
                    break;
                else
                    i++;
            }
        }

        // se duce in spate
        i = 1;
        while(pos.getRow() - i >= 0) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn());
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()-i][pos.getColumn()] != null)
                    break;
                else
                    i++;
            }
        }

        // se duce in stanga
        i = 1;
        while(pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow(), pos.getColumn() - i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()][pos.getColumn()-i] != null)
                    break;
                else
                    i++;
            }
        }

        // se duce in dreapta
        i = 1;
        while(pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow(), pos.getColumn() + i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()][pos.getColumn()+i] != null)
                    break;
                else
                    i++;
            }
        }

        i = 1;
        // ma duc in dreapta sus
        while(pos.getRow() + i < 8 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() + i);
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()+i][pos.getColumn()+i] != null)
                    break;
                else
                    i++;
            }
        }

        // ma duc stanga sus
        i = 1;
        while (pos.getRow() + i < 8 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn() - i);
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()+i][pos.getColumn()-i] != null)
                    break;
                else
                    i++;
            }
        }
        // ma duc in jos, spre stanga
        i = 1;
        while(pos.getRow() - i >= 0 && pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() - i);
            if(ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
                if (ChessBoard.getInstance().getBoard()[pos.getRow()-i][pos.getColumn()-i] != null)
                    break;
                else
                    i++;
            }
        }

        // ma duc dreapta jos
        i = 1;
        while (pos.getRow() - i >= 0 && pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn() + i);
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(newPos);
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
