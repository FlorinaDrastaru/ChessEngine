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
    private Map<Integer, String> pos = new HashMap<>();
    private boolean force;
    private boolean white;
    private int sign;
    private TeamColour colour;

    private static ChessGame instance = null;
    private ChessGame() {

        pos.put(0, "a");
        pos.put(1, "b");
        pos.put(2, "c");
        pos.put(3, "d");
        pos.put(4, "e");
        pos.put(5, "f");
        pos.put(6, "g");
        pos.put(7, "h");
    }
    
    // create a Singleton in order to have a single instance of the game
    public static ChessGame getInstance() {
        if (instance == null) {
            instance = new ChessGame();
        }
        return instance;
    }

    public TeamColour getColour() {
        return colour;
    }

    public void setColour(TeamColour colour) {
        this.colour = colour;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public Map<Integer, String> getPos() {
        return pos;
    }

    /*public void move2() {  // mutarea noastra, in if verifica si daca nu cumva a fost mancat si e
        // piesa alba acolo ca incerca sa o faca indiferent.
        // am mutat move la joc ca avea mai mult sens aici ca e cum jucam noi nu i comanda primita gen
        //cum crezi si tu acuma
        ChessBoard board = ChessBoard.getInstance();
        boolean moved = false;
        for (int i = 6; i > 0; --i) {
            for (int j = 0; j < 8; ++j) {
                if (board.verifyPosition(new Position(i, j)) && board.getChessPiece(new Position(i, j)).idx == 0
                        && board.getChessPiece(new Position(i, j)).getColour().equals(TeamColour.Black)) {
                    Pawn pawn = (Pawn) board.getChessPiece(new Position(i, j));
                    if (!board.verifyPosition(new Position(i-1, j))) {
                        pawn.move(new Position(i-1, j));
                        int ln = i + 1;
                        System.out.print("move " + pos.get(j) + ln + pos.get(j) + i + "\n");
                        moved = true;
                        break;
                    } else { // daca n are loc sa mearga in fata verifica daca poate sa manance
                        pawn.eatOpponent();
                        if (pawn.getPosition().getRow() != i) {
                            int ln = i + 1;
                            int col = pawn.getPosition().getColumn();
                            System.out.print("move " + pos.get(j) + ln + pos.get(col) + i + "\n");
                            moved = true;
                            break;
                        }
                    }
                }
            }
            if (moved) break;
        }
        if (!moved) new Resign().executeCommand();
    }*/
    
    // execute a move on the table
    public void move() {
        ChessBoard board = ChessBoard.getInstance();
        boolean moved = false;
        for (int i = 7; i > 0; --i) {
            for (int j = 0; j < 8; ++j) {
                // search for a valid move and send it to the xboard
                if (board.verifyPosition(new Position(i, j)) && board.getChessPiece(new Position(i, j)).idx == 0
                        && board.getChessPiece(new Position(i, j)).getColour().equals(ChessGame.getInstance().getColour())) {
                    Pawn pawn = (Pawn) board.getChessPiece(new Position(i, j));
                    if (new Position(i + ChessGame.getInstance().getSign(), j).isValidPosition()
                        && !board.verifyPosition(new Position(i + ChessGame.getInstance().getSign(), j))) {
                        pawn.move(new Position(i + ChessGame.getInstance().getSign(), j));
                        int ln = i + 1;
                        System.out.print("move " + pos.get(j) + ln + pos.get(j)
                                + (ln + ChessGame.getInstance().getSign()) + "\n");
                        moved = true;
                        break;
                    } else {
                        // verify if the piece can eliminate another piece
                        pawn.eatOpponent();
                        if (pawn.getPosition().getRow() != i) {
                            int ln = i + 1;
                            int col = pawn.getPosition().getColumn();
                            System.out.print("move " + pos.get(j) + ln + pos.get(col)
                                    + (ln + ChessGame.getInstance().getSign()) + "\n");
                            moved = true;
                            break;
                        }
                    }
                }
            }
            if (moved) break;
        }
        if (!moved) new Resign().executeCommand();
    }
    // converts the move received from xboard in a Position type object
    // modify the position of the opponent on the board
    public void oppMove(String opponentMove) {
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
        
        // verify if the move of the opponent is valid
        if (source.isValidPosition() && dest.isValidPosition() &&
                board.verifyPosition(source)) {
            ChessPiece chessPiece = board.getChessPiece(source);
            board.takeOutChessPiece(source);
            board.putChessPiece(chessPiece, dest);
            chessPiece.setPosition(dest);
        }
    }
}
