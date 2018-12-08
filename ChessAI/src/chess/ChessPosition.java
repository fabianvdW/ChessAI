package chess;

public class ChessPosition {
    private int x;
    private int y;

    public ChessPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public ChessPosition addChessVector(ChessVector cp) {
        int newX = (int) (this.x + cp.x);
        int newY = (int) (this.y + cp.y);
        if (ChessLogic.isValidX(newX) && ChessLogic.isValidY(newY)) {
            return new ChessPosition(newX, newY);
        }
        return null;
    }
    public ChessVector toChessVector(){
        return new ChessVector(this.x,this.y);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (!ChessLogic.isValidX(x)) {
            //TODO Write specific Exception
            throw new RuntimeException("X position set is out of bounds! Got X: " + x + "but expected 0<=" + x + "< 8");
        }
        this.x = x;
    }

    public void setY(int y) {
        if (!ChessLogic.isValidY(y)) {
            //TODO Write specific Exception
            throw new RuntimeException("Y position set is out of bounds! Got X: " + y + "but expected 0<=" + y + "< 8");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ChessPosition) {
            ChessPosition cp2 = (ChessPosition) o;
            return cp2.getX() == this.x && cp2.getY() == this.y;
        } else if(o!=null) {
            throw new RuntimeException("Expected chess Position object!");
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "(" + this.x + "," + this.y + ")";

        return s;
    }

    @Override
    public ChessPosition clone() {
        return new ChessPosition(this.x, this.y);
    }
}
