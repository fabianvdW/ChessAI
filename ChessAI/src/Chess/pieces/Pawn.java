package Chess.pieces;

import Chess.*;


import java.util.*;

public class Pawn extends ChessPiece {
    public Pawn(ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2659";
        } else {
            this.representation = "\u265F";
        }
    }


    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        //TODO Pinning
        List<ChessMove> result = new ArrayList<>();

        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        int incrementor = this.color == ChessColor.WHITE ? -1 : 1;
        //2 vorrücken
        for (int i = 0; i < 4; i++) {
            ChessPosition newPosition = null;
            int newX = this.position.getX();
            int newY = this.position.getY();
            switch (i) {
                case 0:
                    newY += incrementor;
                    break;
                case 1:
                    newY += 2 * incrementor;
                    break;
                case 2:
                    //Diagonal schlagen links
                    newX += 1;
                    newY += incrementor;
                    break;
                case 3:
                    newX -= 1;
                    newY += incrementor;
                    break;
            }
            if (ChessLogic.isValidX(newX) && ChessLogic.isValidX(newY)) {
                ChessPosition cp = new ChessPosition(newX, newY);
                ChessPiece cpPiece = b.getChessPiece(cp);
                ChessMove cm = new ChessMove(this.position.clone(), cp, this, cpPiece);
                if (!pinFlag && ChessLogic.isPinned(cm, b)) {
                    continue;
                }
                if (i == 0 || i == 1) {
                    if (cpPiece == null && (i != 1 || b.getChessPiece(new ChessPosition(this.position.getX(), this.color == ChessColor.WHITE ? 5 : 2)) == null && this.position.getY() == (this.color == ChessColor.WHITE ? 6 : 1))) {
                        result.add(cm);
                    }
                } else {
                    if (cpPiece != null && cpPiece.color == enemyColor) {
                        result.add(cm);
                    } else if (cpPiece == null) {
                        int xInc = i == 2 ? 1 : -1;
                        int cp2X = cp.getX();
                        int cp2Y = cp.getY() - incrementor;
                        if (ChessLogic.isValidX(cp.getX() + xInc) && ChessLogic.isValidY(cp2Y)) {
                            ChessPiece cP2 = b.getChessPiece(new ChessPosition(cp2X, cp2Y));
                            if (cP2 instanceof Pawn && cP2.color == enemyColor && cP2.moves == 1 && (this.color == ChessColor.WHITE ? this.position.getY() == 3 : this.position.getY() == 4)) {
                                cm.old = cP2;
                                result.add(cm);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pawn) {
            Pawn b = (Pawn) o;
            return b.position.equals(this.position);
        }
        return false;
    }

}
