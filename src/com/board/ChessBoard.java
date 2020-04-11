package com.board;

import com.constants.Constants;
import com.game.ChessGame;
import com.pieces.*;

public class ChessBoard {
    private ChessPiece[][] board;
    private static ChessBoard instance = null;
    private ChessBoard() {
        board = new ChessPiece[8][8];
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

    }

}
