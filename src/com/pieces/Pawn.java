package com.pieces;

import com.board.ChessBoard;
import com.board.Position;
import com.game.ChessGame;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, boolean eliminated, int idx) {
        super(colour, eliminated, idx);
    }

    @Override
    // eliminate the pawn from the initial position and
    // add it to the new position
    public void move(Position position) {
        ChessBoard board = ChessBoard.getInstance();
        board.takeOutChessPiece(this.getPosition());
        board.putChessPiece(this, new Position(position.getRow(), position.getColumn()));
        this.setPosition(new Position(position.getRow(), position.getColumn()));
        }

    @Override
    // eliminate other chess piece if it has pe opposite colour
    public void eatOpponent() {
        ChessBoard board = ChessBoard.getInstance();
        Position pos = new Position(this.getPosition().getRow()+ChessGame.getInstance().getSign(), this.getPosition().getColumn()-1);
        if (pos.isValidPosition() && board.verifyPosition(pos)
                && !board.getChessPiece(pos).getColour().equals(this.getColour())) {
            this.move(pos);
        } else {
            pos = new Position(this.getPosition().getRow() + ChessGame.getInstance().getSign(), this.getPosition().getColumn()+1);
            if (pos.isValidPosition() && board.verifyPosition(pos) &&
                    !board.getChessPiece(pos).getColour().equals(this.getColour())) {
                this.move(pos);
            }
        }
    }
}

