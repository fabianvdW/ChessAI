package Chess.pieces;

import Chess.*;

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

    public King(ChessColor color, ChessPosition position, boolean onBoard) {
        super(color, position, onBoard);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2654";
        } else {
            this.representation = "\u265A";
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
            ChessPosition cp = null;
            xCoordinate += xIncrementor;
            yCoordinate += yIncrementor;
            if (!(ChessLogic.isValidX(xCoordinate) && ChessLogic.isValidY(yCoordinate))) {
                continue;
            }
            cp = new ChessPosition(xCoordinate, yCoordinate);
            ChessMove cm = new ChessMove(this.position, cp, this, b.getChessPiece(cp));
            if (!pinFlag && ChessLogic.isThreatened(cm, b, enemyColor)) {
                continue;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            if (cPiece == null || cPiece.color == enemyColor) {
                result.add(cm);
            }

            //TODO Bedrohungen des Königs

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

    @Override
    public King clone() {
        return new King(this.color, this.position.clone(), this.onBoard);
    }
}
