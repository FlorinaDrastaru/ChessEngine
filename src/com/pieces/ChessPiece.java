package com.pieces;

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
    }
    public void eatOpponent(){}

    public abstract LinkedList<Position> getMoves(Position pos);
}
