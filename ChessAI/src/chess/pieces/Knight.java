package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
    public static int[] minimalUnit = {17,15,10,6,-6,-10,-15,-17};

    public Knight(ChessColor color, int position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2658";
        } else {
            this.representation = "\u265E";
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
        for (int i = 0; i < Knight.minimalUnit.length; i++) {
            int cv = Knight.minimalUnit[i];
            int cp = this.position+cv;
            if (!ChessLogic.isValidPosition(cp)) {
                continue;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(this.position, cp, this, cPiece);
            if (cPiece != null && cPiece.color != enemyColor) {
                continue;
            }
            if (!pinFlag && ChessLogic.isPositionThreatened(myKing.position, cm, b, enemyColor)) {
                continue;
            }
            result.add(cm);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Knight) {
            Knight b = (Knight) o;
            return b.position==this.position;
        }
        return false;
    }

}
