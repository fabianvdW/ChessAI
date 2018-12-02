package Chess.pieces;

import Chess.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
    public Knight(ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2658";
        } else {
            this.representation = "\u265E";
        }
    }


    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        //TODO Pinning

        List<ChessMove> result = new ArrayList<>();

        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        for (int i = 0; i < 8; i++) {
            int xIncrementor = 0;
            int yIncrementor = 0;
            switch (i) {
                case 0:
                    xIncrementor = 2;
                    yIncrementor = 1;
                    break;
                case 1:
                    xIncrementor = 2;
                    yIncrementor = -1;
                    break;
                case 2:
                    xIncrementor = -2;
                    yIncrementor = 1;
                    break;
                case 3:
                    xIncrementor = -2;
                    yIncrementor = -1;
                    break;
                case 4:
                    yIncrementor = 2;
                    xIncrementor = 1;
                    break;
                case 5:
                    yIncrementor = 2;
                    xIncrementor = -1;
                    break;
                case 6:
                    yIncrementor = -2;
                    xIncrementor = 1;
                    break;
                case 7:
                    yIncrementor = -2;
                    xIncrementor = -1;
                    break;
            }
            int xCoordinate = this.position.getX() + xIncrementor;
            int yCoordinate = this.position.getY() + yIncrementor;
            if (!(ChessLogic.isValidX(xCoordinate) && ChessLogic.isValidY(yCoordinate))) {
                continue;
            }

            ChessPosition cp = new ChessPosition(xCoordinate, yCoordinate);
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(this.position.clone(), cp, this, cPiece);
            if (!pinFlag && ChessLogic.isPinned(cm, b)) {
                continue;
            }
            if (cPiece == null || cPiece.color == enemyColor) {
                result.add(cm);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Knight) {
            Knight b = (Knight) o;
            return b.position.equals(this.position);
        }
        return false;
    }

}
