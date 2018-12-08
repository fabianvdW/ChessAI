package chess;

import chess.pieces.ChessPiece;

public class PromotionMove extends ChessMove {
    PromotionUnit pm;
    public PromotionMove(int from, int to, ChessPiece moved, ChessPiece old,PromotionUnit pm) {
        super(from, to, moved, old);
        this.pm=pm;
    }
}
