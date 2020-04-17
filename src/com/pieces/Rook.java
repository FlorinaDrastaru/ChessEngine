package com.pieces;

import com.board.Position;
import com.board.ChessBoard;
import com.board.Move;

import java.util.LinkedList;


public class Rook extends ChessPiece {
    public Rook(TeamColour colour, boolean eliminated, int worth, int rating[][]) {
        super(colour, eliminated, worth, rating);
        idx = 1;
    }

    // am facut la toate aceeasi chestie, ma duc pana la capetele tablei,
    // adaug pozitia in lista, si cand dau de o pozitie ocupata,
    // aia o sa fie ultima pozitie adaugata in lista pt ca nu poate sa mearga mai departa
    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();
        int i = 1;
        // se duce in fata
        while(pos.getRow() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn());
            if (ChessBoard.getInstance().verifyPosition(newPos)) {
                break;
            } else {
                moves.add(new Move(pos, newPos));
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
                moves.add(new Move(pos, newPos));
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
                moves.add(new Move(pos, newPos));
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
                moves.add(new Move(pos, newPos));
                if (ChessBoard.getInstance().getBoard()[pos.getRow()][pos.getColumn()+i] != null)
                    break;
                else
                    i++;
            }
        }

        // verific daca am vreo mutare valida
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }

    }

}
