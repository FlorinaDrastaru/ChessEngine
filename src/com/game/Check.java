package com.game;

import java.util.LinkedList;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.pieces.ChessPiece;
import com.pieces.TeamColour;

public class Check {
    // iau fiecare piesa de pe tabla care are culoarea opusa
    // si verific in vectorul ei de mutari daca vreo pozitie coincide
    // cu aia pe care e piesa mea
    public static boolean attackedPos(Position pos) {

        TeamColour initialColour = ChessGame.getInstance().getColour();
        if (initialColour.equals(TeamColour.Black)) {
            ChessGame.getInstance().setColour(TeamColour.White);
        } else {
            ChessGame.getInstance().setColour(TeamColour.Black);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && ChessBoard.getInstance().getBoard()[i][j].getColour().
                                equals(ChessGame.getInstance().getColour()) &&
                                ChessBoard.getInstance().getBoard()[i][j].idx != 5) {
                    LinkedList<Move> moves = ChessBoard.getInstance().
                    getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    if (moves != null) {
                        for (Move move : moves) {
                            if (move.getDest().getColumn() == pos.getColumn() && move.getDest().getRow() == pos.getRow()) {
                                ChessGame.getInstance().setColour(initialColour);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        ChessGame.getInstance().setColour(initialColour);
        return false;
    }

    public static  LinkedList<Move> canDefendPos(Position pos) {
        LinkedList<Move> defendingMoves =  new LinkedList<Move>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // iau toate piesele de pe tabla cu aceeasi culoare ca a mea
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && ChessBoard.getInstance().getBoard()[i][j].getColour()
                            .equals(ChessGame.getInstance().getColour())) {
                    // iau toate mutarile posibile de la piesa curenta
                    LinkedList<Move> moves = ChessBoard.getInstance().
                            getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    // salvez pozitia piessei curentee
                    Position pos1 = new Position(i, j);
                    // iau pe rand pozitiile in care pot sa ajung
                    if (moves != null) {
                        for (Move move : moves) {
                        // fac mutarea de proba
                            ChessPiece piece = probeMove(pos1, move.getDest());
                        // daca mutarea aia face ca pozitia pe care vreau sa o apar sa nu mai fie atacata e true
                            if (!attackedPos(ChessBoard.getInstance().getKing())) {
                                defendingMoves.add(new Move(pos1, move.getDest()));
                                undoMove(pos1, move.getDest(), piece);
                            } else
                            // daca piesa tot e atacata revin la cum era tabla inainte de mutare
                            undoMove(pos1, move.getDest(), piece);
                            }
                        }
                    }
                }
            }

        return defendingMoves;
    }

    public static ChessPiece probeMove(Position pos1, Position pos2) {
        ChessPiece piece = ChessBoard.getInstance().getChessPiece(pos2);
        ChessBoard.getInstance().putChessPiece
                    (ChessBoard.getInstance().getChessPiece(pos1), pos2);
        ChessBoard.getInstance().takeOutChessPiece(pos1);
        if (ChessBoard.getInstance().getChessPiece(pos2).idx == 5) {
            ChessBoard.getInstance().setKing(pos2);
        }
        return piece;
    }


    public static void undoMove(Position pos1, Position pos2, ChessPiece piece) {
        if (ChessBoard.getInstance().getChessPiece(pos2).idx == 5) {
            ChessBoard.getInstance().setKing(pos1);
        }
        ChessBoard.getInstance().putChessPiece
                (ChessBoard.getInstance().getChessPiece(pos2), pos1);
        ChessBoard.getInstance().takeOutChessPiece(pos2);
        if (piece != null) 
            ChessBoard.getInstance().putChessPiece(piece, pos2);
    }

}