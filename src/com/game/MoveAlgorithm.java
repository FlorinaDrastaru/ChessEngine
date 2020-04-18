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
        if (move != null) {
            ChessPiece chessPiece = board.getChessPiece(move.getSrc());
            chessPiece.move(move.getDest());
            chessPiece.incCountMoves();
            chessPiece.changeInitialPos();
            boolean pawnToQueen = false;
            checkPawnPromotion(chessPiece, move.getDest(), pawnToQueen);
            sendMoveToXboard(move.getSrc(), move.getDest(), pawnToQueen);
        } else {
            new Resign().executeCommand();
        }
    }

    public void checkPawnPromotion(ChessPiece chessPiece, Position pos, boolean pawnToQueen) {
        if (chessPiece.getIdx() == 0) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black) && pos.getRow() == 0) { 
                ChessBoard.getInstance().takeOutChessPiece(pos);
                ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.Black, 4, Rating.wQueenBoard, true), pos);
                pawnToQueen = true;
            } else
                if (ChessGame.getInstance().getColour().equals(TeamColour.White) && pos.getRow() == 7) {
                    ChessBoard.getInstance().takeOutChessPiece(pos);
                    ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.White, 4, Rating.wQueenBoard, true), pos);
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

    public int checkKingSafety() {
        int friends = 0;
        int enemies = 0;
        Position pos = ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour());
        int ln = pos.getRow();
        int col = pos.getColumn();
        for (int i = ln - 1; i <= ln + 1; ++i) {
            for (int j = col - 1; j <= col + 1; ++j) {
                if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                    if (ChessBoard.getInstance().getChessPiece(new Position(i,j)) != null){
                        ChessPiece piece = ChessBoard.getInstance().getChessPiece(new Position(i,j));
                        if (piece.getColour().equals(ChessGame.getInstance().getColour())) {
                            if (piece.getIdx() == 4)
                                friends += 30;
                            else 
                                friends += 10;
                        } else {
                            if (piece.getIdx() == 4)
                                enemies += 30;
                            else 
                                enemies += 10;
                        }
                    }
                }
            }
        }
        return friends - enemies;
    }

    public int eval(TeamColour team) {
        if (getWinner() != null) {
            if (getWinner().equals(team)) {
                return Integer.MAX_VALUE;
            } else 
                return Integer.MIN_VALUE;
        } else {
            int strength = 0;
            int oppStrength = 0;
            if (checkChess())
                strength = 1000;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (ChessBoard.getInstance().getBoard()[i][j] != null) {
                    ChessPiece piece = ChessBoard.getInstance().getBoard()[i][j];
                        if (piece.getColour().equals(team)) {
                            if (piece.getIdx() == 4 && (i == ChessBoard.getInstance().getKing(team).getRow() 
                               || j == ChessBoard.getInstance().getKing(team).getColumn()))
                               strength += 50;
                            strength += piece.getWorth();
                        } else {
                            if (piece.getIdx() == 4 && (i == ChessBoard.getInstance().getKing(team).getRow() 
                               || j == ChessBoard.getInstance().getKing(team).getColumn()))
                               oppStrength += 100;
                            oppStrength += piece.getWorth();
                        }
                    }
                }
            }

            int mobility = getAllMoves(team).size();
            ChessGame.getInstance().switchTeam();
            mobility -= getAllMoves(ChessGame.getInstance().getColour()).size();
            ChessGame.getInstance().switchTeam();
            int safety = checkKingSafety();
            return strength - oppStrength + mobility + safety;
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