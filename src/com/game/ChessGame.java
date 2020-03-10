package com.game;

import com.board.ChessBoard;
import com.board.Position;
import com.commands.Resign;
import com.pieces.ChessPiece;
import com.pieces.Pawn;
import com.pieces.TeamColour;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class ChessGame {
    public Map<Integer, String> pos = new HashMap<>();
    public ChessGame() {
        pos.put(0, "a");
        pos.put(1, "b");
        pos.put(2, "c");
        pos.put(3, "d");
        pos.put(4, "e");
        pos.put(5, "f");
        pos.put(6, "g");
        pos.put(7, "h");
    }
    public void move() {  // mutarea noastra, in if verifica si daca nu cumva a fost mancat si e
                         // piesa alba acolo ca incerca sa o faca indiferent.
                        // am mutat move la joc ca avea mai mult sens aici ca e cum jucam noi nu i comanda primita gen
        //cum crezi si tu acuma
        ChessBoard board = ChessBoard.getInstance();
        boolean moved = false;
        for (int j = 0; j < 8; ++j) {
            if (board.verifyPosition(new Position(6, j))  && !board.verifyPosition(new Position(5, j))
                    &&  board.getChessPiece(new Position(6, j)).getColour().equals(TeamColour.Black)) {
                Pawn pawn = (Pawn) board.getChessPiece(new Position(6, j));
                board.takeOutChessPiece(new Position(6, j));
                board.putChessPiece(pawn, new Position(5, j));
                pawn.move(new Position(5, j));
                System.out.print("move " + pos.get(j) + "7" + pos.get(j) + "6\n");
                moved = true;
                break;
            }
        }
        if (!moved) new Resign().executeCommand();
    }

    public void oppMove(String opponentMove) {  // converteste alea in locatie si il pune acolo si il sterge de unde era easy
        ChessBoard board = ChessBoard.getInstance();

        String sourceCol = opponentMove.substring(0,1);
        int sourceRow = Integer.parseInt(opponentMove.substring(1,2));
        String destCol = opponentMove.substring(2,3);
        int destRow = Integer.parseInt(opponentMove.substring(3));

        int sourceColumn = -1;
        int destColumn = -1;

        for (Map.Entry<Integer, String> p : pos.entrySet()) {
            if (p.getValue().equals(sourceCol)) sourceColumn = p.getKey();
            if (p.getValue().equals(destCol)) destColumn = p.getKey();
        }
        Position source = new Position(--sourceRow, sourceColumn);
        Position dest = new Position(--destRow, destColumn);

        if (source.isValidPosition() && dest.isValidPosition() &&
                board.verifyPosition(source)) {
            ChessPiece chessPiece = board.getChessPiece(source);
            board.takeOutChessPiece(source);
            board.putChessPiece(chessPiece, dest);
            chessPiece.move(dest);
        }

    }



}
