package com.game;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.pieces.ChessPiece;
import com.pieces.Queen;
import com.pieces.TeamColour;

import java.util.HashMap;
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
       Pair<Integer, Move> move = null;
        move = new MoveAlgorithm().negaMax(colour, 3);
        new MoveAlgorithm().applyMove(move.second);
    }

    // converts the move received from xboard in a Position type object
    // modify the position of the opponent on the board
    public void oppMove(String opponentMove) {
        ChessBoard board = ChessBoard.getInstance();
        String sourceCol = opponentMove.substring(0,1);
        System.out.println(opponentMove);
        int sourceRow = Integer.parseInt(opponentMove.substring(1,2));
        String destCol = opponentMove.substring(2,3);
        int destRow;
        if (opponentMove.length() == 4)
            destRow = Integer.parseInt(opponentMove.substring(3));
        else 
            destRow = Integer.parseInt(opponentMove.substring(3, 4));

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
 if (chessPiece != null) {  /// ASTA NU TRB AICI CA ADVERSARUL NU DA NICIODATA MISCARI CU PIESE NULE DAR FARA EL MAI DA RATEURI DC PLMM NJ
            board.takeOutChessPiece(source);
            board.putChessPiece(chessPiece, dest);
            chessPiece.setPosition(dest);
            if (chessPiece.getIdx() == 5) {
                if (!ChessGame.getInstance().getColour().equals(TeamColour.Black)) 
                    board.setBlackKing(dest);
                else 
                    board.setWhiteKing(dest);
            }
            if (chessPiece.getIdx() == 0) {
                if (ChessGame.getInstance().getColour().equals(TeamColour.Black) && dest.getRow() == 7) {
                    board.takeOutChessPiece(dest);
                    board.putChessPiece(new Queen(TeamColour.White, false, 4, Rating.wQueenBoard), dest);
                } else if (ChessGame.getInstance().getColour().equals(TeamColour.White) && dest.getRow() == 0) {
                    board.takeOutChessPiece(dest);
                    board.putChessPiece(new Queen(TeamColour.Black, false, 4, Rating.wQueenBoard), dest);
                }
            }
} else System.out.println("nu i " + source.getRow() + " " + source.getColumn());
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

    public void switchTeam() {
        if (colour.equals(TeamColour.White)) {
            colour = TeamColour.Black;
        } else {
            colour = TeamColour.White;
        }
    }
}
