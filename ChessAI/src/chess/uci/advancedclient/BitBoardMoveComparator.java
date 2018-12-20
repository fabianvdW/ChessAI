package chess.uci.advancedclient;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BitBoardMoveComparator implements Comparator<BitBoardMove> {
    Map<BitBoardMove, Double> map;
    boolean move;
    BitBoardMove pvresults;

    public BitBoardMoveComparator(Map<BitBoardMove, Double> ratings, boolean move, BitBoardMove pvresults) {
        this.map = ratings;
        this.move = move;
        this.pvresults = pvresults;
    }

    @Override
    public int compare(BitBoardMove o1, BitBoardMove o2) {
        boolean containso1 = false;
        boolean containso2 = false;
        if (o1.equals(pvresults)) {
            containso1 = true;
        } else if (o2.equals(pvresults)) {
            containso2 = true;
        }
        if (containso1) {
            return 1;
        } else if (containso2) {
            return -1;
        } else {
            double r1 = map.get(o1);
            double r2 = map.get(o2);
            if (r1 > r2) {
                if (move) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (r1 < r2) {
                if (move) {
                    return 1;
                } else {
                    return -1;
                }
            }
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
