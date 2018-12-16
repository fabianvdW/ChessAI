package helpers;

import chess.bitboards.BitBoard;

public class PerftDebugging {


    public static int perftRoot(BitBoard bb, int depth){
        bb.initBoard();
        int count=0;
        for(BitBoard next: bb.bm.legalFollowingGameStates.values()){
            System.gc();
            BitBoard next2=  new BitBoard(next.whitePieces,next.blackPieces,next.enPassant,next.castleWK,next.castleWQ,next.castleBK,next.castleBQ,next.moveHistory,next.move);
            int res= perft(next2,depth-1);
            System.out.println(next2.moveHistory.get(next2.moveHistory.size()-1)+" : "+res);
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
