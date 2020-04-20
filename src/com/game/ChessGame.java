package com.game;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.pieces.ChessPiece;
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

    // executes a move on the board, using negamax
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
            // checks if it is a castle move
            checkCastle(source, dest);
            // checks if it is an en passant move
            checkEnPassant(source, dest);
            board.takeOutChessPiece(source);
            board.putChessPiece(chessPiece, dest);
            chessPiece.setPosition(dest);
            // sets the new position for the king if necessary
            if (chessPiece.getIdx() == 5) {
                if (!colour.equals(TeamColour.Black)) 
                    board.setBlackKing(dest);
                else 
                    board.setWhiteKing(dest);
            }
            boolean pawnToQueen = false;
            // checks if the pawn is promoted to a queen
            new MoveAlgorithm().checkPawnPromotion(chessPiece, dest, pawnToQueen);
        }
    }
 
    // checks if the opponent has castled in order to also move the rook
    public void checkCastle(Position source, Position dest) {
        ChessBoard board = ChessBoard.getInstance();
        ChessPiece chessPiece = board.getChessPiece(source);
        Position king;
        boolean freeSquareToLeft = true;
        boolean freeSquareToRight = true;
        if (chessPiece.getIdx() == 5 && chessPiece.getInitialPos()) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black))
                king =  ChessBoard.getInstance().getWhiteKing();
            else 
                king =  ChessBoard.getInstance().getBlackKing();
            if (dest.getColumn() - source.getColumn() == 2) {
                for (int j = king.getColumn() + 1; j < 7; j++) {
                    if (board.getBoard()[king.getRow()][j] != null) {
                        freeSquareToRight = false;
                    }
                }
                    if (freeSquareToRight && board.getBoard()[king.getRow()][7].getInitialPos()) {
                        board.putChessPiece(board.getChessPiece(new Position(king.getRow(), 7)), new Position(king.getRow(), 5));
                        board.takeOutChessPiece(new Position(king.getRow(), 7));  
                    }
                }
                if (dest.getColumn() - source.getColumn() == -2) {
                    for (int j = king.getColumn() - 1; j > 0; j--) {
                        if (board.getBoard()[king.getRow()][j] != null) {
                            freeSquareToLeft = false;
                        }
                    }
                    if (freeSquareToLeft && board.getBoard()[king.getRow()][0].getInitialPos()) {
                        board.putChessPiece(board.getChessPiece(new Position(king.getRow(), 0)), new Position(king.getRow(), 3));
                        board.takeOutChessPiece(new Position(king.getRow(), 0));   
                    }
                }
            }
    }
    // checks if the opponent has executed an en passant in order to eliminate the pawn
    public void checkEnPassant(Position source, Position dest) {
        ChessBoard board = ChessBoard.getInstance();
        ChessPiece chessPiece = board.getChessPiece(source);
        ChessPiece nearPieceRight = null;
        ChessPiece nearPieceLeft = null;
        int s, d;
        TeamColour teamColour;
        if (chessPiece.getIdx() == 0) {
            // the en passant positions are different for each team
            if (colour.equals(TeamColour.Black)) {
                s = 4;
                d = 5;
                teamColour = TeamColour.Black;
            } else {
                s = 3;
                d = 2;
                teamColour = TeamColour.White;
            }
            if (source.getColumn() < 7) {
                nearPieceRight = board.getChessPiece(new Position(source.getRow(),
                                                     source.getColumn() + 1));
            }
            if (source.getColumn() > 0) {
                nearPieceLeft = board.getChessPiece(new Position(source.getRow(), 
                                                    source.getColumn() - 1));
            }
            if (source.getRow() == s && dest.getRow() == d 
                && colour.equals(teamColour)) {
                if (nearPieceRight != null) {
                    if (dest.getColumn() - source.getColumn() == 1)
                        enPassant(nearPieceRight);
                }
                if (nearPieceLeft != null) {
                    if (dest.getColumn() - source.getColumn() == -1)
                        enPassant(nearPieceLeft); 
                }
            }
        }
    }

    public void enPassant(ChessPiece piece) {
        if (piece.getIdx() == 0 
            && piece.getColour().equals(ChessGame.getInstance().getColour())
            && piece.getCountMoves() == 1){
            ChessBoard.getInstance().takeOutChessPiece(piece.getPosition());
        }
    }
    public TeamColour getColour() {
        return colour;
    }

    public void setColour(TeamColour colour) {
        this.colour = colour;
        if (colour.equals(TeamColour.White)) {
            setSign(1);
        } else {
            setSign(-1);
        }
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
            sign = -1;
        } else {
            colour = TeamColour.White;
            sign = 1;
        }
    }
}
