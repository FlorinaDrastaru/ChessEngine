package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.game.ChessGame;
import java.util.LinkedList;

public class Pawn extends ChessPiece {
    public Pawn(TeamColour colour, int worth, int rating[][], boolean initialPos) {
        super(colour, worth, rating, initialPos);
        idx = 0;
    }
    // checks if a move is possible
    public void addMove(LinkedList<Move> moves, Position src, Position dest) {
        if (ChessBoard.getInstance().getBoard()[dest.getRow()][dest.getColumn()] != null)
            if (!ChessBoard.getInstance().getBoard()[dest.getRow()][dest.getColumn()].getColour()
                                .equals(ChessGame.getInstance().getColour())) {
                moves.add(new Move(src, dest));
            }
    }

    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();
        // sign determines the direction of moving according to the team colour
        int sign = 0;
        if (this.getColour().equals(TeamColour.White))
            sign = 1;
        else 
            sign = -1; 
        int rw = pos.getRow() + sign * 2;
        int newRow = pos.getRow() + sign;
        if ((rw == 3 || rw == 4) && getInitialPos() && ChessBoard.getInstance()
            .getBoard()[newRow][pos.getColumn()] == null
            && ChessBoard.getInstance().getBoard()[rw][pos.getColumn()] == null) {
            moves.add(new Move(pos, new Position(rw, pos.getColumn())));
        }
        if ((newRow >= 0 && sign == -1) || (newRow < 8 && sign == 1)) {
            if (ChessBoard.getInstance().getBoard()[newRow][pos.getColumn()] == null) {
                moves.add(new Move (pos, new Position(newRow, pos.getColumn())));
            }
            if (pos.getColumn() - 1 >= 0)
                addMove(moves, pos, new Position(newRow, pos.getColumn() - 1));
            if (pos.getColumn() + 1 < 8)
                addMove(moves, pos, new Position(newRow, pos.getColumn() + 1));
        }
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }

}

