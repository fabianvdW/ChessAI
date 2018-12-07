package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {
    public Rook(ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2656";
        } else {
            this.representation = "\u265C";
        }
    }

    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        //TODO Pinning

        List<ChessMove> result = new ArrayList<>();

        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        for (int i = 0; i < 4; i++) {
            int xIncrementor = 0;
            int yIncrementor = 0;
            switch (i) {
                case 0:
                    xIncrementor = 1;
                    break;
                case 1:
                    xIncrementor = -1;
                    break;
                case 2:
                    yIncrementor = 1;
                    break;
                case 3:
                    yIncrementor = -1;
            }
            int xCoordinate = this.position.getX();
            int yCoordinate = this.position.getY();
            ChessPosition cp = null;
            do {
                xCoordinate += xIncrementor;
                yCoordinate += yIncrementor;
                if (!(ChessLogic.isValidX(xCoordinate) && ChessLogic.isValidY(yCoordinate))) {
                    break;
                }
                cp = new ChessPosition(xCoordinate, yCoordinate);
                ChessPiece cPiece = b.getChessPiece(cp);
                ChessMove cm = new ChessMove(this.position.clone(), cp, this, cPiece);
                if (cPiece != null && cPiece.color != enemyColor) {
                    break;
                }
                if (!pinFlag && ChessLogic.isPinned(cm, b)) {
                    if(cPiece!=null &&cPiece.color==enemyColor){
                        break;
                    }
                    continue;
                }
                if (cPiece == null) {
                    result.add(cm);
                } else if (cPiece.color == enemyColor) {
                    result.add(cm);
                    break;
                }
            } while (true);

        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Rook) {
            Rook b = (Rook) o;
            return b.position.equals(this.position);
        }
        return false;
    }

}
