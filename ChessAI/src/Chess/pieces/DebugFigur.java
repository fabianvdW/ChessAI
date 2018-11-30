package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessPosition;

import java.util.List;

public class DebugFigur extends ChessPiece{

    public DebugFigur(ChessPiece chessPiece,ChessColor color, ChessPosition position, ChessBoard board){
        super(color,position,board);
        this.representation=chessPiece.representation+"\u274C";
    }
    @Override
    public List<ChessPosition> getPossibleMoves(ChessBoard b) {
        return null;
    }
}
