package com.pieces;

import com.board.ChessBoard;
import com.board.Position;
import com.game.ChessGame;

import java.util.LinkedList;
import java.util.List;

public class Rook extends ChessPiece {
    public Rook(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    @Override
    public void move(Position position) {

    }

    // am facut la toate aceeasi chestie, ma duc pana la capetele tablei,
    // adaug pozitia in lista, si cand dau de o pozitie ocupata,
    // aia o sa fie ultima pozitie adaugata in lista pt ca nu poate sa mearga mai departa
    public LinkedList getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<Position>();
        int i = 0;

        // se duce in fata
        while(pos.getRow() + i < 8) {
            Position newPos = new Position(pos.getRow() + i, pos.getColumn());
            moves.add(newPos);
            if (ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // se duce in spate
        i = 0;
        while(pos.getRow() - i >= 0) {
            Position newPos = new Position(pos.getRow() - i, pos.getColumn());
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // se duce in stanga
        i = 0;
        while(pos.getColumn() - i >= 0) {
            Position newPos = new Position(pos.getRow(), pos.getColumn() - i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // se duce in dreapta
        i = 0;
        while(pos.getColumn() + i < 8) {
            Position newPos = new Position(pos.getRow(), pos.getColumn() + i);
            moves.add(newPos);
            if(ChessBoard.getInstance().verifyPosition(newPos))
                break;
            i++;
        }

        // verific daca am vreo mutare valida
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }

    }
}
