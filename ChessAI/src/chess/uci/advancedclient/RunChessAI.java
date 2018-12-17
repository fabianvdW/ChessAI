package chess.uci.advancedclient;

import chess.uci.UCIPlayer;

public class RunChessAI {
    public static void main(String[] args){
        UCIPlayer p= new ChessAIOne();
        p.start();

    }
}