package com.game;

import com.board.ChessBoard;
import com.board.Position;
import com.commands.Resign;
import com.pieces.ChessPiece;
import com.pieces.Pawn;
import com.pieces.TeamColour;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ChessGame {
    private Map<Integer, String> pos = new HashMap<>();
    private boolean force;
    private TeamColour teamToMove;
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
//        ChessPiece c = board.getChessPiece(new Position(6, 0));
//        LinkedList<Position> mo = c.getMoves(new Position(6, 0));
//        c.move(mo.get(0));
//        int ln_dest = mo.get(0).getRow() + 1;
//                        int ln_src = 6 + 1;
//                        System.out.print("move " + pos.get(0) + ln_src
//                                + pos.get(mo.get(0).getColumn())
//                                + ln_dest + "\n");
//        ChessPiece c1 = board.getChessPiece(new Position(7, 0));
//        LinkedList<Position> mo1 = c.getMoves(new Position(7, 0));
//        c1.move(mo.get(0));
//        int ln_dest1 = mo.get(0).getRow() + 1;
//        int ln_src1 = 7 + 1;
//        System.out.print("move " + pos.get(0) + ln_src1
//                + pos.get(mo1.get(0).getColumn())
//                + ln_dest1 + "\n");
//        System.out.println(mo.size());
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; ++i) {
                if (board.getBoard()[i][j] != null &&
                        board.getBoard()[i][j].getColour().equals(colour)) {
                    LinkedList<Position> moves;
                    ChessPiece chessPiece = board.getChessPiece(new Position(i,j));
                    moves = chessPiece.getMoves(chessPiece.getPosition());
                    if (moves != null) {
                        chessPiece.move(moves.get(0));
                        int ln_dest = moves.get(0).getRow() + 1;
                        int ln_src = i + 1;
                        System.out.print("move " + pos.get(j) + ln_src
                                + pos.get(moves.get(0).getColumn())
                                + ln_dest + "\n");
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
                !board.verifyPosition(source)) {
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

    public TeamColour getTeamToMove() {
        return teamToMove;
    }

    public void setTeamToMove(TeamColour teamToMove) {
        this.teamToMove = teamToMove;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public Map<Integer, String> getPos() {
        return pos;
    }
}
