package com.pieces;
import com.board.ChessBoard;
import com.board.Position;
import java.util.LinkedList;


public abstract class ChessPiece {
    private Position position;
    private boolean eliminated = false;
    private TeamColour colour;
    public int idx;


    public ChessPiece(TeamColour colour, boolean eliminated, int idx) {
        this.eliminated = eliminated;
        this.colour = colour;
        this.idx = idx;
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

    public void setColour(TeamColour colour) {
        this.colour = colour;
    }

    public void move(Position position) {
        ChessBoard board = ChessBoard.getInstance();
        board.takeOutChessPiece(this.getPosition());
        board.putChessPiece(this, new Position(position.getRow(), position.getColumn()));
        this.setPosition(new Position(position.getRow(), position.getColumn()));
    }

    public void eatOpponent() {
    }


    public abstract LinkedList<Position> getMoves(Position pos);





}