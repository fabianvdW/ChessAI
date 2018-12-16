package tests;

import chess.bitboards.BitBoard;
import helpers.FENLoader;
import helpers.PerftDebugging;

public class TestPerft {
    public static void main(String[] args) {
        BitBoard bb;

        bb = FENLoader.getBitBoardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ");
        assert (PerftDebugging.perft(bb, 1) == 20);
        System.out.println("Test 1 passed!");
        assert (PerftDebugging.perft(bb, 2) == 400);
        System.out.println("Test 2 passed!");
        assert (PerftDebugging.perft(bb, 3) == 8902);
        System.out.println("Test 3 passed!");
        assert (PerftDebugging.perft(bb, 4) == 197281);
        System.out.println("Test 4 passed!");
        assert (PerftDebugging.perft(bb, 5) == 4865609);
        System.out.println("Test 5 passed!");

        bb = FENLoader.getBitBoardFromFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        assert (PerftDebugging.perft(bb, 1) == 48);
        System.out.println("Test 6 passed!");
        assert (PerftDebugging.perft(bb, 2) == 2039);
        System.out.println("Test 7 passed!");
        assert (PerftDebugging.perft(bb, 3) == 97862);
        System.out.println("Test 8 passed!");
        assert (PerftDebugging.perft(bb, 4) == 4085603);
        System.out.println("Test 9 passed!");

        bb = FENLoader.getBitBoardFromFen("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        assert (PerftDebugging.perft(bb, 1) == 14);
        System.out.println("Test 10 passed!");
        assert (PerftDebugging.perft(bb, 2) == 191);
        System.out.println("Test 11 passed!");
        assert (PerftDebugging.perft(bb, 3) == 2812);
        System.out.println("Test 12 passed!");
        assert (PerftDebugging.perft(bb, 4) == 43238);
        System.out.println("Test 13 passed!");
        assert (PerftDebugging.perft(bb, 5) == 674624);
        System.out.println("Test 14 passed!");

        bb = FENLoader.getBitBoardFromFen("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        assert (PerftDebugging.perft(bb, 1) == 6);
        System.out.println("Test 15 passed!");
        assert (PerftDebugging.perft(bb, 2) == 264);
        System.out.println("Test 16 passed!");
        assert (PerftDebugging.perft(bb, 3) == 9467);
        System.out.println("Test 17 passed!");
        assert (PerftDebugging.perft(bb, 4) == 422333);
        System.out.println("Test 18 passed!");

        bb = FENLoader.getBitBoardFromFen("r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1");
        assert (PerftDebugging.perft(bb, 1) == 6);
        System.out.println("Test 19 passed!");
        assert (PerftDebugging.perft(bb, 2) == 264);
        System.out.println("Test 20 passed!");
        assert (PerftDebugging.perft(bb, 3) == 9467);
        System.out.println("Test 21 passed!");
        assert (PerftDebugging.perft(bb, 4) == 422333);
        System.out.println("Test 22 passed!");

        bb = FENLoader.getBitBoardFromFen("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        assert (PerftDebugging.perft(bb, 1) == 44);
        System.out.println("Test 23 passed!");
        assert (PerftDebugging.perft(bb, 2) == 1486);
        System.out.println("Test 24 passed!");
        assert (PerftDebugging.perft(bb, 3) == 62379);
        System.out.println("Test 25 passed!");
        assert (PerftDebugging.perft(bb, 4) == 2103487);
        System.out.println("Test 26 passed!");

        bb = FENLoader.getBitBoardFromFen("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
        assert (PerftDebugging.perft(bb, 1) == 46);
        System.out.println("Test 27 passed!");
        assert (PerftDebugging.perft(bb, 2) == 2079);
        System.out.println("Test 28 passed!");
        assert (PerftDebugging.perft(bb, 3) == 89890);
        System.out.println("Test 29 passed!");
        assert (PerftDebugging.perft(bb, 4) == 3894594);
        System.out.println("Test 30 passed!");

        bb = FENLoader.getBitBoardFromFen("8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1");
        assert (PerftDebugging.perft(bb, 4) == 23527);
        System.out.println("Test 31 passed!");

        bb = FENLoader.getBitBoardFromFen("8/k1P5/8/1K6/8/8/8/8 w - - 0 1");
        assert (PerftDebugging.perft(bb, 7) == 567584);
        System.out.println("Test 32 passed!");

        bb = FENLoader.getBitBoardFromFen("K1k5/8/P7/8/8/8/8/8 w - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 2217);
        System.out.println("Test 33 passed!");

        bb = FENLoader.getBitBoardFromFen("8/P1k5/K7/8/8/8/8/8 w - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 92683);
        System.out.println("Test 34 passed!");

        bb = FENLoader.getBitBoardFromFen("4k3/1P6/8/8/8/8/K7/8 w - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 217342);
        System.out.println("Test 35 passed!");

        bb = FENLoader.getBitBoardFromFen("8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1");
        assert (PerftDebugging.perft(bb, 5) == 1004658);
        System.out.println("Test 36 passed!");

        bb = FENLoader.getBitBoardFromFen("2K2r2/4P3/8/8/8/8/8/3k4 w - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 3821001);
        System.out.println("Test 37 passed!");

        bb = FENLoader.getBitBoardFromFen("r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1");
        assert (PerftDebugging.perft(bb, 4) == 1720476);
        System.out.println("Test 38 passed!");

        bb = FENLoader.getBitBoardFromFen("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1");
        assert (PerftDebugging.perft(bb, 4) == 1274206);
        System.out.println("Test 39 passed!");

        bb = FENLoader.getBitBoardFromFen("3k4/8/8/8/8/8/8/R3K3 w Q - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 803711);
        System.out.println("Test 40 passed!");

        bb = FENLoader.getBitBoardFromFen("5k2/8/8/8/8/8/8/4K2R w K - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 661072);
        System.out.println("Test 41 passed!");

        bb = FENLoader.getBitBoardFromFen("8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1");
        assert (PerftDebugging.perft(bb, 6) == 1440467);
        System.out.println("Test 42 passed!");

        bb = FENLoader.getBitBoardFromFen("8/8/4k3/8/2p5/8/B2P2K1/8 w - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 1015133);
        System.out.println("Test 43 passed!");

        bb = FENLoader.getBitBoardFromFen("3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1");
        assert (PerftDebugging.perft(bb, 6) == 1134888);
        System.out.println("Test 44 passed!");

        bb = FENLoader.getBitBoardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ");
        long t0=System.currentTimeMillis();
        assert(PerftDebugging.perftRoot(bb,6)==119060324);
        long t1=System.currentTimeMillis();
        System.out.println("NPS: "+119060324/((t1-t0)/1000.0));
    }
}
