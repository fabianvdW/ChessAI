package helpers;

public class BitBoardGenerator {
    public static void main(String[] args){
        System.out.println(generateRankBitBoards());
    }
    public static String generateFileBitBoards(){
        //File sind Spalten
        StringBuilder sb= new StringBuilder();
        sb.append("{");
        for(int i=0;i<8;i++){
            long bb=0L;
            for(int j=0;j<64;j++){
                bb<<=1;
                if(j%8==i){
                    bb+=1L;
                }
            }
            sb.append(String.format("0x%016X", bb)+"L");
            if(i!=7){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String generateRankBitBoards(){
        //File sind Spalten
        StringBuilder sb= new StringBuilder();
        sb.append("{");
        for(int i=0;i<8;i++){
            long bb=0L;
            for(int j=0;j<64;j++){
                bb<<=1;
                if(j/8==i){
                    bb+=1L;
                }
            }
            sb.append(String.format("0x%016X", bb)+"L");
            if(i!=7){
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
