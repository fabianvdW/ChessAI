package tests;

import Chess.ChessBoard;
import Chess.ChessLogic;
import Chess.ChessPosition;
import Chess.pieces.Pawn;

public class TestChessMate {

    public static void main(String[] args){
        boolean mate= scenario2();
        //scenario2();
    }
    public static boolean scenario2(){
        ChessBoard cb= new ChessBoard();
        cb.setChessPiece(new ChessPosition(1,3),cb.BLACK_QUEEN);
        cb.setChessPiece(new ChessPosition(3,0),null);
        cb.setChessPiece(new ChessPosition(2,2),cb.BLACK_PAWNS.get(0));
        cb.setChessPiece(new ChessPosition(0,3),cb.WHITE_KING);
        cb.setChessPiece(new ChessPosition(4,7),null);
        System.out.println(cb.toString());
        System.out.println("Mate: "+ChessLogic.isChessMate(cb));
        return false;
    }
    public static boolean scenario1(){
        ChessBoard cb= new ChessBoard();
        System.out.println(cb.toString());
        boolean mate= ChessLogic.isChessMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
    }
}
