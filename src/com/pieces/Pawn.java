package com.pieces;

import com.board.ChessBoard;
import com.board.Position;
import com.game.ChessGame;

import java.util.HashMap;
import java.util.Map;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, boolean eliminated) {
        super(colour, eliminated);
        idx = 0;
    }

    @Override
    public void move(Position position) {
        ChessBoard board = ChessBoard.getInstance();
        board.takeOutChessPiece(this.getPosition());
        board.putChessPiece(this, new Position(position.getRow(), position.getColumn()));
        this.setPosition(new Position(position.getRow(), position.getColumn()));
        }

    @Override
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

