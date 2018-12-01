package Chess;

import Chess.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessThreat {
    boolean threat;
    List<ChessPiece> threats;
    public ChessThreat(boolean threat, List<ChessPiece> threats){
        this.threat=threat;
        this.threats=threats;
    }
}
