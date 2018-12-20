package chess.uci.advancedclient;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;

public class BitBoardMoveRating {
    public BitBoardMove bm;
    public BitBoard bb;
    public double rating;

    public BitBoardMoveRating(BitBoardMove bm, double rating,BitBoard bb) {
        this.bm = bm;
        this.rating = rating;
        this.bb=bb;
    }
}
