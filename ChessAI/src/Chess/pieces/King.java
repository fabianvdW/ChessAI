package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessLogic;
import Chess.ChessPosition;

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
    public List<ChessPosition> getPossibleMoves(ChessBoard b) {
        //TODO Pinning

        List<ChessPosition> result = new ArrayList<>();

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
            if(ChessLogic.isThreatened(cp,b,enemyColor)){
                continue;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            if (cPiece == null||cPiece.color == enemyColor) {
                result.add(cp);
            }

            //TODO Bedrohungen des Königs

        }
        return result;
    }
}
