package chess;


public class ChessVector {
    public double x;
    public double y;
    public ChessVector(double x, double y){
        this.x=x;
        this.y=y;
    }
    public ChessVector times(double z){
        return new ChessVector(this.x*z,this.y*z);
    }
    public boolean isMultiplierOf(ChessVector cv){
        if (cv.x == 0 && this.x != 0||cv.y==0&& this.y!=0|| this.x==0&& cv.x!=0 || this.y==0&& cv.y!=0) {
            return false;
        }
        if(this.x!=0){
            double res= this.x/cv.x;
            if(res%1!=0||res<0){
                return false;
            }
        }
        if(this.y!=0){
            double res= this.y/cv.y;
            if(res %1!=0||res<0){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof ChessVector){
            ChessVector cv= (ChessVector) o;
            return cv.x==this.x&& cv.y==this.y;
        }
        return false;
    }
    @Override
    public String toString(){
        return "("+this.x+","+this.y+")";
    }
}
