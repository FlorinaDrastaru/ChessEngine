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
        move = new MoveAlgorithm().negaMax(colour, 2);
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
            checkCastle(source, dest);
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
                    board.putChessPiece(new Queen(TeamColour.White, false, 4, Rating.wQueenBoard, true), dest);
                } else if (ChessGame.getInstance().getColour().equals(TeamColour.White) && dest.getRow() == 0) {
                    board.takeOutChessPiece(dest);
                    board.putChessPiece(new Queen(TeamColour.Black, false, 4, Rating.wQueenBoard, true), dest);
                }
            }
        }
    }
 

    public void checkCastle(Position source, Position dest) {
        ChessBoard board = ChessBoard.getInstance();
        ChessPiece chessPiece = board.getChessPiece(source);
        boolean freeSquareToLeft = true;
        boolean freeSquareToRight = true;
        if (chessPiece.getIdx() == 5 && chessPiece.getInitialPos()) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black)) {
                if (dest.getColumn() - source.getColumn() == 2) {
       
                for (int j = ChessBoard.getInstance().getWhiteKing().getColumn() + 1; j < 7; j++) {
                    if (board.getBoard()[0][j] != null) {
                        freeSquareToRight = false;
                    }
                }
                if (freeSquareToRight && board.getBoard()[0][7].getInitialPos()) {
                    board.putChessPiece(board.getChessPiece(new Position(0, 7)), new Position(0, 5));
                    board.takeOutChessPiece(new Position(0, 7));
                    
                }
            }
            if (dest.getColumn() - source.getColumn() == -2) {
                for (int j = ChessBoard.getInstance().getWhiteKing().getColumn() - 1; j > 0; j--) {
                    if (board.getBoard()[0][j] != null) {
                        freeSquareToLeft = false;
                    }
                }
                if (freeSquareToLeft && board.getBoard()[0][0].getInitialPos()) {
                    board.putChessPiece(board.getChessPiece(new Position(0, 0)), new Position(0, 3));
                    board.takeOutChessPiece(new Position(0, 0));
                    
                }
            }
            }

            if (ChessGame.getInstance().getColour().equals(TeamColour.White)) {
                if (dest.getColumn() - source.getColumn() == 2) {
                    for (int j = ChessBoard.getInstance().getBlackKing().getColumn() + 1; j < 7; j++) {
                        if (board.getBoard()[7][j] != null) {
                            freeSquareToRight = false;
                        }
                    }
                    if (freeSquareToRight && board.getBoard()[7][7].getInitialPos()) {
                        board.putChessPiece(board.getChessPiece(new Position(7, 7)), new Position(7, 5));
                        board.takeOutChessPiece(new Position(7, 7));
                        
                    }
                }
                if (dest.getColumn() - source.getColumn() == -2) {
                    for (int j = ChessBoard.getInstance().getBlackKing().getColumn() - 1; j > 0; j--) {
                        if (board.getBoard()[7][j] != null) {
                            freeSquareToLeft = false;
                        }
                    }
                    if (freeSquareToLeft && board.getBoard()[7][0].getInitialPos()) {
                        board.putChessPiece(board.getChessPiece(new Position(7, 0)), new Position(7, 3));
                        board.takeOutChessPiece(new Position(7, 0));
                        
                    }
                }
            }


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
