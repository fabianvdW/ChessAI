package chess.uci.advancedclient;

import chess.ChessGameStatus;
import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;
import chess.bitboards.BitBoardMoves;
import chess.uci.UCIGoInstance;
import chess.uci.simpleclient.SimpleClientGoInstance;

import java.util.*;

public class ChessAIOneGoInstance extends UCIGoInstance {
    public static int nodes;
    public static int profiler;

    public ChessAIOneGoInstance(BitBoard position) {
        super(position);
    }

    public ChessAIOneGoInstance(BitBoard position, int wtime, int btime, int winc, int binc) {
        super(position, wtime, btime, winc, binc);
    }

    public void endGoInstance() {
        System.out.println("bestmove " + this.bestMove);
        this.stop();
    }

    @Override
    public void run() {
        int mytime = this.position.move ? this.wtime : this.btime;
        int myinc = this.position.move ? this.winc : this.binc;
        double time = mytime / 30.0 + myinc - 10;
        ChessAIOneGoInstance t = this;
        //Time management
        Timer timer = new java.util.Timer();
        timer.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        t.endGoInstance();
                    }
                }, (int) (time)
        );


        Map<Integer, Map<BitBoard, BitBoardMove>> pvresults = new HashMap<>();
        for (int i = 1; i < 100; i++) {
            List<BitBoardMoveRating> bbr = ChessAIOneGoInstance.alphaBetaRoot(position, i, position.move ? 1 : -1, pvresults);
            this.bestMove = bbr.get(1).bm;
            if (bbr.size() < i + 1) {
                //Found mate
                timer.cancel();
                this.endGoInstance();
            }
            for (int j = 1; j <= i; j++) {
                Map<BitBoard, BitBoardMove> existing = pvresults.getOrDefault(j, new HashMap<>());
                BitBoardMoveRating bbc = bbr.get(j);
                BitBoard before = bbr.get(j - 1).bb;
                if (!existing.containsKey(before)) {
                    existing.put(before, bbc.bm);
                }
                pvresults.put(j, existing);
            }
            System.out.println("Depth " + i + " rating: " + bbr.get(0).rating);
        }
    }


    public static List<BitBoardMoveRating> alphaBeta(BitBoard position, double rating, int depth, double alpha, double beta, int maximizing, int maxDepth, BitBoardMove currMove, Map<Integer, Map<BitBoard, BitBoardMove>> pvresults, boolean capturingMove) {
        nodes += 1;
        position.initBoard();
        ArrayList<BitBoardMoveRating> ratings = new ArrayList<>();

        if (position.status != ChessGameStatus.INGAME) {
            if (position.status == ChessGameStatus.DRAW) {
                ratings.add(new BitBoardMoveRating(currMove, 0, position));
            } else if (position.status == ChessGameStatus.BLACKWIN) {
                ratings.add(new BitBoardMoveRating(currMove, maximizing * -300 + maximizing * (maxDepth - depth), position));
            } else {
                ratings.add(new BitBoardMoveRating(currMove, maximizing * 300 - maximizing * (maxDepth - depth), position));
            }
            return ratings;
        }

        int amountOfPieces = position.getAmountOfWhitePieces() + position.getAmountOfBlackPieces();
        if (depth <= 0 & !capturingMove) {
            ratings.add(new BitBoardMoveRating(currMove, maximizing * rating, position));
            return ratings;
        }

        //Move ordering
        HashMap<BitBoardMove, Double> boardRatings = new HashMap<>();
        for (BitBoardMove bbm : position.bm.legalMoves) {
            boardRatings.put(bbm, BoardRating.getBoardRating(position.bm.legalFollowingGameStates.get(bbm)));
        }
        //Iterative deepening results
        Map<BitBoard, BitBoardMove> depthResults = pvresults.getOrDefault(maxDepth - depth + 1, new HashMap<>());
        BitBoardMove boardResults = depthResults.getOrDefault(position, null);
        Collections.sort(position.bm.legalMoves, new BitBoardMoveComparator(boardRatings, maximizing == 1, boardResults));

        if (depth <= 0 && capturingMove) {
            //Quiesence
            //Needs better move ordering
            //That sorts by captures of value.
            //Check out best Move and all other capturing Moves
            double value = -100000.0;
            List<BitBoardMoveRating> bestPv = null;
            int count = 0;
            for (BitBoardMove bbm : position.bm.legalMoves) {
                BitBoard next = position.bm.legalFollowingGameStates.get(bbm);
                BitBoard next2 = new BitBoard(next.whitePieces, next.blackPieces, next.enPassant, next.castleWK, next.castleWQ, next.castleBK, next.castleBQ, next.moveHistory, next.move);
                boolean capture = false;
                if ((next2.getAmountOfBlackPieces() + next2.getAmountOfWhitePieces()) != amountOfPieces) {
                    capture = true;
                }
                if (capture || count == 0) {
                    List<BitBoardMoveRating> followingBestMoves = alphaBeta(next2, boardRatings.get(bbm), depth - 1, -1 * beta, -1 * alpha, -1 * maximizing, maxDepth, bbm, pvresults, capture);
                    for (BitBoardMoveRating bbr : followingBestMoves) {
                        bbr.rating *= -1;
                    }
                    BitBoardMoveRating bbr = followingBestMoves.get(0);
                    value = value > bbr.rating ? value : bbr.rating;
                    if (value > alpha) {
                        alpha = value;
                        bestPv = followingBestMoves;
                    }
                    if (alpha > beta) {
                        break;
                    }
                }
                count++;
            }
            if (bestPv == null) {
                ratings.add(new BitBoardMoveRating(currMove, maximizing * rating, position));
                return ratings;
            }
            ratings.add(new BitBoardMoveRating(currMove, value, position));
            ratings.addAll(bestPv);
            return ratings;
        }


        double value = -100000.0;
        List<BitBoardMoveRating> bestPv = null;
        for (BitBoardMove bbm : position.bm.legalMoves) {
            BitBoard next = position.bm.legalFollowingGameStates.get(bbm);
            BitBoard next2 = new BitBoard(next.whitePieces, next.blackPieces, next.enPassant, next.castleWK, next.castleWQ, next.castleBK, next.castleBQ, next.moveHistory, next.move);
            boolean capture = false;
            if (next2.getAmountOfBlackPieces() + next2.getAmountOfWhitePieces() != amountOfPieces) {
                capture = true;
            }
            List<BitBoardMoveRating> followingBestMoves = alphaBeta(next2, boardRatings.get(bbm), depth - 1, -1 * beta, -1 * alpha, -1 * maximizing, maxDepth, bbm, pvresults, capture);
            for (BitBoardMoveRating bbr : followingBestMoves) {
                bbr.rating *= -1;
            }
            BitBoardMoveRating bbr = followingBestMoves.get(0);
            value = value > bbr.rating ? value : bbr.rating;
            if (value > alpha) {
                alpha = value;
                bestPv = followingBestMoves;
            }
            if (alpha > beta) {
                break;
            }
        }
        ratings.add(new BitBoardMoveRating(currMove, value, position));
        if (bestPv != null) {
            ratings.addAll(bestPv);
        }
        return ratings;
    }

    public static List<BitBoardMoveRating> alphaBetaRoot(BitBoard position, int depth, int maximizing, Map<Integer, Map<BitBoard, BitBoardMove>> pvresults) {
        return alphaBeta(position, BoardRating.getBoardRating(position), depth, -1000, 1000, maximizing, depth, null, pvresults, false);
    }
}

