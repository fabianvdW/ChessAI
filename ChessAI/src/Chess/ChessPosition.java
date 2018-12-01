package Chess;

public class ChessPosition {
    private int x;
    private int y;

    public ChessPosition(int x, int y) {
        setX(x);
        setY(y);
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
    public boolean equals(Object o){
        if(o instanceof ChessPosition){
            ChessPosition cp2= (ChessPosition) o;
            return cp2.getX()== this.x && cp2.getY()== this.y;
        }else{
            throw new RuntimeException("Expected Chess Position object!");
        }
    }

    @Override
    public String toString(){
        String s="("+this.x+","+this.y+")";

        return s;
    }

    @Override
    public ChessPosition clone(){
        return new ChessPosition(this.x, this.y);
    }
}
