package tests;

import chess.bitboards.BitBoard;
import chess.uci.advancedclient.ChessAIOneGoInstance;
import helpers.FENLoader;

public class TestAlphaBetaPruning {

    public static void main(String[] args){

        //Depth 5 of starting position are 5072213 nodes
        // 207265
        BitBoard bb = FENLoader.getBitBoardFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ");
        long t0=System.currentTimeMillis();
        ChessAIOneGoInstance.alphaBeta(bb,6,-1000,1000,1);
        long t1=System.currentTimeMillis();
        System.out.println("Time: "+(t1-t0));
        System.out.println("Profiler: "+ChessAIOneGoInstance.profiler);
        System.out.println(ChessAIOneGoInstance.nodes);
    }
}
