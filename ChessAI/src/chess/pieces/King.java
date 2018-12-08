package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    public static int[] minimalUnit = {9, 8, 7, 1, -1, -7, -8, -9};

    public King(ChessColor color, int position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2654";
        } else {
            this.representation = "\u265A";
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
        //Check Castle
        if (this.moves == 0 && !ChessLogic.isPositionThreatened(this.position, null, b, enemyColor)) {
            for (int i = 0; i < 2; i++) {
                ChessPiece x = null;
                int thisY=(this.position-this.position%8);
                if (i == 0) {
                    x = b.getChessPiece(thisY);
                } else {
                    x = b.getChessPiece(thisY);
                }
                if (x instanceof Rook) {
                    Rook r = (Rook) x;
                    if (r.moves == 0) {
                        int xIncrementor = i == 0 ? -1 : 1;
                        int pos1 = this.position+xIncrementor;
                        int pos2 = this.position+2*xIncrementor;
                        //Fields free
                        if (b.getChessPiece(pos1) == null && b.getChessPiece(pos2) == null) {
                            if (!ChessLogic.isPositionThreatened(pos1, null, b, enemyColor) && !ChessLogic.isPositionThreatened(pos2, null, b, enemyColor)) {
                                result.add(new CastleMove(this.position, pos2, this, null, r));
                            }
                        }
                    }
                }
            }
        }
        //Check normal Moves
        for (int i = 0; i < King.minimalUnit.length; i++) {
            int cv = King.minimalUnit[i];
            int cp = this.position+cv;
            if (!ChessLogic.isValidPosition(cp)) {
                continue;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(this.position, cp, this, cPiece);
            if (cPiece != null && cPiece.color != enemyColor) {
                continue;
            }
            if (!pinFlag && ChessLogic.isPositionThreatened(cm.to, cm, b, enemyColor)) {
                continue;
            }
            result.add(cm);
        }


        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof King) {
            King b = (King) o;
            return b.position==this.position;
        }
        return false;
    }
}
