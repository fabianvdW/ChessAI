package tests;

import chess.CastleMove;
import chess.ChessGame;
import chess.ChessMove;
import chess.pieces.ChessPiece;
import chess.pieces.King;
import chess.pieces.Rook;

public class TestCastle {
    public static void main(String[] args) {
        assert (castleShortWhite());
        System.out.println("Test 1 passed!");
        assert (castleShortWhiteFalse());
        System.out.println("Test 2 passed!");
        assert (casteLongBlack());
        System.out.println("Test 3 passed!");
        assert (casteLongBlackFalse());
        System.out.println("Test 4 passed!");
    }

    public static boolean castleShortWhite() {
        ChessGame cg = new ChessGame(null, null);
        int g2 = 6 + 8 * 6;
        cg.applyChessMove(new ChessMove(g2, 46, cg.currentBoard.getChessPiece(g2), null));
        //Black
        int a7 = 8;
        cg.applyChessMove(new ChessMove(a7, 16, cg.currentBoard.getChessPiece(a7), null));
        //White
        int f1 = 8 * 7 + 5;
        cg.applyChessMove(new ChessMove(f1, 54, cg.currentBoard.getChessPiece(f1), null));
        //Black
        int a6 = 16;
        cg.applyChessMove(new ChessMove(a6, 24, cg.currentBoard.getChessPiece(a6), null));
        //White
        int g1 = 7 * 8 + 6;
        cg.applyChessMove(new ChessMove(g1, 47, cg.currentBoard.getChessPiece(g1), null));
        //Black
        int a5 = 24;
        cg.applyChessMove(new ChessMove(a5, 32, cg.currentBoard.getChessPiece(a5), null));
        //System.out.println(cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard,false));
        if (cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard, false).size() != 2) {
            return false;
        }
        ChessPiece cRook = cg.currentBoard.getChessPiece(63);
        if (cRook instanceof Rook) {
            CastleMove cm = new CastleMove(61, 63, cg.currentBoard.getChessPiece(61), null, (Rook) cRook);
            cg.applyChessMove(cm);
            return cg.currentBoard.getBoard()[63] instanceof King && cg.currentBoard.getBoard()[62] instanceof Rook;
        }
        return false;
    }

    public static boolean castleShortWhiteFalse() {
        ChessGame cg = new ChessGame(null, null);
        int g2 = 53;
        cg.applyChessMove(new ChessMove(g2, 37,cg.currentBoard.getChessPiece(g2), null));
        //Black
        int a7 = 12;
        cg.applyChessMove(new ChessMove(a7, 20,cg.currentBoard.getChessPiece(a7), null));
        //White
        int f1 = 54;
        cg.applyChessMove(new ChessMove(f1, 46,cg.currentBoard.getChessPiece(f1), null));
        //Black
        int a6 = 5;
        cg.applyChessMove(new ChessMove(a6, 26,cg.currentBoard.getChessPiece(a6), null));
        //White
        int g1 = 61;
        cg.applyChessMove(new ChessMove(g1, 54,cg.currentBoard.getChessPiece(g1), null));
        //Black
        int a5 = 8;
        cg.applyChessMove(new ChessMove(a5, 16,cg.currentBoard.getChessPiece(a5), null));
        //White
        int b1 = 63;
        cg.applyChessMove(new ChessMove(b1, 47,cg.currentBoard.getChessPiece(b1), null));
        //Black
        int a4 = 16;
        cg.applyChessMove(new ChessMove(a4, 24,cg.currentBoard.getChessPiece(a4), null));
        //System.out.println(cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard,false));
        return cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard, false).size() == 1;
    }

    public static boolean casteLongBlack() {
        ChessGame cg = new ChessGame(null, null);
        //White
        int h2 = new int (7, 6);
        int h3 = new int (7, 5);
        int h4 = new int (7, 4);
        int h5 = new int (7, 3);
        int h6 = new int (7, 2);
        int g7 = new int (6, 1);
        cg.applyChessMove(new ChessMove(h2, h3, cg.currentBoard.getChessPiece(h2), null));

        //Black
        int d8 = new int (3, 0);
        int d7 = new int (3, 1);
        int d6 = new int (3, 2);
        int d5 = new int (3, 3);
        int c8 = new int (2, 0);
        int b8 = new int (1, 0);
        int a6 = new int (0, 2);
        cg.applyChessMove(new ChessMove(d7, d5, cg.currentBoard.getChessPiece(d7), null));

        //White
        cg.applyChessMove(new ChessMove(h3, h4, cg.currentBoard.getChessPiece(h3), null));

        //Black
        cg.applyChessMove(new ChessMove(d8, d6, cg.currentBoard.getChessPiece(d8), null));

        //White
        cg.applyChessMove(new ChessMove(h4, h5, cg.currentBoard.getChessPiece(h4), null));

        //Black
        cg.applyChessMove(new ChessMove(c8, d7, cg.currentBoard.getChessPiece(c8), null));

        //White
        cg.applyChessMove(new ChessMove(h5, h6, cg.currentBoard.getChessPiece(h5), null));

        //Black
        cg.applyChessMove(new ChessMove(b8, a6, cg.currentBoard.getChessPiece(b8), null));

        //White
        cg.applyChessMove(new ChessMove(h6, g7, cg.currentBoard.getChessPiece(h6), cg.currentBoard.getChessPiece(g7)));

        if (cg.currentBoard.BLACK_KING.getPossibleMoves(cg.currentBoard, false).size() != 2) {
            return false;
        }
        ChessPiece cRook = cg.currentBoard.getChessPiece(new int (0, 0));
        if (cRook instanceof Rook) {
            CastleMove cm = new CastleMove(new int (4, 0),new int (2, 0),cg.currentBoard.getChessPiece(new int (4, 0)),
            null, (Rook) cRook);
            cg.applyChessMove(cm);
            return cg.currentBoard.getBoard()[2][0] instanceof King && cg.currentBoard.getBoard()[3][0] instanceof Rook;
        }
        return false;
    }

    public static boolean casteLongBlackFalse() {
        ChessGame cg = new ChessGame(null, null);
        //White
        int g5 = new int (6, 3);
        int c1 = new int (2, 7);
        int d2 = new int (3, 6);
        int d3 = new int (3, 5);
        int h2 = new int (7, 6);
        int h3 = new int (7, 5);
        int h4 = new int (7, 4);
        int h5 = new int (7, 3);
        int h6 = new int (7, 2);
        cg.applyChessMove(new ChessMove(h2, h3, cg.currentBoard.getChessPiece(h2), null));

        //Black
        int d8 = new int (3, 0);
        int d7 = new int (3, 1);
        int d6 = new int (3, 2);
        int d5 = new int (3, 3);
        int c8 = new int (2, 0);
        int b8 = new int (1, 0);
        int a6 = new int (0, 2);
        int e7 = new int (4, 1);
        int e6 = new int (4, 2);
        cg.applyChessMove(new ChessMove(d7, d5, cg.currentBoard.getChessPiece(d7), null));

        //White
        cg.applyChessMove(new ChessMove(h3, h4, cg.currentBoard.getChessPiece(h3), null));

        //Black
        cg.applyChessMove(new ChessMove(d8, d6, cg.currentBoard.getChessPiece(d8), null));

        //White
        cg.applyChessMove(new ChessMove(h4, h5, cg.currentBoard.getChessPiece(h4), null));

        //Black
        cg.applyChessMove(new ChessMove(c8, d7, cg.currentBoard.getChessPiece(c8), null));

        //White
        cg.applyChessMove(new ChessMove(d2, d3, cg.currentBoard.getChessPiece(d2), null));

        //Black
        cg.applyChessMove(new ChessMove(b8, a6, cg.currentBoard.getChessPiece(b8), null));

        //White
        cg.applyChessMove(new ChessMove(c1, g5, cg.currentBoard.getChessPiece(c1), cg.currentBoard.getChessPiece(g5)));

        //Black
        cg.applyChessMove(new ChessMove(e7, e6, cg.currentBoard.getChessPiece(e7), null));

        //White
        cg.applyChessMove(new ChessMove(h5, h6, cg.currentBoard.getChessPiece(h5), null));

        return cg.currentBoard.BLACK_KING.getPossibleMoves(cg.currentBoard, false).isEmpty();
    }
}
