package com.pieces;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import java.util.LinkedList;


public abstract class ChessPiece {
    private Position position;  // placement
    private TeamColour colour;  // team
    private int worth;   // material score
    public int idx;   // chesspiece type
    private int ratingsBoard[][];  // position score
    private boolean initialPos;   // if it has been moved
    private int countMoves;  // how many moves


    public ChessPiece(TeamColour colour, int worth,
                      int rating[][], boolean initialPos) {
        this.colour = colour;
        this.worth = worth;
        ratingsBoard = rating;
        this.initialPos = initialPos;
        countMoves = 0;           
    }
    // moves the chesspiece on the inner board
    public void move(Position position) {
        ChessBoard board = ChessBoard.getInstance();
        board.takeOutChessPiece(this.getPosition());
        board.putChessPiece(this, new Position(position.getRow(), position.getColumn()));
        this.setPosition(new Position(position.getRow(), position.getColumn()));
        if (idx == 5) {
            ChessBoard.getInstance().setKing(position, colour);
        }
    }

    // computes the rating based on type and position on board
    public int getWorth() {
        return worth + ratingsBoard[position.getRow()][position.getColumn()];
    }
    
    public abstract LinkedList<Move> getMoves(Position pos);

    public boolean getInitialPos() {
        return initialPos;
    }

    public void changeInitialPos() {
        initialPos = !initialPos;
    }
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getIdx() {
        return idx;
    }

    public int getCountMoves() {
        return countMoves;
    }

    public void incCountMoves() {
        countMoves++;
    }

    public void decCountMoves() {
        countMoves--;
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
}