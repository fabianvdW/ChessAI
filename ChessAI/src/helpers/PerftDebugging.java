package helpers;

import chess.bitboards.BitBoard;

public class PerftDebugging {


    public static int perftRoot(BitBoard bb, int depth){
        bb.initBoard();
        int count=0;
        for(BitBoard next: bb.bm.legalFollowingGameStates.values()){
            int res= perft(next,depth-1);
            System.out.println(next.moveHistory.get(next.moveHistory.size()-1)+" : "+res);
            count+=res;
        }
        return count;
    }
    public static int perft(BitBoard bb, int depth){
        if(depth==0){
            return 1;
        }else{
            bb.initBoard();
            int count=0;
            for(BitBoard next: bb.bm.legalFollowingGameStates.values()){
                count+=perft(next,depth-1);
            }
            return count;
        }
    }
}
