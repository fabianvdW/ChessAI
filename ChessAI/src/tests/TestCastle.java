package tests;

import chess.CastleMove;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
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
        ChessPosition g2 = new ChessPosition(6, 6);
        cg.applyChessMove(new ChessMove(g2, new ChessPosition(6, 5), cg.currentBoard.getChessPiece(g2), null));
        //Black
        ChessPosition a7 = new ChessPosition(0, 1);
        cg.applyChessMove(new ChessMove(a7, new ChessPosition(0, 2), cg.currentBoard.getChessPiece(a7), null));
        //White
        ChessPosition f1 = new ChessPosition(5, 7);
        cg.applyChessMove(new ChessMove(f1, new ChessPosition(6, 6), cg.currentBoard.getChessPiece(f1), null));
        //Black
        ChessPosition a6 = new ChessPosition(0, 2);
        cg.applyChessMove(new ChessMove(a6, new ChessPosition(0, 3), cg.currentBoard.getChessPiece(a6), null));
        //White
        ChessPosition g1 = new ChessPosition(6, 7);
        cg.applyChessMove(new ChessMove(g1, new ChessPosition(7, 5), cg.currentBoard.getChessPiece(g1), null));
        //Black
        ChessPosition a5 = new ChessPosition(0, 3);
        cg.applyChessMove(new ChessMove(a5, new ChessPosition(0, 4), cg.currentBoard.getChessPiece(a5), null));
        //System.out.println(cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard,false));
        if (cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard, false).size() != 2) {
            return false;
        }
        ChessPiece cRook = cg.currentBoard.getChessPiece(new ChessPosition(7, 7));
        if (cRook instanceof Rook) {
            CastleMove cm = new CastleMove(new ChessPosition(4, 7), new ChessPosition(6, 7), cg.currentBoard.getChessPiece(new ChessPosition(4, 7)), null, (Rook) cRook);
            cg.applyChessMove(cm);
            return cg.currentBoard.getBoard()[6][7] instanceof King && cg.currentBoard.getBoard()[5][7] instanceof Rook;
        }
        return false;
    }

    public static boolean castleShortWhiteFalse() {
        ChessGame cg = new ChessGame(null, null);
        ChessPosition g2 = new ChessPosition(5, 6);
        cg.applyChessMove(new ChessMove(g2, new ChessPosition(5, 4), cg.currentBoard.getChessPiece(g2), null));
        //Black
        ChessPosition a7 = new ChessPosition(4, 1);
        cg.applyChessMove(new ChessMove(a7, new ChessPosition(4, 2), cg.currentBoard.getChessPiece(a7), null));
        //White
        ChessPosition f1 = new ChessPosition(6, 6);
        cg.applyChessMove(new ChessMove(f1, new ChessPosition(6, 5), cg.currentBoard.getChessPiece(f1), null));
        //Black
        ChessPosition a6 = new ChessPosition(5, 0);
        cg.applyChessMove(new ChessMove(a6, new ChessPosition(2, 3), cg.currentBoard.getChessPiece(a6), null));
        //White
        ChessPosition g1 = new ChessPosition(5, 7);
        cg.applyChessMove(new ChessMove(g1, new ChessPosition(6, 6), cg.currentBoard.getChessPiece(g1), null));
        //Black
        ChessPosition a5 = new ChessPosition(0, 1);
        cg.applyChessMove(new ChessMove(a5, new ChessPosition(0, 2), cg.currentBoard.getChessPiece(a5), null));
        //White
        ChessPosition b1 = new ChessPosition(6, 7);
        cg.applyChessMove(new ChessMove(b1, new ChessPosition(7, 5), cg.currentBoard.getChessPiece(b1), null));
        //Black
        ChessPosition a4 = new ChessPosition(0, 2);
        cg.applyChessMove(new ChessMove(a4, new ChessPosition(0, 3), cg.currentBoard.getChessPiece(a4), null));
        //System.out.println(cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard,false));
        return cg.currentBoard.WHITE_KING.getPossibleMoves(cg.currentBoard, false).size() == 1;
    }

    public static boolean casteLongBlack() {
        ChessGame cg = new ChessGame(null, null);
        //White
        ChessPosition h2 = new ChessPosition(7, 6);
        ChessPosition h3 = new ChessPosition(7, 5);
        ChessPosition h4 = new ChessPosition(7, 4);
        ChessPosition h5 = new ChessPosition(7, 3);
        ChessPosition h6 = new ChessPosition(7, 2);
        ChessPosition g7 = new ChessPosition(6, 1);
        cg.applyChessMove(new ChessMove(h2, h3, cg.currentBoard.getChessPiece(h2), null));

        //Black
        ChessPosition d8 = new ChessPosition(3, 0);
        ChessPosition d7 = new ChessPosition(3, 1);
        ChessPosition d6 = new ChessPosition(3, 2);
        ChessPosition d5 = new ChessPosition(3, 3);
        ChessPosition c8 = new ChessPosition(2, 0);
        ChessPosition b8 = new ChessPosition(1, 0);
        ChessPosition a6 = new ChessPosition(0, 2);
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
        ChessPiece cRook = cg.currentBoard.getChessPiece(new ChessPosition(0, 0));
        if (cRook instanceof Rook) {
            CastleMove cm = new CastleMove(new ChessPosition(4, 0), new ChessPosition(2, 0), cg.currentBoard.getChessPiece(new ChessPosition(4, 0)), null, (Rook) cRook);
            cg.applyChessMove(cm);
            return cg.currentBoard.getBoard()[2][0] instanceof King && cg.currentBoard.getBoard()[3][0] instanceof Rook;
        }
        return false;
    }

    public static boolean casteLongBlackFalse() {
        ChessGame cg = new ChessGame(null, null);
        //White
        ChessPosition g5 = new ChessPosition(6, 3);
        ChessPosition c1 = new ChessPosition(2, 7);
        ChessPosition d2 = new ChessPosition(3, 6);
        ChessPosition d3 = new ChessPosition(3, 5);
        ChessPosition h2 = new ChessPosition(7, 6);
        ChessPosition h3 = new ChessPosition(7, 5);
        ChessPosition h4 = new ChessPosition(7, 4);
        ChessPosition h5 = new ChessPosition(7, 3);
        ChessPosition h6 = new ChessPosition(7, 2);
        cg.applyChessMove(new ChessMove(h2, h3, cg.currentBoard.getChessPiece(h2), null));

        //Black
        ChessPosition d8 = new ChessPosition(3, 0);
        ChessPosition d7 = new ChessPosition(3, 1);
        ChessPosition d6 = new ChessPosition(3, 2);
        ChessPosition d5 = new ChessPosition(3, 3);
        ChessPosition c8 = new ChessPosition(2, 0);
        ChessPosition b8 = new ChessPosition(1, 0);
        ChessPosition a6 = new ChessPosition(0, 2);
        ChessPosition e7 = new ChessPosition(4, 1);
        ChessPosition e6 = new ChessPosition(4, 2);
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
