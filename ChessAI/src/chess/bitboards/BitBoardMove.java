package chess.bitboards;

public class BitBoardMove {

    public int x1;
    public int y1;
    public int x2;
    public int y2;
    public int movingPiece;

    public boolean color;
    public char desc;


    public BitBoardMove(int x1, int y1, int x2, int y2, boolean color, int movingPiece) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.desc = ' ';
        this.color = color;
        this.movingPiece = movingPiece;
    }

    public BitBoardMove(int x1, int y1, int x2, int y2, boolean color, char desc, int movingPiece) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.desc = desc;
        this.movingPiece=movingPiece;
    }

    @Override
    public String toString() {
        return "(" +(char)( 'H'-7+x1 )+ "," + (8-y1) + ") -> (" + (char)('H'-7+x2) + "," + (8-y2) + "): " + this.desc + " Color: " + this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BitBoardMove) {
            BitBoardMove bm = (BitBoardMove) o;
            return bm.x1 == this.x1 && bm.x2 == this.x2 && bm.y1 == this.y1 && bm.y2 == this.y2 && bm.color == this.color && bm.desc == this.desc;
        }
        return false;
    }
}
