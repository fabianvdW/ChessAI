package chess.uci.advancedclient;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;

import java.util.Comparator;
import java.util.Map;

public class BitBoardMoveComparator implements Comparator<BitBoardMove> {
    Map<BitBoardMove,BitBoard> map;
    boolean move;
    public BitBoardMoveComparator(Map<BitBoardMove,BitBoard> moves,boolean move){
        this.map=moves;
        this.move=move;
    }
    @Override
    public int compare(BitBoardMove o1, BitBoardMove o2) {
        double r1= BoardRating.getBoardRating(map.get(o1),0);
        double r2= BoardRating.getBoardRating(map.get(o2),0);
        if(r1>r2){
            if(move) {
                return -1;
            }else{
                return 1;
            }
        }else if(r1<r2){
            if(move) {
                return 1;
            }else{
                return -1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
