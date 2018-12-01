package tests;

import Chess.ChessBoard;
import Chess.ChessGameStatus;
import Chess.ChessLogic;
import Chess.ChessMove;

import java.util.List;

public class TestApplyMoves {

    public static void main(String[] args){
        ChessBoard cb= new ChessBoard();
        int moves=0;
        while(cb.status==ChessGameStatus.INGAME){
            moves++;
            List<ChessMove> availableMoves= ChessLogic.getAllPossibleMoves(cb,cb.move);
            cb.applyChessMove(availableMoves.get((int)(availableMoves.size()*Math.random())));
            System.out.println(cb.toString());
        }
        System.out.println(cb.status);
        System.out.println("Moves: "+moves);
    }
}
