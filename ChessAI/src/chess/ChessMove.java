package chess;

import chess.pieces.ChessPiece;

public class ChessMove {
    public int from;
    public int to;
    public ChessPiece moved;
    public ChessPiece old;

    public ChessMove(int from, int to, ChessPiece moved, ChessPiece old) {
        this.from = from;
        this.to = to;
        this.moved = moved;
        this.old = old;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChessMove) {
            ChessMove cm = (ChessMove) o;
            return (cm.from==this.from && cm.to==this.to);
        }
        return false;
    }

    @Override
    public String toString() {
        return moved.representation + " " + ChessLogic.toStringPosition(this.from) + " -> " + ChessLogic.toStringPosition(this.to) + (old != null ? "Captures: " + old.representation : "");
    }

}
