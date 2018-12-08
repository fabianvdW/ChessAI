package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {
    public static int[] minimalUnit = {8,1,-1,-8};

    public Rook(ChessColor color, int position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2656";
        } else {
            this.representation = "\u265C";
        }
    }

    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        List<ChessMove> result = new ArrayList<>();
        if (b.initialized) {
            if (this.color == ChessColor.WHITE) {
                return b.WHITE_MOVES.getOrDefault(this, result);
            } else {
                return b.BLACK_MOVES.getOrDefault(this, result);
            }
        }
        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        ChessPiece myKing = this.color == ChessColor.WHITE ? b.WHITE_KING : b.BLACK_KING;
        for (int i = 0; i < Rook.minimalUnit.length; i++) {
            int cv = Rook.minimalUnit[i];
            result.addAll(ChessLogic.cycleThrough(b, this.position, cv, enemyColor, myKing, this, pinFlag));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Rook) {
            Rook b = (Rook) o;
            return b.position==this.position;
        }
        return false;
    }

}
