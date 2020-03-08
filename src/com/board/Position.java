package com.board;

import com.constants.Constants;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isValidPosition() {
        if (getRow() < Constants.LOW_BOARD_LIMIT
                || getRow() >= Constants.SUP_BOARD_LIMIT
                || getColumn() < Constants.LOW_BOARD_LIMIT
                || getColumn() >= Constants.SUP_BOARD_LIMIT)
            return false;
        return true;
    }
}
