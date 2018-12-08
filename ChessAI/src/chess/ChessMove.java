package chess;

import chess.pieces.ChessPiece;

public class ChessMove {
    public ChessPosition from;
    public ChessPosition to;
    public ChessPiece moved;
    public ChessPiece old;

    public ChessMove(ChessPosition from, ChessPosition to, ChessPiece moved, ChessPiece old) {
        this.from = from;
        this.to = to;
        this.moved = moved;
        this.old = old;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChessMove) {
            ChessMove cm = (ChessMove) o;
            return (cm.from.equals(this.from) && cm.to.equals(this.to));
        }
        return false;
    }

    @Override
    public String toString() {
        return moved.representation + " " + from.toString() + " -> " + to.toString() + (old != null ? "Captures: " + old.representation : "");
    }

}
