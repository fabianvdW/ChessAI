package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessPosition;

import java.util.List;

public abstract class ChessPiece {
    public ChessColor color;
    public ChessPosition position;
    public String representation;
    public boolean onBoard;

    public ChessPiece(ChessColor color, ChessPosition position,ChessBoard board) {
        this.color=color;
        this.position=position;
        this.onBoard=true;
        board.board[this.position.getX()][this.position.getY()]=this;
    }

    public abstract List<ChessPosition> getPossibleMoves(ChessBoard b);
}
