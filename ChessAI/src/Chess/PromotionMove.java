package Chess;

import Chess.pieces.ChessPiece;

public class PromotionMove extends ChessMove {
    PromotionUnit pm;
    public PromotionMove(ChessPosition from, ChessPosition to, ChessPiece moved, ChessPiece old,PromotionUnit pm) {
        super(from, to, moved, old);
        this.pm=pm;
    }
}
