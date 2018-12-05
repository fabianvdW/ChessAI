package Chess;

import Chess.pieces.ChessPiece;
import Chess.pieces.Rook;

public class CastleMove extends ChessMove {
    Rook r;

    public CastleMove(ChessPosition from, ChessPosition to, ChessPiece moved, ChessPiece old, Rook r) {
        super(from, to, moved, old);
        this.r = r;
    }

    @Override
    public String toString() {
        return moved.representation + " " + from.toString() + " -> " + to.toString() + "(Castle with " + r.representation + " on: " + r.position.toString() + ")";
    }
}
