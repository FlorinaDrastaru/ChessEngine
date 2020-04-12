package com.game;

import java.util.LinkedList;

import com.board.ChessBoard;
import com.board.Position;
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
                    LinkedList<Position> moves = ChessBoard.getInstance().
                            getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    if (moves != null) {
                        for (Position move : moves) {
                            if (move.getColumn() == pos.getColumn() && move.getRow() == pos.getRow()) {
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

    public static boolean canDefendPos(Position pos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // iau toate piesele de pe tabla cu aceeasi culoare ca a mea
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && ChessBoard.getInstance().getBoard()[i][j].getColour()
                            .equals(ChessGame.getInstance().getColour())) {
                    // iau toate mutarile posibile de la piesa curenta
                    LinkedList<Position> moves = ChessBoard.getInstance().
                            getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    // salvez pozitia piessei curentee
                    Position pos1 = new Position(i, j);
                    // iau pe rand pozitiile in care pot sa ajung
                    if (moves != null) {
                        for (Position pos2 : moves) {
                        // fac mutarea de proba
                            probeMove(pos1, pos2);
                        // daca mutarea aia face ca pozitia pe care vreau sa o apar sa nu mai fie atacata e true
                            if (attackedPos(ChessBoard.getInstance().getKing()) == false) {
                                int ln_dest = pos2.getRow() + 1;
                                int ln_src = pos1.getRow() + 1;
                                System.out.print("move " 
                                    + ChessGame.getInstance().getPos().get(pos1.getColumn())
                                    + ln_src
                                    + ChessGame.getInstance().getPos().get(pos2.getColumn())
                                    + ln_dest + "\n");
                                return true;
                            }
                            // daca piesa tot e atacata revin la cum era tabla inainte de mutare
                            undoMove(pos1, pos2);
                            }
                        }
                    }
                }
            }

        return false;
    }

    public static void probeMove(Position pos1, Position pos2) {
        ChessBoard.getInstance().putChessPiece
                    (ChessBoard.getInstance().getChessPiece(pos1), pos2);
        ChessBoard.getInstance().takeOutChessPiece(pos1);
        if (ChessBoard.getInstance().getChessPiece(pos2).idx == 5) {
            ChessBoard.getInstance().setKing(pos2);
        }
    }


    public static void undoMove(Position pos1, Position pos2) {
        ChessBoard.getInstance().putChessPiece
                    (ChessBoard.getInstance().getChessPiece(pos2), pos1);
        ChessBoard.getInstance().takeOutChessPiece(pos2);
    }

}