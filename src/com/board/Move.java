package com.board;

// stores a move as two positions: source and destination
public class Move {
    private Position src;
    private Position dest;

    public Move() {
    }
    public Move(Position src, Position dest) {
        this.src = src;
        this.dest = dest;
    }

    public Position getSrc() {
        return src;
    }

    public Position getDest() {
        return dest;
    }

    public void setSrc(Position src) {
        this.src = src;
    }

    public void setDest(Position dest) {
        this.dest = dest;
    }
}