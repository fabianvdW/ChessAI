package chess.uci.advancedclient;

import chess.uci.UCIPlayer;

public class ChessAIOne extends UCIPlayer {
    static String name="name SimpleClient v1";
    static String author="author Fabian von der Warth";

    public ChessAIOne(){
        this.numProcessors=1;
        this.numProcessorsMax=1;
        this.numProcessorsMin=1;
    }

    @Override
    public void setupgo() {
        this.goInstance=new ChessAIOneGoInstance(this.position);
    }

    @Override
    public void setupgo(int wtime, int btime, int winc, int binc) {
        this.goInstance= new ChessAIOneGoInstance(this.position,wtime,btime,winc,binc);
    }

    @Override
    public String getID() {
        return "id "+name+"\nid "+author;
    }

    @Override
    public String getOptions() {
        return super.getOptions();
    }

    @Override
    public void setOption(String option, String value) {
        super.setOption(option,value);
    }
}
