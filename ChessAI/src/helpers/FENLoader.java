package helpers;

import chess.BitBoard;

import java.util.ArrayList;

public class FENLoader {
    public static BitBoard getBitBoardFromFen(String fen){
        long[] whitePieces = new long[6];
        long[] blackPieces = new long[6];
        long enPassant=0L;
        boolean castleWK=false;
        boolean castleWQ=false;
        boolean castleBK=false;
        boolean castleBQ=false;
        boolean move=false;
        String[] fenParts= fen.split(" ");
        if(fenParts.length!=6&&fenParts.length!=4){
            throw new RuntimeException("Illegal FEN String!");
        }
        //Init board
        int board=63;
        for(int i=0;i<fenParts[0].length();i++){
            char c= fenParts[0].charAt(i);
            if(c=='/'){
                continue;
            }
            if(c=='r'){
                blackPieces[BitBoard.ROOKS]|=(1L<<board--);
            }else if(c=='R'){
                whitePieces[BitBoard.ROOKS]|=(1L<<board--);
            }else if(c=='n'){
                blackPieces[BitBoard.KNIGHTS]|=(1L<<board--);
            }else if(c=='N'){
                whitePieces[BitBoard.KNIGHTS]|=(1L<<board--);
            }else if(c=='b'){
                blackPieces[BitBoard.BISHOPS]|=(1L<<board--);
            }else if(c=='B'){
                whitePieces[BitBoard.BISHOPS]|=(1L<<board--);
            }else if(c=='q'){
                blackPieces[BitBoard.QUEENS]|=(1L<<board--);
            }else if(c=='Q'){
                whitePieces[BitBoard.QUEENS]|=(1L<<board--);
            }else if(c=='k'){
                blackPieces[BitBoard.KING]|=(1L<<board--);
            }else if(c=='K'){
                whitePieces[BitBoard.KING]|=(1L<<board--);
            }else if(c=='p'){
                blackPieces[BitBoard.PAWNS]|=(1L<<board--);
            }else if(c=='P'){
                whitePieces[BitBoard.PAWNS]|=(1L<<board--);
            }else{
                int free=c-'0';
                if(free<1||free>8){
                    throw new RuntimeException("Illegal FEN!");
                }
                board-=free;
            }
        }

        //Side to move
        if(fenParts[1].length()==1){
            if(fenParts[1].charAt(0)=='w'){
                move=true;
            }else if(fenParts[1].charAt(0)=='b'){
                move=false;
            }else{
                throw new RuntimeException("Illegal FEN!");
            }
        }else{
            throw new RuntimeException("Illegal FEN!");
        }

        //Castling ability
        if(fenParts[2].length()<1||fenParts[2].length()>4){
            throw new RuntimeException("Illegal FEN!");
        }
        if(fenParts[2].charAt(0)!='-') {
            for (int i = 0; i < fenParts[2].length(); i++) {
                char c= fenParts[2].charAt(i);
                if(c=='K'){
                    castleWK=true;
                }else if(c=='k'){
                    castleBK=true;
                }else if(c=='Q'){
                    castleWQ=true;
                }else if(c=='q'){
                    castleBQ=true;
                }else{
                    throw new RuntimeException("Illegal FEN!");
                }
            }
        }

        //En passant possibility
        if(fenParts[3].length()<1||fenParts[3].length()>2){
            throw new RuntimeException("Illegal FEN!");
        }
        if(fenParts[3].charAt(0)!='-'){
            if(fenParts[3].length()==2) {
                int file = fenParts[3].charAt(0)-'a';
                int rank = fenParts[3].charAt(1)-'0';
                if(rank==6){
                    rank=3;
                }else if(rank==3){
                    rank=4;
                }else{
                    throw new RuntimeException("Illegal FEN!");
                }
                int pos=63-file-rank*8;
                enPassant=1L<<pos;
            }else{
                throw new RuntimeException("Illegal FEN!");
            }
        }

        //not reading half moves and moves yet
        return new BitBoard(whitePieces,blackPieces,enPassant,castleWK,castleWQ,castleBK,castleBQ,new ArrayList<>(),move);
    }
}
