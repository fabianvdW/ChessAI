package tests;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessPosition;
import Chess.pieces.Bishop;
import Chess.pieces.DebugFigur;
import Chess.pieces.Knight;
import Chess.pieces.Rook;


public class Test1 {
    public static void main(String[] args){
        KingTest();
    }
    public static void KingTest(){
        ChessBoard b= new ChessBoard();
        System.out.println(b.toString());
        b.WHITE_KING.position= new ChessPosition(3,2);
        b.board[3][2]=b.WHITE_KING;
        for (ChessPosition p : b.WHITE_KING.getPossibleMoves(b)) {
            new DebugFigur(b.WHITE_KING, ChessColor.WHITE, p, b);
        }

        System.out.println(b.toString());
    }
    public static void QueenTest(){
        ChessBoard b= new ChessBoard();
        System.out.println(b.toString());
        b.WHITE_QUEEN.position= new ChessPosition(3,4);
        b.board[3][4]=b.WHITE_QUEEN;
            for (ChessPosition p : b.WHITE_QUEEN.getPossibleMoves(b)) {
                new DebugFigur(b.WHITE_QUEEN, ChessColor.WHITE, p, b);
            }

        System.out.println(b.toString());
    }
    public static void KnightTest(){
        ChessBoard b= new ChessBoard();
        System.out.println(b.toString());
        Knight c= new Knight(ChessColor.WHITE,new ChessPosition(3,4),b);
        b.WHITE_KNIGHTS.add(c);
        for(int i=0;i<3;i++) {
            for (ChessPosition p : b.WHITE_KNIGHTS.get(i).getPossibleMoves(b)) {
                new DebugFigur(b.WHITE_KNIGHTS.get(i), ChessColor.WHITE, p, b);
            }

        }
        System.out.println(b.toString());
    }
    public static void RookTest(){
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.board[0][6]=null;
        Rook c= new Rook(ChessColor.WHITE,new ChessPosition(3,4),b);
        b.WHITE_ROOKS.add(c);
        for(int i=0;i<3;i++) {
            for (ChessPosition p : b.WHITE_ROOKS.get(i).getPossibleMoves(b)) {
                new DebugFigur(b.WHITE_ROOKS.get(i), ChessColor.WHITE, p, b);
            }
        }
        System.out.println(b.toString());
    }
    public static void BishopTest(){
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.board[3][6]=null;
        Bishop c= new Bishop(ChessColor.WHITE,new ChessPosition(1,5),b);
        b.WHITE_BISHOPS.add(c);
        for(int i=0;i<3;i++) {
            for (ChessPosition p : b.WHITE_BISHOPS.get(i).getPossibleMoves(b)) {
                new DebugFigur(b.WHITE_BISHOPS.get(i), ChessColor.WHITE, p, b);
            }
        }
        System.out.println(b.toString());
    }
    public static void PawnTest(){
        ChessBoard b= new ChessBoard();
        System.out.println(b.toString());
        for(int i=0;i<8;i++) {
            for (ChessPosition p : b.WHITE_PAWNS.get(i).getPossibleMoves(b)) {
                new DebugFigur(b.WHITE_PAWNS.get(i), ChessColor.WHITE, p, b);
            }
            for (ChessPosition p : b.BLACK_PAWNS.get(i).getPossibleMoves(b)) {
                new DebugFigur(b.BLACK_PAWNS.get(i), ChessColor.BLACK, p, b);
            }
        }
        System.out.println(b.toString());
    }
}
