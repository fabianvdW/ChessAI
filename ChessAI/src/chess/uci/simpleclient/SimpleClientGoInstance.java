package chess.uci.simpleclient;

import chess.bitboards.BitBoard;
import chess.uci.UCIGoInstance;

import javax.swing.*;


public class SimpleClientGoInstance extends UCIGoInstance {
    public SimpleClientGoInstance(BitBoard position) {
        super(position);
    }
    public SimpleClientGoInstance(BitBoard position,int wtime, int btime, int winc, int binc){
        super(position,wtime,btime,winc,binc);
    }

    @Override
    public void run() {
        Thread t= this;
        if(this.wtime!=0) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            // your code here
                            t.stop();
                            System.out.println("bestmove "+((SimpleClientGoInstance) t).bestMove);
                        }
                    },(int)(10)
            );

        }
        this.position.initBoard();
        this.bestMove=this.position.bm.legalMoves.get((int)(this.position.bm.legalMoves.size()*Math.random()));
    }
}
