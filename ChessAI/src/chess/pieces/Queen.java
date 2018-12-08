package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {
    public static ChessVector[] minimalUnit = {new ChessVector(0, 1), new ChessVector(1, 1), new ChessVector(1, 0), new ChessVector(1, -1), new ChessVector(0, -1), new ChessVector(-1, -1), new ChessVector(-1, 0), new ChessVector(-1, 1)};

    public Queen(ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2655";
        } else {
            this.representation = "\u265B";
        }
    }

    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        List<ChessMove> result = new ArrayList<>();
        if (b.initialized) {
            if (this.color == ChessColor.WHITE) {
                return b.WHITE_MOVES.getOrDefault(this, result);
            }else {
                return b.BLACK_MOVES.getOrDefault(this, result);
            }
        }
        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        ChessPiece myKing = this.color == ChessColor.WHITE ? b.WHITE_KING : b.BLACK_KING;
        for (int i = 0; i < Queen.minimalUnit.length; i++) {
            ChessVector cv = Queen.minimalUnit[i];
            result.addAll(ChessLogic.cycleThrough(b, this.position, cv, enemyColor, myKing, this, pinFlag));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Queen) {
            Queen b = (Queen) o;
            return b.position.equals(this.position);
        }
        return false;
    }
}
