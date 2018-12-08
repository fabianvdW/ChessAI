package chess;

import chess.pieces.ChessPiece;
import chess.pieces.Rook;

public class CastleMove extends ChessMove {
    Rook r;

    public CastleMove(int from, int to, ChessPiece moved, ChessPiece old, Rook r) {
        super(from, to, moved, old);
        this.r = r;
    }

    @Override
    public String toString() {
        return moved.representation + " " + ChessLogic.toStringPosition(from) + " -> " + ChessLogic.toStringPosition(to) + "(Castle with " + r.representation + " on: " + ChessLogic.toStringPosition(r.position) + ")";
    }
}
