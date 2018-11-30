package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessLogic;
import Chess.ChessPosition;


import java.util.*;

public class Pawn extends ChessPiece {

    public Pawn(ChessColor color, ChessPosition position,ChessBoard board) {
        super(color, position, board);
        if(this.color== ChessColor.WHITE) {
            this.representation = "\u2659";
        }else{
            this.representation= "\u265F";
        }
    }

    @Override
    public List<ChessPosition> getPossibleMoves(ChessBoard b) {
        //TODO Pinning
        List<ChessPosition> result = new ArrayList<>();

        ChessColor enemyColor = this.color==ChessColor.BLACK? ChessColor.WHITE: ChessColor.BLACK;
        int incrementor= this.color==ChessColor.WHITE? -1: 1;
        //2 vorrücken
        for (int i = 0; i < 4; i++) {
            ChessPosition newPosition = null;
            int newX=this.position.getX();
            int newY=this.position.getY();
            switch (i) {
                case 0:
                    newY+=incrementor;
                    break;
                case 1:
                    newY+=2*incrementor;
                    break;
                case 2:
                    //Diagonal schlagen links
                    newX+=1;
                    newY+=incrementor;
                    break;
                case 3:
                    newX-=1;
                    newY+=incrementor;
                    break;
            }
            if(ChessLogic.isValidX(newX)&& ChessLogic.isValidX(newY)){
                ChessPosition cp= new ChessPosition(newX,newY);
                ChessPiece cpPiece = b.getChessPiece(cp);
                if(i==0||i==1){;
                    if(cpPiece==null&&(i!=1 ||this.position.getY()== (this.color== ChessColor.WHITE? 6:  1))){
                        result.add(cp);
                    }
                }else{
                    if(cpPiece!=null && cpPiece.color==enemyColor){
                        result.add(cp);
                    }
                }
            }
        }
        return result;
    }
}
