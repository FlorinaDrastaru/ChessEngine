package com.pieces;

import com.board.Position;
import com.board.ChessBoard;
import com.board.Position;
import com.game.ChessGame;

import java.util.LinkedList;
import java.util.List;

public class Rook extends ChessPiece {
    public Rook(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }


    // am facut la toate aceeasi chestie, ma duc pana la capetele tablei,
    // adaug pozitia in lista, si cand dau de o pozitie ocupata,
    // aia o sa fie ultima pozitie adaugata in lista pt ca nu poate sa mearga mai departa
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

        // verific daca am vreo mutare valida
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }

    }

}
