package Chess;

import Chess.pieces.ChessPiece;
import Chess.pieces.King;

import java.util.List;

public class ChessLogic {
    public static boolean isValidX(int x){
        return x>=0 && x<8;
    }
    public static boolean isValidY(int y){
        return y>=0 && y<8;
    }
    public static boolean isThreatened(ChessPosition cp, ChessBoard cb, ChessColor enemyColor){
        List<ChessPiece> enemyPieces= (enemyColor==ChessColor.WHITE)? cb.WHITE_PIECES: cb.BLACK_PIECES;
        for(int i=0;i<enemyPieces.size();i++){
            ChessPiece cPiece = enemyPieces.get(i);
            if(cPiece.onBoard){
                if(cPiece instanceof King){
                    if(Math.abs(cPiece.position.getX()-cp.getX())<=1&& Math.abs(cPiece.position.getY()-cp.getY())<=1){
                        return true;
                    }
                }else{
                    for(ChessPosition cPos: cPiece.getPossibleMoves(cb)){
                        if(cPos.equals(cp)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
