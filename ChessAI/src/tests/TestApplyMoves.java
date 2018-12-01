package tests;

import Chess.ChessBoard;

public class TestApplyMoves {

    public static void main(String[] args){
        ChessBoard cb= new ChessBoard();
        System.out.println(cb.toString());
        cb.applyChessMove(cb.WHITE_KNIGHTS.get(0).getPossibleMoves(cb,false).get(1));
        System.out.println(cb.toString());
    }
}
