package chess.uci.advancedclient;

import chess.bitboards.BitBoardMove;

public class BitBoardMoveRating {
    public BitBoardMove bm;
    public double rating;

    public BitBoardMoveRating(BitBoardMove bm, double rating) {
        this.bm = bm;
        this.rating = rating;
    }
}
