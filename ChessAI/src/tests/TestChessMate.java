package tests;

import Chess.*;
import Chess.pieces.Pawn;

public class TestChessMate {

    public static void main(String[] args){
        scenario1();
        scenario2();
        scenario3();
    }
    public static boolean scenario3(){
        ChessBoard cb = new ChessBoard();
        cb.setChessPiece(new ChessPosition(4,4),cb.WHITE_PAWNS.get(4));
        cb.setChessPiece(new ChessPosition(4,6),null);
        cb.setChessPiece(new ChessPosition(5,1),cb.WHITE_QUEEN);
        cb.setChessPiece(new ChessPosition(3,7),null);
        cb.setChessPiece(new ChessPosition(2,4),cb.WHITE_BISHOPS.get(1));
        cb.setChessPiece(new ChessPosition(7,2),cb.BLACK_KNIGHTS.get(1));
        cb.setChessPiece(new ChessPosition(6,0),null);
        cb.BLACK_PAWNS.get(5).onBoard=false;
        cb.move=ChessColor.BLACK;
        System.out.println(cb.toString());
        System.out.println("Mate: "+ChessLogic.isCheckMate(cb));
        return false;
    }
    public static boolean scenario2(){
        ChessBoard cb= new ChessBoard();
        cb.setChessPiece(new ChessPosition(1,3),cb.BLACK_QUEEN);
        cb.setChessPiece(new ChessPosition(3,0),null);
        cb.setChessPiece(new ChessPosition(2,2),cb.BLACK_PAWNS.get(0));
        cb.setChessPiece(new ChessPosition(0,3),cb.WHITE_KING);
        cb.setChessPiece(new ChessPosition(4,7),null);
        System.out.println(cb.toString());
        System.out.println("Mate: "+ChessLogic.isCheckMate(cb));
        return false;
    }
    public static boolean scenario1(){
        ChessBoard cb= new ChessBoard();
        System.out.println(cb.toString());
        boolean mate= ChessLogic.isCheckMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
    }
}
