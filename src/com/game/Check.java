package com.game;

import java.util.LinkedList;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.pieces.ChessPiece;
import com.pieces.TeamColour;

public class Check {
    // attackedPos method checks if a position on the board,
    // is under attack from any of the opponents' chesspieces
    public static boolean attackedPos(Position pos) {
        TeamColour initialColour = ChessGame.getInstance().getColour();
        ChessGame.getInstance().switchTeam();
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
                            // if a move lands the opponent on the position that is being checked
                            // that means the position is under possible attack
                            if (move.getDest().getColumn() == pos.getColumn() 
                                && move.getDest().getRow() == pos.getRow()) {
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

    // canDefendPos method checks if any of the possible moves can protect the
    // position that is under attack and returns a list of the ones that can
    public static  LinkedList<Move> canDefendPos(Position pos) {
        LinkedList<Move> defendingMoves =  new LinkedList<Move>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.getInstance().getBoard()[i][j] != null
                        && ChessBoard.getInstance().getBoard()[i][j].getColour()
                            .equals(ChessGame.getInstance().getColour())) {
                    LinkedList<Move> moves = ChessBoard.getInstance().
                            getChessPiece(new Position(i, j)).getMoves(new Position(i, j));
                    Position pos1 = new Position(i, j);
                    if (moves != null) {
                        for (Move move : moves) {
                        // every possible moved is executed 
                            ChessPiece piece = probeMove(pos1, move.getDest());
                        // and it checks if the King is still under attack
                            if (!attackedPos(ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()))) {
                                defendingMoves.add(new Move(pos1, move.getDest()));   
                            }
                            // after the evaluation the move is undone
                            undoMove(pos1, move.getDest(), piece);
                            }
                        }
                    }
                }
            }
        // the list of safe moves is being returned
        return defendingMoves;
    }
    // the method that simulates the execution of a move
    public static ChessPiece probeMove(Position pos1, Position pos2) {
        ChessPiece piece = ChessBoard.getInstance().getChessPiece(pos2);
        ChessBoard.getInstance().getChessPiece(pos1).move(pos2);
        return piece;  // the eliminated piece (if any) is returned to be stored
    }
    // the method that undoes a probed move
    public static void undoMove(Position pos1, Position pos2, ChessPiece piece) {
        ChessBoard.getInstance().getChessPiece(pos2).move(pos1);
        if (piece != null) 
            ChessBoard.getInstance().putChessPiece(piece, pos2);
    }

}