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

    
    public Pair<Integer, Move> negaMax(TeamColour team, int depth) {
        if (depth == 0 || isGameOver()) {
            return new Pair<Integer, Move>(eval(ChessGame.getInstance().getColour()), new Move());
        }
        
        
        LinkedList<Move> moves = getAllMoves(team);
        Move bestMove = null;
        int bestScore = -Integer.MIN_VALUE;

        for (Move nextMove : moves) {
            if (apply_move(nextMove) == true) {
                int score;
                if (team.equals(TeamColour.Black)) {
                    score = -negaMax(TeamColour.White, depth - 1).first;
                } else {
                    score = -negaMax(TeamColour.Black, depth - 1).first;
                }
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = nextMove;
                }
            }
            undoMove(nextMove);
        }

        return new Pair(bestScore, bestMove);
    }



    public void undoMove(Move m) {
        Position pos1 = m.getStartPos();
        Position pos2 = m.getEndPos();
        ChessPiece piece = ChessBoard.getInstance().getChessPiece(pos1);
        Check.undoMove(pos1, pos2, piece);
    }

    public boolean apply_move(Move move) {
        boolean moved = false;
        ChessGame game = ChessGame.getInstance();
        ChessBoard board = ChessBoard.getInstance();
        int i = move.getStartPos().getRow();
        int j = move.getStartPos().getColumn();
        ChessPiece chessPiece = board.getChessPiece(move.getStartPos());
     
        ChessPiece piece = Check.probeMove(new Position(i,j), move.getStartPos());
        if (!Check.attackedPos(ChessBoard.getInstance().getKing())) {
            Check.undoMove(move.getStartPos(), move.getEndPos(), piece);
            chessPiece.move(move.getEndPos());
            if (chessPiece.getIdx() == 5) {
                board.setKing(move.getEndPos());
            }
            boolean pawnToQueen = false;
            checkPawnPromotion(chessPiece, move.getEndPos(), pawnToQueen);
            sendMoveToXboard(move.getStartPos(), move.getEndPos(), pawnToQueen);
            moved = true;
            
        } else Check.undoMove(move.getStartPos(), move.getEndPos(), piece);

    
        return moved;
    }

    public void move() {
        while (!isGameOver()) {
            Pair<Integer, Move> move = null;
            move = negaMax(ChessGame.getInstance().getColour(), 2);
            apply_move(move.second);
        }
    }

    public void checkPawnPromotion(ChessPiece chessPiece, Position pos, boolean pawnToQueen) {
        
        if (chessPiece.getIdx() == 0) {
            if (ChessGame.getInstance().getColour().equals(TeamColour.Black) && pos.getRow() == 0) { 
                ChessBoard.getInstance().takeOutChessPiece(pos);
                ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.Black, false, 4), pos);
                pawnToQueen = true;
            } else
                if (ChessGame.getInstance().getColour().equals(TeamColour.White) && pos.getRow() == 7) {
                    ChessBoard.getInstance().takeOutChessPiece(pos);
                    ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.White, false, 4), pos);
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
        if (Check.attackedPos(board.getKing()) == true && !Check.canDefendPos(board.getKing())) {
            return true;
        }
        return false;
    }
    public int eval(TeamColour team) {
        if (getWinner().equals(team)) {
            return Integer.MAX_VALUE;
        }
        if (!getWinner().equals(team)) {
            return Integer.MIN_VALUE;
        }
        return 0;
    }


    public LinkedList<Move> getAllMoves(TeamColour team) {
        LinkedList<Move> possibleMoves = new LinkedList<>();
        ChessBoard board = ChessBoard.getInstance();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] != null && board.getBoard()[i][j].getColour().equals(team)) {
                    Position piecePos = new Position(i, j);
                    ChessPiece chessPiece = board.getChessPiece(piecePos);
                    LinkedList<Position> positions = chessPiece.getMoves(piecePos);
                    for (int k = 0; k < positions.size(); k++) {
                        Move m = new Move(piecePos, positions.get(i));
                        possibleMoves.add(m);
                    }

                }
            }
        }

        return possibleMoves;

    }

    public TeamColour getWinner() {
        ChessBoard board = ChessBoard.getInstance();

        if (Check.attackedPos(board.getBlackKing()) == true && !Check.canDefendPos(board.getBlackKing())) {
            new Resign().executeCommand();
            return TeamColour.White;
        } else if (Check.attackedPos(board.getWhiteKing()) == true && !Check.canDefendPos(board.getWhiteKing())) {
            return TeamColour.Black;
        }
        return null;
    }



}