package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.game.Check;

import java.util.LinkedList;


public class King extends ChessPiece {
    public King(TeamColour colour, int worth, int rating[][], boolean initialPos) {
        super(colour, worth, rating, initialPos);
        idx = 5;
    }
    // checks if the move is posible and if it would not get the king in chess
    // and it is added to the list
    public void addMove(LinkedList<Move> moves, Position src, Position dest) {
        if (!ChessBoard.getInstance().verifyPosition(dest)) {
            if (!checkKingChess(dest)){      
                ChessPiece piece =  Check.probeMove(src, dest);                
                if (!Check.attackedPos(dest))
                    moves.add(new Move(src, dest));
                Check.undoMove(src, dest, piece);
            }
        }
    }
    // checks if the opponent's king doesn't get the player's king
    // in chess if the move is made
    public boolean checkKingChess(Position dest) {
        int ln = dest.getRow();
        int col = dest.getColumn();
        for (int i = ln - 1; i <= ln + 1; ++i) {
            for (int j = col - 1; j <= col + 1; ++j) {
                if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                    if (ChessBoard.getInstance().getChessPiece(new Position(i,j)) != null){
                        ChessPiece piece = ChessBoard.getInstance().getChessPiece(new Position(i,j));
                        if (!piece.getColour().equals(this.getColour()) 
                            && piece.getIdx() == 5)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public LinkedList<Move> getMoves(Position pos) {
        LinkedList<Move> moves = new LinkedList<>();
        //  checks if every possible move is correct
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn()));
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() + 1));
        addMove(moves, pos, new Position(pos.getRow() + 1, pos.getColumn() - 1));
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn()));
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() - 1));
        addMove(moves, pos, new Position(pos.getRow() - 1, pos.getColumn() + 1));
        addMove(moves, pos, new Position(pos.getRow(), pos.getColumn() + 1));
        addMove(moves, pos, new Position(pos.getRow(), pos.getColumn() - 1));
        if (moves.size() == 0) {
            return null;
        } else {
            return moves;
        }
    }
}
