package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(ChessColor color, ChessPosition position, ChessBoard board) {
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
        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        //Check Castle
        if (this.moves == 0 && !ChessLogic.isPositionThreatened(this.position, null, b, enemyColor)) {
            for (int i = 0; i < 2; i++) {
                ChessPiece x = null;
                if (i == 0) {
                    x = b.getChessPiece(new ChessPosition(0, this.position.getY()));
                } else {
                    x = b.getChessPiece(new ChessPosition(7, this.position.getY()));
                }
                if (x instanceof Rook) {
                    Rook r = (Rook) x;
                    if (r.moves == 0) {
                        int xIncrementor = i == 0 ? -1 : 1;
                        ChessPosition pos1 = new ChessPosition(this.position.getX() + xIncrementor, this.position.getY());
                        ChessPosition pos2 = new ChessPosition(this.position.getX() + 2 * xIncrementor, this.position.getY());
                        //Fields free
                        if (b.getChessPiece(pos1) == null && b.getChessPiece(pos2) == null) {
                            if (!ChessLogic.isPositionThreatened(pos1, null, b, enemyColor) && !ChessLogic.isPositionThreatened(pos2, null, b, enemyColor)) {
                                result.add(new CastleMove(this.position.clone(), pos2, this, null, r));
                            }
                        }
                    }
                }
            }
        }
        //Check normal Moves
        for (int i = 0; i < 8; i++) {
            int xIncrementor = 0;
            int yIncrementor = 0;
            switch (i) {
                case 0:
                    xIncrementor = 1;
                    yIncrementor = 1;
                    break;
                case 1:
                    xIncrementor = 1;
                    yIncrementor = 0;
                    break;
                case 2:
                    xIncrementor = 1;
                    yIncrementor = -1;
                    break;
                case 3:
                    xIncrementor = 0;
                    yIncrementor = 1;
                    break;
                case 4:
                    xIncrementor = 0;
                    yIncrementor = -1;
                    break;
                case 5:
                    xIncrementor = -1;
                    yIncrementor = 1;
                    break;
                case 6:
                    xIncrementor = -1;
                    yIncrementor = 0;
                    break;
                case 7:
                    xIncrementor = -1;
                    yIncrementor = -1;
                    break;

            }
            int xCoordinate = this.position.getX();
            int yCoordinate = this.position.getY();
            xCoordinate += xIncrementor;
            yCoordinate += yIncrementor;
            if (!(ChessLogic.isValidX(xCoordinate) && ChessLogic.isValidY(yCoordinate))) {
                continue;
            }
            ChessPosition cp = new ChessPosition(xCoordinate, yCoordinate);
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(this.position.clone(), cp, this, cPiece);
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
            return b.position.equals(this.position);
        }
        return false;
    }
}
