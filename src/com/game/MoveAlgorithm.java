package com.game;

import java.util.LinkedList;

import com.board.ChessBoard;
import com.board.Move;
import com.board.Position;
import com.commands.Resign;
import com.pieces.ChessPiece;
import com.pieces.Queen;
import com.pieces.TeamColour;

public class MoveAlgorithm {

    public Pair<Integer, Move> negaMax(TeamColour player, int depth) {
        if (depth == 0 || isGameOver()) {
            return new Pair<Integer, Move>(eval(ChessGame.getInstance().getColour()), new Move());
        }
        LinkedList<Move> moves = new LinkedList<>();
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        ChessBoard board = ChessBoard.getInstance();
        // System.out.println("verificam in nega regele e pe " +
        //  ChessBoard.getInstance().getBoard()[ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()).getRow()][ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()).getColumn()].getColour().name().substring(0,1) +
        // " rege alb " + ChessBoard.getInstance().getWhiteKing().getRow() + ChessBoard.getInstance().getBlackKing().getColumn() + " rege negru " + ChessBoard.getInstance().getBlackKing().getRow() + ChessBoard.getInstance().getBlackKing().getColumn() +  " cul noi "
        // + ChessGame.getInstance().getColour().name().substring(0,1));
        if (Check.attackedPos(board.getKing(ChessGame.getInstance().getColour()))) {
            System.out.println("rege atacat");
            moves = Check.canDefendPos(board.getKing(ChessGame.getInstance().getColour()));
            if (moves == null) {
                System.out.println("nu apar");
                new Resign().executeCommand();
            }
        } else {
            moves = getAllMoves(player);
        }

        for (Move nextMove : moves) {
            ChessPiece piece = Check.probeMove(nextMove.getSrc(), nextMove.getDest());                     
            if (!Check.attackedPos(ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()))) {
                int score;
                if (player.equals(TeamColour.Black)) {
                    ChessGame.getInstance().setColour(TeamColour.White);
                    score = -negaMax(TeamColour.White, depth - 1).first;
                } else {
                    ChessGame.getInstance().setColour(TeamColour.Black);
                    score = -negaMax(TeamColour.Black, depth - 1).first;
                }
                if (score >= bestScore) {
                    bestScore = score;
                    bestMove = nextMove;
                }
            }
            Check.undoMove(nextMove.getSrc(), nextMove.getDest(), piece);
        }
        if (player.equals(TeamColour.White)) {
            ChessGame.getInstance().setColour(TeamColour.White);
        } else {
                ChessGame.getInstance().setColour(TeamColour.Black);
        }
        return new Pair<Integer, Move>(bestScore, bestMove);
    }

    public void applyMove(Move move) {
        ChessBoard board = ChessBoard.getInstance();
        System.out.println("REGE SI CULOARE JOC " + board.getKing(ChessGame.getInstance().getColour()).getRow() + " " + ChessGame.getInstance().getColour().name().substring(0,1));
        if (move != null) {
            ChessPiece chessPiece = board.getChessPiece(move.getSrc());
            chessPiece.move(move.getDest());
            chessPiece.changeInitialPos();
            boolean pawnToQueen = false;
            checkPawnPromotion(chessPiece, move.getDest(), pawnToQueen);
            sendMoveToXboard(move.getSrc(), move.getDest(), pawnToQueen);
        } else {
            System.out.println("RESIGN REGE SI CULOARE JOC " + board.getKing(ChessGame.getInstance().getColour()).getRow() + " " + ChessGame.getInstance().getColour().name().substring(0,1));
            new Resign().executeCommand();
        }
    }

    public void checkPawnPromotion(ChessPiece chessPiece, Position pos, boolean pawnToQueen) {
        if (chessPiece.getIdx() == 0) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black) && pos.getRow() == 0) { 
                ChessBoard.getInstance().takeOutChessPiece(pos);
                ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.Black, false, 4, Rating.wQueenBoard, true), pos);
                pawnToQueen = true;
            } else
                if (ChessGame.getInstance().getColour().equals(TeamColour.White) && pos.getRow() == 7) {
                    ChessBoard.getInstance().takeOutChessPiece(pos);
                    ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.White, false, 4, Rating.wQueenBoard, true), pos);
                    pawnToQueen = true;
                }
        }
    }

    public void sendMoveToXboard(Position pos1, Position pos2, boolean pawnToQueen) {
        ChessGame chessGame = ChessGame.getInstance();
        int ln_dest = pos2.getRow() + 1;
        int ln_src = pos1.getRow() + 1;
        System.out.println("move " + chessGame.getPos().get(pos1.getColumn()) 
                            + ln_src + chessGame.getPos().get(pos2.getColumn()) + ln_dest);  
        if (pawnToQueen) {
            System.out.println("q\n");
        } else {
            System.out.println("\n");
        }                    
    }

    public boolean isGameOver() {
        ChessBoard board = ChessBoard.getInstance();
        if (Check.attackedPos(board.getKing(ChessGame.getInstance().getColour())) &&
             Check.canDefendPos(board.getKing(ChessGame.getInstance().getColour())) == null) {
            return true;
        }
        return false;
    }

    public boolean checkChess() {
        boolean chess;
        ChessGame.getInstance().switchTeam();
        chess = Check.attackedPos(ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()));
        ChessGame.getInstance().switchTeam();
        return chess;
        
    }
    public int eval(TeamColour team) {
        if (getWinner() != null) {
            if (getWinner().equals(team)) {
                return Integer.MAX_VALUE;
            } else 
                return Integer.MIN_VALUE;
        } else {
            if (checkChess())
                return Integer.MAX_VALUE;
            int strength = 0;
            int oppStrength = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (ChessBoard.getInstance().getBoard()[i][j] != null)
                        if (ChessBoard.getInstance().getBoard()[i][j].getColour().equals(team)) {
                        strength += ChessBoard.getInstance().getBoard()[i][j].getWorth();
                    } else {
                        oppStrength += ChessBoard.getInstance().getBoard()[i][j].getWorth();
                    }
                }
            }
            return strength - oppStrength;
        }
    }

    public LinkedList<Move> getAllMoves(TeamColour team) {
        LinkedList<Move> possibleMoves = new LinkedList<>();
        ChessBoard board = ChessBoard.getInstance();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] != null && board.getBoard()[i][j].getColour().equals(team)) {
                    ChessPiece chessPiece = board.getChessPiece(new Position(i, j));
                    LinkedList<Move> allMoves = chessPiece.getMoves(chessPiece.getPosition());
                    if (allMoves != null) {
                        possibleMoves.addAll(allMoves);
                    }

                }
            }
        }
        return possibleMoves;
    }

    public TeamColour getWinner() {
        if (isGameOver()) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.White)) {
                return TeamColour.Black;
            } else {
                return TeamColour.White;
            }
        } else {
            ChessGame.getInstance().switchTeam();
            if (isGameOver()) {
                if (ChessGame.getInstance().getColour().equals(TeamColour.White)) {
                    return TeamColour.Black;
                } else {
                    return TeamColour.White;
                }
            }
            ChessGame.getInstance().switchTeam();
        }        
        return null;
    }



}