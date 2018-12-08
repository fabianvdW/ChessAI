package chess.pieces;

import chess.ChessBoard;
import chess.ChessColor;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.List;

public abstract class ChessPiece {
    public ChessColor color;
    public ChessPosition position;
    public String representation;
    public boolean onBoard;
    public int moves;

    public ChessPiece(ChessColor color, ChessPosition position, ChessBoard board) {
        this.color = color;
        this.position = position;
        this.onBoard = true;
        board.setChessPiece(this.position, this);
    }

    public abstract List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag);

    @Override
    public abstract boolean equals(Object o);

}
