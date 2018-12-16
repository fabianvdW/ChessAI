package chess.uci;

import chess.uci.simpleclient.SimpleClient;

public class UCITest {
    public static void main(String[] args){
        UCIPlayer p= new SimpleClient();
        p.start();
    }
}
