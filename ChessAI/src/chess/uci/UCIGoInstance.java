package chess.uci;

import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMove;


public abstract class UCIGoInstance extends Thread{

    public BitBoardMove bestMove;
    public BitBoard position;

    public int wtime;
    public int btime;
    public int winc;
    public int binc;
    public UCIGoInstance(BitBoard position){
        this.position=position;
    }
    public UCIGoInstance(BitBoard position, int wtime, int btime, int winc, int binc){
        this(position);
        this.wtime=wtime;
        this.btime=btime;
        this.winc=winc;
        this.binc=binc;
    }
    @Override
    public abstract void run();

}
