package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import java.util.LinkedList;


public abstract class ChessPiece {
    private Position position;
    private boolean eliminated = false;
    private TeamColour colour;
    private int worth;
    public int idx;
    private int ratingsBoard[][];
    private boolean initialPos;


    public ChessPiece(TeamColour colour, boolean eliminated, int worth,
                      int rating[][], boolean initialPos) {
        this.eliminated = eliminated;
        this.colour = colour;
        this.worth = worth;
        ratingsBoard = rating;
        this.initialPos = initialPos;              
    }

    public boolean getInitialPos() {
        return initialPos;
    }

    public void changeInitialPos() {
        initialPos = false;
    }
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getWorth() {
        return worth + ratingsBoard[position.getRow()][position.getColumn()];
    }

    public int getIdx() {
        return idx;
    }

    public boolean isEliminated() {
        if (eliminated == true) {
            return true;
        } else {
            return false;
        }
    }

    public void eliminatePiece(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public final void setTeamColor(final TeamColour color) {
        this.colour = color;
    }

    public TeamColour getColour() {
        return colour;
    }

    public void setRatingsBoard(int ratingsBoard[][]) {
        this.ratingsBoard = ratingsBoard;
    }

    public int[][] getRatingsBoard() {
        return ratingsBoard;
    }

    public void setColour(TeamColour colour) {
        this.colour = colour;
    }

    public void move(Position position) {
        ChessBoard board = ChessBoard.getInstance();
        board.takeOutChessPiece(this.getPosition());
        board.putChessPiece(this, new Position(position.getRow(), position.getColumn()));
        this.setPosition(new Position(position.getRow(), position.getColumn()));
    }

    public abstract LinkedList<Move> getMoves(Position pos);

}