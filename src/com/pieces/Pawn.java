package com.pieces;

import com.board.ChessBoard;
import com.board.Position;
import com.game.ChessGame;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }


//    @Override
//    // eliminate other chess piece if it has the opposite colour
//    public void eatOpponent() {
//        ChessBoard board = ChessBoard.getInstance();
//        Position pos = new Position(this.getPosition().getRow()
//                + ChessGame.getInstance().getSign(), this.getPosition().getColumn() - 1);
//        if (pos.isValidPosition() && board.verifyPosition(pos)
//                && !board.getChessPiece(pos).getColour().equals(this.getColour())) {
//            this.move(pos);
//        } else {
//            pos = new Position(this.getPosition().getRow()
//                    + ChessGame.getInstance().getSign(), this.getPosition().getColumn() + 1);
//            if (pos.isValidPosition() && board.verifyPosition(pos) &&
//                    !board.getChessPiece(pos).getColour().equals(this.getColour())) {
//                this.move(pos);
//            }
//        }
//    }


    public LinkedList<Position> getMoves(Position pos) {
        LinkedList<Position> moves = new LinkedList<>();
        if (pos.getRow() - 1 >= 0) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) {
                if (ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn()] == null) {
                    System.out.println("lala");
                    moves.add(new Position(pos.getRow() - 1, pos.getColumn()));
                }
                if (pos.getColumn() - 1 >= 0)
                    if (ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn() - 1] != null &&
                            !ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn() - 1].getColour().equals(
                                    ChessGame.getInstance().getColour())) {
                    moves.add(new Position(pos.getRow() - 1, pos.getColumn() - 1));
                }
                if (pos.getColumn() + 1 < 8)
                        if (ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn() + 1] != null &&
                                !ChessBoard.getInstance().getBoard()[pos.getRow() - 1][pos.getColumn() + 1].getColour().equals(
                                        ChessGame.getInstance().getColour())) {
                    moves.add(new Position(pos.getRow() - 1, pos.getColumn() + 1));
                }
            }
        }
        if (pos.getRow() + 1 < 8) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.White)) {
                if (ChessBoard.getInstance().getBoard()[pos.getRow() + 1][pos.getColumn()] == null) {
                    moves.add(new Position(pos.getRow() + 1, pos.getColumn()));
                }
                if (pos.getColumn() - 1 >= 0)
                    if (!ChessBoard.getInstance().getBoard()[pos.getRow() + 1][pos.getColumn() - 1].getColour().equals(
                        ChessGame.getInstance().getColour())) {
                    moves.add(new Position(pos.getRow() + 1, pos.getColumn() - 1));
                }

                if (pos.getColumn() + 1 < 8)
                    if (!ChessBoard.getInstance().getBoard()[pos.getRow() + 1][pos.getColumn() + 1].getColour().equals(
                        ChessGame.getInstance().getColour())) {

                    moves.add(new Position(pos.getRow() + 1, pos.getColumn() + 1));
                }
            }
        }

        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }

}

