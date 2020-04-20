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
    // we implemented the game using the negamax algorithm;
    // it returns a pair that includes the score that set of moves
    // will bring and the move that generated it;
    // in the end the best move will be sent to the applyMove
    // method to be executed
    public Pair<Integer, Move> negaMax(TeamColour player, int depth) {
        if (depth == 0 || isGameOver()) {
            return new Pair<Integer, Move>(eval(player), new Move());
        }
        LinkedList<Move> moves = new LinkedList<>();
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        ChessBoard board = ChessBoard.getInstance();
        // if the king is attacked it only takes as possible moves the moves
        // that protect the king
        if (Check.attackedPos(board.getKing(player))) {
            moves = Check.canDefendPos(board.getKing(player));
            if (moves == null) {
                new Resign().executeCommand();
            }
        } else {
            // otherwise it takes all possible move
            moves = getAllMoves(player);
        }
        // every moved will be tested and the algorithm will go one to the next step
        // in depth to see the generated options and choose the best score according
        // to the player 
        for (Move nextMove : moves) {
            ChessPiece piece = Check.probeMove(nextMove.getSrc(), nextMove.getDest());                     
            if (!Check.attackedPos(ChessBoard.getInstance().getKing(player))) {
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
            // after that branch is completed the move is undone
            Check.undoMove(nextMove.getSrc(), nextMove.getDest(), piece);
        }
        if (player.equals(TeamColour.White)) {
            ChessGame.getInstance().setColour(TeamColour.White);
        } else {
                ChessGame.getInstance().setColour(TeamColour.Black);
            }
        return new Pair<Integer, Move>(bestScore, bestMove);
    }
    // applyMove method receives the move negamax picked and executes it
    public void applyMove(Move move) {
        ChessBoard board = ChessBoard.getInstance();
        if (move != null) {
            ChessPiece chessPiece = board.getChessPiece(move.getSrc());
            chessPiece.move(move.getDest());  // moves the piece
            chessPiece.incCountMoves();
            chessPiece.changeInitialPos();
            boolean pawnToQueen = false;
            checkPawnPromotion(chessPiece, move.getDest(), pawnToQueen);
            sendMoveToXboard(move.getSrc(), move.getDest(), pawnToQueen);
        } else {
            new Resign().executeCommand();
        }
    }
    // checkPawnPromotion checks if a pawn made to the other side of the board
    // and if so, turns it into a queen
    public void checkPawnPromotion(ChessPiece chessPiece, Position pos, boolean pawnToQueen) {
        if (chessPiece.getIdx() == 0) {
            if (chessPiece.getColour().equals(TeamColour.Black) && pos.getRow() == 0) { 
                ChessBoard.getInstance().takeOutChessPiece(pos);
                ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.Black, 4, Rating.bQueenBoard, true), pos);
                pawnToQueen = true;
            } else
                if (chessPiece.getColour().equals(TeamColour.White) && pos.getRow() == 7) {
                    ChessBoard.getInstance().takeOutChessPiece(pos);
                    ChessBoard.getInstance().putChessPiece(new Queen(TeamColour.White, 4, Rating.wQueenBoard, true), pos);
                    pawnToQueen = true;
                }
        }
    }
    // sendMoveToXboard takes the coordonates of the executed move and sends them to the Xboard
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
    // checks if the game ended with check mate
    public boolean isGameOver() {
        ChessBoard board = ChessBoard.getInstance();
        if (Check.attackedPos(board.getKing(ChessGame.getInstance().getColour())) &&
            Check.canDefendPos(board.getKing(ChessGame.getInstance().getColour())) == null) {
            return true;
        }
        return false;
    }
    // checks if the opponent is in chess
    public boolean checkChess() {
        boolean chess;
        ChessGame.getInstance().switchTeam();
        chess = Check.attackedPos(ChessBoard.getInstance().getKing(ChessGame.getInstance().getColour()));
        ChessGame.getInstance().switchTeam();
        return chess;
        
    }
    // checkKingSafty method counts the number of opponent chesspieces and friendly
    // chesspieces that are around the king in order to add the differrence to the
    // score
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
                            friends += 10;
                        } else {
                            if (piece.getIdx() == 4)  // an enemy queen is worth more
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
    // eval method evaluates the board after the depth reaches 0 or the game is over
    // and returns the score that line of moves generated
    public int eval(TeamColour team) {
        // if check mate
        if (getWinner() != null) {
            if (getWinner().equals(team)) {
                return Integer.MAX_VALUE;
            } else 
                return Integer.MIN_VALUE;
        } else {
            // if the king is in chess
            if (Check.attackedPos(ChessBoard.getInstance().getKing(team)))
                return Integer.MIN_VALUE;
            int strength = 0;  // the player's points on the board
            int oppStrength = 0;  // the opponent's points on the board
            if (checkChess())  // if the opponent is in chess, strength is increased
                strength = 15000;
            int noOfPieces = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (ChessBoard.getInstance().getBoard()[i][j] != null) {
                        noOfPieces++;
                        ChessPiece piece = ChessBoard.getInstance().getBoard()[i][j];
                        if (piece.getColour().equals(team)) {
                            if (piece.getIdx() == 4 && (i == ChessBoard.getInstance().getKing(team).getRow() 
                               || j == ChessBoard.getInstance().getKing(team).getColumn()))
                               strength += 50;  // a frindly queen around is worth more
                            strength += piece.getWorth();
                        } else {
                            if (piece.getIdx() == 4 && (i == ChessBoard.getInstance().getKing(team).getRow() 
                               || j == ChessBoard.getInstance().getKing(team).getColumn()))
                               oppStrength += 100;   // an enemy queen around is worth more for the opponent
                            oppStrength += piece.getWorth();
                        }
                    }
                }
            }
            // if there are few pieces left the king's positions ratings change in order
            // to be moved towards the middle of the board
            if (noOfPieces < 6)
                ChessBoard.getInstance().getChessPiece(ChessBoard.getInstance().getKing(team))
                    .setRatingsBoard(Rating.KingEndBoard);
            // mobility represents the number of possible moves a player has for the next move
            int mobility = getAllMoves(team).size();
            ChessGame.getInstance().switchTeam();
            mobility -= getAllMoves(ChessGame.getInstance().getColour()).size();
            ChessGame.getInstance().switchTeam();
            int safety = checkKingSafety();
            return strength - oppStrength + mobility + safety;
        }
    }
    // getAllMoves returns a list with all the possible moves on the board
    // by adding the lists of all the pieces
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
    // in case of check mate a winner is set
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