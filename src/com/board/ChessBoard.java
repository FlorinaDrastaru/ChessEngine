package com.board;

import com.constants.Constants;
import com.game.ChessGame;
import com.game.Rating;
import com.pieces.*;

public class ChessBoard {
    private ChessPiece[][] board;
    private static ChessBoard instance = null;
    private ChessBoard() {
        board = new ChessPiece[8][8];
    }
    public boolean verifyPosition(Position pos) {
        if (pos.isValidPosition()) {
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
        putChessPiece(new Rook(TeamColour.White, false, 500, Rating.wRookBoard), new Position(0, 0));
        putChessPiece(new Knight(TeamColour.White, false, 300, Rating.wKnightBoard), new Position(0, 1));
        putChessPiece(new Bishop(TeamColour.White, false, 300, Rating.wBishopBoard), new Position(0, 2));
        putChessPiece(new Queen(TeamColour.White, false, 900, Rating.wQueenBoard), new Position(0, 3));
        putChessPiece(new King(TeamColour.White, false, 9000, Rating.wKingMidBoard), new Position(0, 4));
        putChessPiece(new Bishop(TeamColour.White, false, 300, Rating.wBishopBoard), new Position(0, 5));
        putChessPiece(new Knight(TeamColour.White, false, 300, Rating.wKnightBoard), new Position(0, 6));
        putChessPiece(new Rook(TeamColour.White, false, 500, Rating.wRookBoard), new Position(0, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.White, false, 100, Rating.wPawnBoard), new Position(1, i));
        }

        putChessPiece(new Rook(TeamColour.Black, false, 500, Rating.bRookBoard), new Position(7, 0));
        putChessPiece(new Knight(TeamColour.Black, false, 300, Rating.bKnightBoard), new Position(7, 1));
        putChessPiece(new Bishop(TeamColour.Black, false, 300, Rating.bBishopBoard), new Position(7, 2));
        putChessPiece(new Queen(TeamColour.Black, false, 900, Rating.bQueenBoard), new Position(7, 3));
        putChessPiece(new King(TeamColour.Black, false, 9000, Rating.bKingMidBoard), new Position(7, 4));
        putChessPiece(new Bishop(TeamColour.Black, false, 300, Rating.bBishopBoard), new Position(7, 5));
        putChessPiece(new Knight(TeamColour.Black, false, 300, Rating.bKnightBoard), new Position(7, 6));
        putChessPiece(new Rook(TeamColour.Black, false, 500, Rating.bRookBoard), new Position(7, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.Black, false, 100, Rating.bPawnBoard), new Position(6, i));
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

    public void setKing(Position pos) {
        if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) 
            setBlackKing(pos);
        else 
            setWhiteKing(pos);
    }

    public Position getKing() {
        if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) 
            return getBlackKing();
        else 
            return getWhiteKing();
    }
    public static ChessBoard getInstance() {
        if (instance == null) {
            instance = new ChessBoard();
        }
        return instance;
    }
}
