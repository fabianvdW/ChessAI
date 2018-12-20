package tests;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;
import chess.uci.advancedclient.BitBoardMoveRating;
import chess.uci.advancedclient.ChessAIOneGoInstance;
import helpers.FENLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAlphaBetaPruning {

    public static void main(String[] args) {

        //Depth 5 of starting position are 5072213 nodes
        // 207265
        BitBoard bb = FENLoader.getBitBoardFromFen("N5K1/8/5k2/8/8/2N5/8/5N2 w - - 0 1");
        Map<Integer,Map<BitBoard,BitBoardMove>> pvresults= new HashMap<>();
        for (int i = 1; i < 20; i++) {
            List<BitBoardMoveRating> bbr = ChessAIOneGoInstance.alphaBetaRoot(bb, i, 1,pvresults);
            for(int j=1;j<=i;j++){
                Map<BitBoard,BitBoardMove> existing=pvresults.getOrDefault(j,new HashMap<>());
                BitBoardMoveRating bbc= bbr.get(j);
                BitBoard before=bbr.get(j-1).bb;
                if(!existing.containsKey(before)) {
                    existing.put(before, bbc.bm);
                }
                pvresults.put(j,existing);
            }
            System.out.println("Depth " + i + " rating: " + bbr.get(0).rating);
        }
        /*for(int i: pvresults.keySet()){
            System.out.println("I: "+i);
            for(BitBoard cb: pvresults.get(i).keySet()){
                System.out.println(cb.toString()+" has :"+pvresults.get(i).get(cb));
            }
        }*/
        System.out.println(ChessAIOneGoInstance.nodes);
    }
}
