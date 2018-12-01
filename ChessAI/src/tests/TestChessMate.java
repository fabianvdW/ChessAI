package tests;

import Chess.*;
import Chess.pieces.ChessPiece;
import Chess.pieces.Pawn;

public class TestChessMate {

    public static void main(String[] args){
        assert(!scenario1());
        assert(scenario2());
        assert(!scenario3());
        assert(scenario4());

    }
    public static boolean scenario4(){
        ChessBoard cb = new ChessBoard();
        clearBoard(cb);
        cb.setChessPiece(new ChessPosition(7,0),cb.WHITE_KING);
        cb.setChessPiece(new ChessPosition(1,0),cb.BLACK_ROOKS.get(0));
        cb.setChessPiece(new ChessPosition(1,1),cb.BLACK_ROOKS.get(1));
        System.out.println(cb.toString());
        boolean mate = ChessLogic.isCheckMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
        //return false;
    }
    public static void clearBoard(ChessBoard cb){
        for(ChessPiece cp: cb.WHITE_PIECES){
            cp.onBoard=false;
            cb.setChessPiece(cp.position,null);
        }
        for(ChessPiece cp: cb.BLACK_PIECES){
            cp.onBoard=false;
            cb.setChessPiece(cp.position,null);
        }
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
        boolean mate = ChessLogic.isCheckMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
    }
    public static boolean scenario2(){
        ChessBoard cb= new ChessBoard();
        cb.setChessPiece(new ChessPosition(1,3),cb.BLACK_QUEEN);
        cb.setChessPiece(new ChessPosition(3,0),null);
        cb.setChessPiece(new ChessPosition(2,2),cb.BLACK_PAWNS.get(0));
        cb.setChessPiece(new ChessPosition(0,3),cb.WHITE_KING);
        cb.setChessPiece(new ChessPosition(4,7),null);
        System.out.println(cb.toString());
        boolean mate = ChessLogic.isCheckMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
    }
    public static boolean scenario1(){
        ChessBoard cb= new ChessBoard();
        System.out.println(cb.toString());
        boolean mate= ChessLogic.isCheckMate(cb);
        System.out.println("Mate: "+mate);
        return mate;
    }
}
