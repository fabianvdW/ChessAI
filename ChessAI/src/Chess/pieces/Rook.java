package Chess.pieces;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessLogic;
import Chess.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {
    public Rook(ChessColor color, ChessPosition position,ChessBoard board){
        super(color,position,board);
        if(this.color== ChessColor.WHITE) {
            this.representation = "\u2656";
        }else{
            this.representation= "\u265C";
        }
    }

    @Override
    public List<ChessPosition> getPossibleMoves(ChessBoard b) {
        //TODO Pinning

        List<ChessPosition> result = new ArrayList<>();

        ChessColor enemyColor = this.color==ChessColor.BLACK? ChessColor.WHITE: ChessColor.BLACK;
        for(int i=0;i<4;i++){
            int xIncrementor=0;
            int yIncrementor=0;
            switch(i){
                case 0: xIncrementor=1;
                break;
                case 1: xIncrementor=-1;
                break;
                case 2: yIncrementor=1;
                break;
                case 3: yIncrementor=-1;
            }
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
        return result;
    }
}
