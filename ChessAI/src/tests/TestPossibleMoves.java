package tests;

import Chess.ChessBoard;
import Chess.ChessColor;
import Chess.ChessMove;
import Chess.ChessPosition;
import Chess.pieces.Bishop;
import Chess.pieces.DebugFigur;
import Chess.pieces.Knight;
import Chess.pieces.Rook;


public class TestPossibleMoves {
    public static void main(String[] args) {
        PawnTest();
    }

    public static void KingTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.WHITE_KING.position = new ChessPosition(4, 2);
        for (ChessMove p : b.WHITE_KING.getPossibleMoves(b, false)) {
            new DebugFigur(b.WHITE_KING, ChessColor.WHITE, p.to, b);
        }

        System.out.println(b.toString());
    }

    public static void QueenTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.WHITE_QUEENS.get(0).position = new ChessPosition(3, 4);
        b.setChessPiece(b.WHITE_QUEENS.get(0).position, b.WHITE_QUEENS.get(0));
        for (ChessMove p : b.WHITE_QUEENS.get(0).getPossibleMoves(b, false)) {
            new DebugFigur(b.WHITE_QUEENS.get(0), ChessColor.WHITE, p.to, b);
        }

        System.out.println(b.toString());
    }

    public static void KnightTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        Knight c = new Knight(ChessColor.WHITE, new ChessPosition(3, 4), b);
        b.WHITE_KNIGHTS.add(c);
        for (int i = 0; i < 3; i++) {
            for (ChessMove p : b.WHITE_KNIGHTS.get(i).getPossibleMoves(b, false)) {
                new DebugFigur(b.WHITE_KNIGHTS.get(i), ChessColor.WHITE, p.to, b);
            }

        }
        System.out.println(b.toString());
    }

    public static void RookTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.setChessPiece(new ChessPosition(0, 6), null);
        Rook c = new Rook(ChessColor.WHITE, new ChessPosition(3, 4), b);
        b.WHITE_ROOKS.add(c);
        for (int i = 0; i < 3; i++) {
            for (ChessMove p : b.WHITE_ROOKS.get(i).getPossibleMoves(b, false)) {
                new DebugFigur(b.WHITE_ROOKS.get(i), ChessColor.WHITE, p.to, b);
            }
        }
        System.out.println(b.toString());
    }

    public static void BishopTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        b.setChessPiece(new ChessPosition(3, 6), null);
        Bishop c = new Bishop(ChessColor.WHITE, new ChessPosition(1, 5), b);
        b.WHITE_BISHOPS.add(c);
        for (int i = 0; i < 3; i++) {
            for (ChessMove p : b.WHITE_BISHOPS.get(i).getPossibleMoves(b, false)) {
                new DebugFigur(b.WHITE_BISHOPS.get(i), ChessColor.WHITE, p.to, b);
            }
        }
        System.out.println(b.toString());
    }

    public static void PawnTest() {
        ChessBoard b = new ChessBoard();
        System.out.println(b.toString());
        for (int i = 0; i < 8; i++) {
            for (ChessMove p : b.WHITE_PAWNS.get(i).getPossibleMoves(b, false)) {
                new DebugFigur(b.WHITE_PAWNS.get(i), ChessColor.WHITE, p.to, b);
            }
            for (ChessMove p : b.BLACK_PAWNS.get(i).getPossibleMoves(b, false)) {
                new DebugFigur(b.BLACK_PAWNS.get(i), ChessColor.BLACK, p.to, b);
            }
        }
        System.out.println(b.toString());
    }
}
