package Chess;

import Chess.pieces.ChessPiece;
import Chess.pieces.King;

import java.util.List;

public class ChessLogic {
    public static boolean isValidX(int x) {
        return x >= 0 && x < 8;
    }

    public static boolean isValidY(int y) {
        return y >= 0 && y < 8;
    }

    public static boolean isThreatened(ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
        List<ChessPiece> enemyPieces = (enemyColor == ChessColor.WHITE) ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        ChessPosition cpNew= cm.to;

        ChessPiece chessPiece = cb.getChessPiece(cpNew);
        applyChessMoveToBoardWithoutLogic(cm,cb);
        for (int i = 0; i < enemyPieces.size(); i++) {
            ChessPiece cPiece = enemyPieces.get(i);
            if (cPiece.onBoard) {
                if (cPiece instanceof King) {
                    if (Math.abs(cPiece.position.getX() - cpNew.getX()) <= 1 && Math.abs(cPiece.position.getY() - cpNew.getY()) <= 1) {
                        reverseChessMoveToBoardWithoutLogic(cm,cb);
                        return true;
                    }
                } else {
                    for (ChessMove cMove : cPiece.getPossibleMoves(cb)) {
                        ChessPosition cPos= cMove.to;
                        if (cPos.equals(cpNew)) {
                            reverseChessMoveToBoardWithoutLogic(cm,cb);
                            return true;
                        }
                    }
                }
            }
        }
        reverseChessMoveToBoardWithoutLogic(cm,cb);
        return false;
    }

    public static void applyChessMoveToBoardWithoutLogic(ChessMove cm, ChessBoard cb){
        cb.setChessPiece(cm.from,null);
        cb.setChessPiece(cm.to,cm.moved);
    }

    public static void reverseChessMoveToBoardWithoutLogic(ChessMove cm, ChessBoard cb){
        cb.setChessPiece(cm.from,cm.moved);
        cb.setChessPiece(cm.to,cm.old);
    }
}
