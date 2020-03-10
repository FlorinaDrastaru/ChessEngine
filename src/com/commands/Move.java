package com.commands;


import com.board.ChessBoard;
import com.board.Position;
import com.pieces.Pawn;

import java.util.HashMap;
import java.util.Map;

public class Move implements Command {
    @Override
    public void executeCommand() {
//        ChessBoard board = ChessBoard.getInstance();
//        Pawn pawn = (Pawn) board.getChessPiece(new Position(6, 0));
//        pawn.move();
        ChessBoard board = ChessBoard.getInstance();
        Map<Integer, String> pos = new HashMap<>();
        pos.put(0, "a");
        pos.put(1, "b");
        pos.put(2, "c");
        pos.put(3, "d");
        pos.put(4, "e");
        pos.put(5, "f");
        pos.put(6, "g");
        pos.put(7, "h");

        for (int j = 0; j < 8; ++j) {
            if (board.verifyPosition(new Position(6, j)) && !board.verifyPosition(new Position(5, j))) {
                Pawn pawn = (Pawn) board.getChessPiece(new Position(6, j));
                board.takeOutChessPiece(new Position(6, j));
                board.putChessPiece(pawn, new Position(5, j));
                pawn.move(new Position(5, j));
                System.out.print("move " + pos.get(j) + "7" + pos.get(j) + "6\n");
                break;
            }

        }

    }
}