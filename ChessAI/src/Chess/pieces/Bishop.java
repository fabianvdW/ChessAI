package Chess.pieces;

import Chess.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(ChessColor color, ChessPosition position,ChessBoard board){
        super(color,position,board);
        if(this.color== ChessColor.WHITE) {
            this.representation = "\u2657";
        }else{
            this.representation= "\u265D";
        }
    }

    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b,boolean pinFlag) {
        //TODO Pinning

        List<ChessMove> result = new ArrayList<>();

        ChessColor enemyColor = this.color==ChessColor.BLACK? ChessColor.WHITE: ChessColor.BLACK;
        //4 Diagonalen
        for(int yIncrementor=-1;yIncrementor<=1;yIncrementor++){
            if(yIncrementor==0)continue;
            for(int xIncrementor=-1;xIncrementor<=1;xIncrementor++){
                if(xIncrementor==0)continue;
                int xCoordinate= this.position.getX();
                int yCoordinate= this.position.getY();
                ChessPosition cp = null;
                do{
                    xCoordinate+=xIncrementor;
                    yCoordinate+=yIncrementor;
                    if(!(ChessLogic.isValidX(xCoordinate)&&ChessLogic.isValidY(yCoordinate))){
                        break;
                    }
                    cp= new ChessPosition(xCoordinate,yCoordinate);
                    ChessPiece cPiece = b.getChessPiece(cp);
                    ChessMove cm = new ChessMove(this.position,cp,this,cPiece);
                    if(cPiece!=null && cPiece.color!=enemyColor){
                        break;
                    }
                    if(!pinFlag&&ChessLogic.isPinned(cm,b)){
                        continue;
                    }
                    if(cPiece==null){
                        result.add(cm);
                    }else if(cPiece.color==enemyColor){
                        result.add(cm);
                        break;
                    }
                }while (true);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Bishop){
            Bishop b = (Bishop)o;
            return b.position.equals(this.position);
        }
        return false;
    }
}
