package tests;

import chess.ChessGameStatus;
import chess.bitboards.BitBoard;

import java.util.HashMap;

public class TestBitBoardTime {
    public static void main(String[] args){
        int games=1000;
        int moves=0;
        HashMap<ChessGameStatus, Integer> results = new HashMap<>();
        long time=0L;
        for(int i=0;i<games;i++){
            BitBoard bb=new BitBoard();
            long t0=System.currentTimeMillis();
            moves+=BitBoard.playGame(bb).size();
            long t1=System.currentTimeMillis();
            time+=(t1-t0);
        }
        System.out.println("Plies per Second: "+(moves+0.0)/((time)/1000.0));
        System.out.println("Time for " + games + " games: " + (time));
        System.out.println("Avg. Time: " + ((time) / (games + 0.0)));
        System.out.println("Avg. Plies. " + moves / (games + 0.0));
    }
}
