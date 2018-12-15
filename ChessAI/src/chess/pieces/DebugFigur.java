package chess.pieces;

import chess.ChessBoard;
import chess.ChessColor;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.List;

public class DebugFigur extends ChessPiece {

    public DebugFigur(ChessPiece chessPiece, ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        this.representation = chessPiece.representation + "\u274C";
    }

    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
    @Override
    public int hashCode() {
        return (this.position.getX()+this.position.getY()*8)*(this.color==ChessColor.WHITE?2:1);
    }
}
