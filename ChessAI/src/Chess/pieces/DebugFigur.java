package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessMove;
import Chess.ChessPosition;

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
}
