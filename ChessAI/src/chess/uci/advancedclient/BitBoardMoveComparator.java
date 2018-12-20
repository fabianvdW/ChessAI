package chess.uci.advancedclient;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;

import java.util.Comparator;
import java.util.Map;

public class BitBoardMoveComparator implements Comparator<BitBoardMove> {
    Map<BitBoardMove,Double> map;
    boolean move;
    public BitBoardMoveComparator(Map<BitBoardMove,Double> ratings,boolean move){
        this.map=ratings;
        this.move=move;
    }
    @Override
    public int compare(BitBoardMove o1, BitBoardMove o2) {
        double r1= map.get(o1);
        double r2= map.get(o2);
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
