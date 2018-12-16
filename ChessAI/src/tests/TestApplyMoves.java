package tests;

import chess.*;

import java.util.List;

public class TestApplyMoves {

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        ChessGame cb = new ChessGame();
        int moves = 0;
        while (cb.status == ChessGameStatus.INGAME) {
            moves++;
            List<ChessMove> availableMoves = ChessLogic.getAllPossibleMoves(cb.currentBoard, cb.move);
            cb.applyChessMove(availableMoves.get((int) (availableMoves.size() * Math.random())));
            System.out.println(cb.toString());
        }
        System.out.println(cb.status);
        System.out.println("Moves: " + moves);
        System.out.println(System.currentTimeMillis() - t0);
    }
}
