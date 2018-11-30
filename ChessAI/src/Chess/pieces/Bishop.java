package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessLogic;
import Chess.ChessPosition;

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
    public List<ChessPosition> getPossibleMoves(ChessBoard b) {
        //TODO Pinning

        List<ChessPosition> result = new ArrayList<>();

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
                    if(cPiece==null){
                        result.add(cp);
                    }else if(cPiece.color==enemyColor){
                        result.add(cp);
                        break;
                    }else{
                        break;
                    }
                }while (true);
            }
        }
        return result;
    }
}
