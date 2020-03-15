package com.game;

import com.board.ChessBoard;
import com.board.Position;
import com.commands.Resign;
import com.pieces.ChessPiece;
import com.pieces.Pawn;
import com.pieces.TeamColour;

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
                    pawn.eatOpponent();
                    if (pawn.getPosition().getRow() != i) {
                        int ln = i + 1;
                        int col = pawn.getPosition().getColumn();
                        System.out.print("move " + pos.get(j) + ln + pos.get(col)
                                + (ln + ChessGame.getInstance().getSign()) + "\n");
                        moved = true;
                        break;
                    }
                    if (new Position(i + ChessGame.getInstance().getSign(), j).isValidPosition()
                            && !board.verifyPosition(new Position(i + ChessGame.getInstance().getSign(), j))) {
                        pawn.move(new Position(i + ChessGame.getInstance().getSign(), j));
                        int ln = i + 1;
                        System.out.print("move " + pos.get(j) + ln + pos.get(j)
                                + (ln + ChessGame.getInstance().getSign()) + "\n");
                        moved = true;
                        break;
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
}
