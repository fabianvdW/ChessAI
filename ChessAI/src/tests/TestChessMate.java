package tests;

import Chess.ChessBoard;
import Chess.ChessLogic;
import Chess.ChessPosition;

public class TestChessMate {

    public static void main(String[] args){
        boolean mate= scenario1();
        scenario2();
    }
    public static boolean scenario2(){
        ChessBoard cb= new ChessBoard();
        cb.BLACK_QUEEN.position= new ChessPosition(4,5);
        cb.setChessPiece(cb.BLACK_QUEEN.position,cb.BLACK_QUEEN);
        cb.setChessPiece(new ChessPosition(4,6),null);
        System.out.println(cb.toString());
        System.out.println("Mate: "+ChessLogic.isChessMate(cb));
        return ChessLogic.isChessMate(cb);
    }
    public static boolean scenario1(){
        ChessBoard cb= new ChessBoard();
        System.out.println(cb.toString());
        boolean mate= ChessLogic.isChessMate(cb);
        System.out.println("ChessMate: "+mate);
        return mate;
    }
}
