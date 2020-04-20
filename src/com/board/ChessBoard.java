package com.board;

import com.constants.Constants;
import com.game.ChessGame;
import com.game.Rating;
import com.pieces.*;

public class ChessBoard {
    private ChessPiece[][] board;
    private static ChessBoard instance = null;
    private Position whiteKing;
    private Position blackKing;
    private ChessBoard() {
        board = new ChessPiece[8][8];
    }
    // checks if a position is on the board and if its free or
    // has an opponent's piece on it
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
        putChessPiece(new Rook(TeamColour.White, 500, Rating.wRookBoard, true), new Position(0, 0));
        putChessPiece(new Knight(TeamColour.White, 300, Rating.wKnightBoard, true), new Position(0, 1));
        putChessPiece(new Bishop(TeamColour.White, 300, Rating.wBishopBoard, true), new Position(0, 2));
        putChessPiece(new Queen(TeamColour.White, 900, Rating.wQueenBoard, true), new Position(0, 3));
        putChessPiece(new King(TeamColour.White, 9000, Rating.wKingMidBoard, true), new Position(0, 4));
        putChessPiece(new Bishop(TeamColour.White, 300, Rating.wBishopBoard, true), new Position(0, 5));
        putChessPiece(new Knight(TeamColour.White, 300, Rating.wKnightBoard, true), new Position(0, 6));
        putChessPiece(new Rook(TeamColour.White, 500, Rating.wRookBoard, true), new Position(0, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.White, 100, Rating.wPawnBoard, true), new Position(1, i));
        }

        putChessPiece(new Rook(TeamColour.Black, 500, Rating.bRookBoard, true), new Position(7, 0));
        putChessPiece(new Knight(TeamColour.Black, 300, Rating.bKnightBoard, true), new Position(7, 1));
        putChessPiece(new Bishop(TeamColour.Black, 300, Rating.bBishopBoard, true), new Position(7, 2));
        putChessPiece(new Queen(TeamColour.Black, 900, Rating.bQueenBoard, true), new Position(7, 3));
        putChessPiece(new King(TeamColour.Black, 9000, Rating.bKingMidBoard, true), new Position(7, 4));
        putChessPiece(new Bishop(TeamColour.Black, 300, Rating.bBishopBoard, true), new Position(7, 5));
        putChessPiece(new Knight(TeamColour.Black, 300, Rating.bKnightBoard, true), new Position(7, 6));
        putChessPiece(new Rook(TeamColour.Black, 500, Rating.bRookBoard, true), new Position(7, 7));

        for (int i = 0; i < Constants.SUP_BOARD_LIMIT; i++) {
            putChessPiece(new Pawn(TeamColour.Black, 100, Rating.bPawnBoard, true), new Position(6, i));
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        // initial kings' positions are set
        setBlackKing(new Position(7, 4));
        setWhiteKing(new Position(0, 4));

    }

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

    public void setKing(Position pos, TeamColour team) {
        if (team.equals(TeamColour.Black)) 
            setBlackKing(pos);
        else 
            setWhiteKing(pos);
    }

    public Position getKing(TeamColour team) {
        if (team.equals(TeamColour.Black)) 
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
