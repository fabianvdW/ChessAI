package chess.uci.advancedclient;

import chess.ChessGameStatus;
import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;
import chess.uci.UCIGoInstance;
import chess.uci.simpleclient.SimpleClientGoInstance;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ChessAIOneGoInstance extends UCIGoInstance {
    public static int nodes;
    public static int profiler;

    public ChessAIOneGoInstance(BitBoard position) {
        super(position);
    }

    public ChessAIOneGoInstance(BitBoard position, int wtime, int btime, int winc, int binc) {
        super(position, wtime, btime, winc, binc);
    }

    @Override
    public void run() {
        ChessAIOneGoInstance t = this;
        //Time management
        if (this.wtime != 0) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            // your code here
                            t.stop();
                            System.out.println("bestmove " + t.bestMove);
                        }
                    }, (int) (10000)
            );

        }

        this.position.initBoard();
        for (int i = 1; i < 100; i++) {
            BitBoardMoveRating bbr = alphaBetaRoot(this.position, i, this.position.move ? 1 : -1);
            this.bestMove = bbr.bm;
            System.out.println("Depth " + i + " rating: " + bbr.rating);
        }
    }


    public static BitBoardMoveRating alphaBeta(BitBoard position,double rating, int depth, double alpha, double beta, int maximizing, int maxDepth) {
        nodes += 1;
        long t0=System.currentTimeMillis();
        position.initBoard();
        long t1=System.currentTimeMillis();
        profiler+=(t1-t0);
        if (position.status != ChessGameStatus.INGAME) {
            if (position.status == ChessGameStatus.DRAW) {
                return new BitBoardMoveRating(null, 0);
            } else if (position.status == ChessGameStatus.BLACKWIN) {
                return new BitBoardMoveRating(null, maximizing * -300 + maximizing * (maxDepth-depth));
            } else {
                return new BitBoardMoveRating(null, maximizing * 300 - maximizing * (maxDepth-depth));
            }
        }

        if (depth == 0) {
            return new BitBoardMoveRating(null, maximizing * rating);
        }

        //Move ordering
        HashMap<BitBoardMove,Double> boardRatings= new HashMap<>();
        for(BitBoardMove bbm: position.bm.legalMoves){
            boardRatings.put(bbm,BoardRating.getBoardRating(position.bm.legalFollowingGameStates.get(bbm)));
        }
        Collections.sort(position.bm.legalMoves, new BitBoardMoveComparator(boardRatings, maximizing == 1));
        double value = -100000.0;
        BitBoardMove best = null;
        for (BitBoardMove bbm : position.bm.legalMoves) {
            BitBoard next = position.bm.legalFollowingGameStates.get(bbm);
            BitBoard next2 = new BitBoard(next.whitePieces, next.blackPieces, next.enPassant, next.castleWK, next.castleWQ, next.castleBK, next.castleBQ, next.moveHistory, next.move);
            BitBoardMoveRating bbr = alphaBeta(next2,boardRatings.get(bbm), depth - 1, -1 * beta, -1 * alpha, -1 * maximizing, maxDepth);
            bbr.rating *= -1;
            value = value > bbr.rating ? value : bbr.rating;
            if (value > alpha) {
                alpha = value;
                best = bbm;
            }
            if (alpha > beta) {
                break;
            }
        }
        return new BitBoardMoveRating(best, value);
    }

    public static BitBoardMoveRating alphaBetaRoot(BitBoard position, int depth, int maximizing) {
        return alphaBeta(position,BoardRating.getBoardRating(position), depth, -1000, 1000, maximizing, depth);
    }
}
