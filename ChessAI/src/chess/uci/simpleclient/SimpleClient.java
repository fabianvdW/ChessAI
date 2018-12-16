package chess.uci.simpleclient;

import chess.uci.UCIGoInstance;
import chess.uci.UCIPlayer;

public class SimpleClient extends UCIPlayer {
    static String name="name SimpleClient v1";
    static String author="author Fabian von der Warth";

    public SimpleClient(){
        this.numProcessors=1;
        this.numProcessorsMax=1;
        this.numProcessorsMin=1;
    }

    @Override
    public void setupgo() {
        this.goInstance= new SimpleClientGoInstance(this.position);
    }

    @Override
    public void setupgo(int wtime, int btime, int winc, int binc) {
        this.goInstance= new SimpleClientGoInstance(this.position,wtime,btime,winc,binc);
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
