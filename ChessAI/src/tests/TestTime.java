package tests;

import chess.ChessGame;
import chess.ChessGameStatus;
import chess.ChessLogic;
import chess.ChessMove;

import java.util.HashMap;
import java.util.List;

public class TestTime {

    public static void main(String[] args) {
        int games = 1000;
        //Plays thousand games of chess and measures time
        HashMap<ChessGameStatus, Integer> results = new HashMap<>();
        int moves = 0;
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < games; i++) {
            ChessGame cg = new ChessGame(null, null);
            while (cg.status == ChessGameStatus.INGAME) {
                moves++;
                List<ChessMove> availableMoves = ChessLogic.getAllPossibleMoves(cg.currentBoard, cg.move);
                cg.applyChessMove(availableMoves.get((int) (availableMoves.size() * Math.random())));
            }
            if (results.containsKey(cg.status)) {
                results.put(cg.status, results.get(cg.status) + 1);
            } else {
                results.put(cg.status, 1);
            }
        }
        long t1 = System.currentTimeMillis();
        System.out.println("Time for " + games + " games: " + (t1 - t0));
        System.out.println("Avg. Time: " + ((t1 - t0) / (games + 0.0)));
        System.out.println("Avg. Moves. " + moves / (games + 0.0));
        System.out.println("Results: ");
        for (ChessGameStatus cg : results.keySet()) {
            System.out.println(cg + ": " + results.get(cg));
        }
    }
}
