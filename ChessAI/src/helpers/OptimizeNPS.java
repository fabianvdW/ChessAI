package helpers;

import chess.bitboards.BitBoard;

public class OptimizeNPS {
    public static void main(String[] args) {
        BitBoard bb = FENLoader.getBitBoardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ");
        long t0 = System.currentTimeMillis();
        assert (PerftDebugging.perftRoot(bb, 6)!=0);
        long t1 = System.currentTimeMillis();
        System.out.println("NPS: " + PerftDebugging.nodes / ((t1 - t0) / 1000.0));
    }
}
