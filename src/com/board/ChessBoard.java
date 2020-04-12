package com.board;

import com.constants.Constants;
import com.game.ChessGame;
import com.pieces.*;

import java.util.LinkedList;

public class ChessBoard {
    private ChessPiece[][] board;
    private static ChessBoard instance = null;
    private ChessBoard() {
        board = new ChessPiece[8][8];
    }
    // imi salvez pozitiile regilor pe tabla
    private Position whiteKing;
    private Position blackKing;
    public Position getWhiteKing() {
        return whiteKing;
    }
    public Position getBlackKing() {
        return blackKing;
    }

    public void setWhiteKing(Position pos) {
        whiteKing = pos;
    }

    public void setBlackKing(Position pos) {
        blackKing = pos;
    }

    public static ChessBoard getInstance() {
        if (instance == null) {
            instance = new ChessBoard();
        }
        return instance;
    }

    // verify if the pos is occupied

    public boolean verifyPosition(Position pos) {
        if (pos.getRow() >= 0 && pos.getRow() < 8 && pos.getColumn() >= 0 && pos.getColumn() < 8) {
            if (board[pos.getRow()][pos.getColumn()] == null
             || !board[pos.getRow()][pos.getColumn()].getColour().equals(ChessGame.getInstance().getColour())) {
                return false;
            }
        }
        return true;
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public ChessPiece getChessPiece(Position pos) {
        return board[pos.getRow()][pos.getColumn()];
    }

    public void takeOutChessPiece(Position pos) {
        board[pos.getRow()][pos.getColumn()] = null;
    }

    public void putChessPiece(ChessPiece piece, Position pos) {
        if (pos.isValidPosition()) {
            board[pos.getRow()][pos.getColumn()] = piece;
            piece.setPosition(pos);
        }
    }

    public void initiateBoard() {
        putChessPiece(new Rook(TeamColour.White, false,1), new Position(0, 0));
        putChessPiece(new Knight(TeamColour.White, false,2), new Position(0, 1));
        putChessPiece(new Bishop(TeamColour.White, false, 3), new Position(0, 2));
        putChessPiece(new Queen(TeamColour.White, false, 4), new Position(0, 3));
        putChessPiece(new King(TeamColour.White, false, 5), new Position(0, 4));
        putChessPiece(new Bishop(TeamColour.White, false, 3), new Position(0, 5));
        putChessPiece(new Knight(TeamColour.White, false, 2), new Position(0, 6));
        putChessPiece(new Rook(TeamColour.White, false, 1), new Position(0, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.White, false, 0), new Position(1, i));
        }

        putChessPiece(new Rook(TeamColour.Black, false,1), new Position(7, 0));
        putChessPiece(new Knight(TeamColour.Black, false, 2), new Position(7, 1));
        putChessPiece(new Bishop(TeamColour.Black, false, 3), new Position(7, 2));
        putChessPiece(new Queen(TeamColour.Black, false, 4), new Position(7, 3));
        putChessPiece(new King(TeamColour.Black, false, 5), new Position(7, 4));
        putChessPiece(new Bishop(TeamColour.Black, false,3), new Position(7, 5));
        putChessPiece(new Knight(TeamColour.Black, false, 2), new Position(7, 6));
        putChessPiece(new Rook(TeamColour.Black, false, 1), new Position(7, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.Black, false, 0), new Position(6, i));
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        // setez pozitiile initiale ale regilor
        setBlackKing(new Position(7, 4));
        setWhiteKing(new Position(0, 4));

    }


    // iau fiecare piesa de pe tabla care are culoarea opusa
    // si verific in vectorul ei de mutari daca vreo pozitie coincide
    // cu aia pe care e piesa mea
    public boolean attackedPos(Position pos) {

        TeamColour initialColour = ChessGame.getInstance().getColour();
        if (initialColour.equals(TeamColour.Black)) {
            ChessGame.getInstance().setColour(TeamColour.White);
        } else {
            ChessGame.getInstance().setColour(TeamColour.Black);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && instance.board[i][j].getColour().equals(ChessGame.getInstance().getColour())) {
                    LinkedList<Position> moves = instance.getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    //System.out.println(moves.get(0).getRow() + " " + moves.get(0).getColumn());

                    System.out.println(instance.getChessPiece(new Position(i, j)).getClass().getSimpleName() +
                           moves.get(0).getRow() + moves.get(0).getColumn());

                    if (moves != null) {
                        for (Position move : moves) {
                            if (move.getColumn() == pos.getColumn() && move.getRow() == pos.getRow()) {
                                System.out.println("adev");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        ChessGame.getInstance().setColour(initialColour);
        System.out.println(ChessGame.getInstance().getColour());
        return false;
    }




    public boolean canDefendPos(Position pos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // iau toate piesele de pe tabla cu aceeasi culoare ca a mea
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && instance.getBoard()[i][j].getColour().equals(ChessGame.getInstance().getColour())) {
                    // iau toate mutarile posibile de la piesa curenta
                    LinkedList<Position> moves = instance.getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    // salvez pozitia piessei curentee
                    Position pos1 = new Position(i, j);
                    // iau pe rand pozitiile in care pot sa ajung
                    for (Position pos2 : moves) {
                        // fac mutarea de proba
                        probeMove(pos1, pos2);
                        // daca mutarea aia face ca pozitia pe care vreau sa o apar sa nu mai fie atacata e true
                        if (attackedPos(pos) == false)
                            return true;

                            // daca piesa tot e atacata revin la cum era tabla inainte de mutare
                        undoMove(pos1, pos2);
                        }
                    }
                }
            }

        return false;
    }

    public void probeMove(Position pos1, Position pos2) {
        putChessPiece(instance.getChessPiece(pos1), pos2);
        takeOutChessPiece(pos1);
    }


    public void undoMove(Position pos1, Position pos2) {
        putChessPiece(instance.getChessPiece(pos2), pos1);
        takeOutChessPiece(pos2);
    }




}
