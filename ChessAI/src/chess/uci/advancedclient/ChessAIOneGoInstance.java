package chess.uci.advancedclient;

import chess.ChessGameStatus;
import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;
import chess.uci.UCIGoInstance;
import chess.uci.simpleclient.SimpleClientGoInstance;

import java.util.List;

public class ChessAIOneGoInstance extends UCIGoInstance {

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
        System.out.println("new AI v5");
        for (int i = 1; i < 100; i++) {
            BitBoardMoveRating bbr= alphaBeta(this.position,i,-1000,1000,this.position.move?1:-1);
            this.bestMove = bbr.bm;
            System.out.println("Depth "+i+" rating: "+bbr.rating);
        }
    }


    public static BitBoardMoveRating alphaBeta(BitBoard position, int depth, double alpha, double beta, int maximizing) {
        position.initBoard();
        if (depth == 0) {
            return new BitBoardMoveRating(null, maximizing*BoardRating.getBoardRating(position,depth));
        }
        if (position.status != ChessGameStatus.INGAME) {
            if (position.status == ChessGameStatus.DRAW) {
                return new BitBoardMoveRating(null, 0);
            } else if (position.status == ChessGameStatus.BLACKWIN) {
                return new BitBoardMoveRating(null, maximizing * -300-maximizing*(depth));
            } else {
                return new BitBoardMoveRating(null, maximizing * 300+maximizing*(depth));
            }
        }

        List<BitBoardMove> childNodes = position.bm.legalMoves;
        double value = -100000.0;
        BitBoardMove best = null;
        for (BitBoardMove bbm : childNodes) {
            BitBoard next= position.bm.legalFollowingGameStates.get(bbm);
            BitBoard next2=  new BitBoard(next.whitePieces,next.blackPieces,next.enPassant,next.castleWK,next.castleWQ,next.castleBK,next.castleBQ,next.moveHistory,next.move);
            BitBoardMoveRating bbr = alphaBeta(next2, depth - 1, -1 * beta, -1 * alpha, -1 * maximizing);
            bbr.rating*=-1;
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
}

class BitBoardMoveRating {
    BitBoardMove bm;
    double rating;

    public BitBoardMoveRating(BitBoardMove bm, double rating) {
        this.bm = bm;
        this.rating = rating;
    }
}
