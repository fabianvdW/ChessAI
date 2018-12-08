package tests;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.pieces.ChessPiece;
import chess.pieces.Pawn;

public class TestEnPassant {

    public static void main(String[] args) {
        enPassantWhite1();
        enPassantBlack1();
        enPassantWhite2();
        enPassantBlack2();
    }

    public static void enPassantWhite1() {
        ChessGame cg = new ChessGame(null, null);
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 6), new ChessPosition(5, 4), cg.currentBoard.getChessPiece(new ChessPosition(5, 6)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 3), cg.currentBoard.getChessPiece(new ChessPosition(1, 1)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 4), new ChessPosition(5, 3), cg.currentBoard.getChessPiece(new ChessPosition(5, 4)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(4, 1), new ChessPosition(4, 3), cg.currentBoard.getChessPiece(new ChessPosition(4, 1)), null));
        //System.out.println(cg);
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 3), new ChessPosition(4, 2), cg.currentBoard.getChessPiece(new ChessPosition(5, 3)), cg.currentBoard.getChessPiece(new ChessPosition(4, 3))));
        //System.out.println(cg);
        ChessPiece[][] board = cg.currentBoard.getBoard();
        assert (board[4][2] instanceof Pawn);
        assert (board[4][3] == null);
        System.out.println("Test White 1 passed!");

    }

    public static void enPassantBlack1() {
        ChessGame cg = new ChessGame(null, null);
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 6), new ChessPosition(5, 5), cg.currentBoard.getChessPiece(new ChessPosition(5, 6)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 3), cg.currentBoard.getChessPiece(new ChessPosition(1, 1)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(2, 6), new ChessPosition(2, 4), cg.currentBoard.getChessPiece(new ChessPosition(2, 6)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 3), new ChessPosition(1, 4), cg.currentBoard.getChessPiece(new ChessPosition(1, 3)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 5), new ChessPosition(5, 4), cg.currentBoard.getChessPiece(new ChessPosition(5, 5)), null));
        //System.out.println(cg);
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 4), new ChessPosition(2, 5), cg.currentBoard.getChessPiece(new ChessPosition(1, 4)), cg.currentBoard.getChessPiece(new ChessPosition(2, 4))));
        ChessPiece[][] board = cg.currentBoard.getBoard();
        assert (board[2][5] instanceof Pawn);
        assert (board[2][4] == null);
        System.out.println("Test Black 1 passed!");
    }

    public static void enPassantWhite2() {
        ChessGame cg = new ChessGame(null, null);
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 6), new ChessPosition(5, 4), cg.currentBoard.getChessPiece(new ChessPosition(5, 6)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(4, 1), new ChessPosition(4, 2), cg.currentBoard.getChessPiece(new ChessPosition(4, 1)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 4), new ChessPosition(5, 3), cg.currentBoard.getChessPiece(new ChessPosition(5, 4)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(4, 2), new ChessPosition(4, 3), cg.currentBoard.getChessPiece(new ChessPosition(4, 2)), null));
        //System.out.println(cg.currentBoard.getChessPiece(new ChessPosition(5,3)).getPossibleMoves(cg.currentBoard,false));
        assert (cg.currentBoard.getChessPiece(new ChessPosition(5, 3)).getPossibleMoves(cg.currentBoard, false).size() == 1);
        System.out.println("Test White 2 passed!");

    }

    public static void enPassantBlack2() {
        ChessGame cg = new ChessGame(null, null);
        cg.applyChessMove(new ChessMove(new ChessPosition(2, 6), new ChessPosition(2, 5), cg.currentBoard.getChessPiece(new ChessPosition(2, 6)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 1), new ChessPosition(1, 3), cg.currentBoard.getChessPiece(new ChessPosition(1, 1)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(2, 5), new ChessPosition(2, 4), cg.currentBoard.getChessPiece(new ChessPosition(2, 5)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(1, 3), new ChessPosition(1, 4), cg.currentBoard.getChessPiece(new ChessPosition(1, 3)), null));
        cg.applyChessMove(new ChessMove(new ChessPosition(5, 6), new ChessPosition(5, 4), cg.currentBoard.getChessPiece(new ChessPosition(5, 6)), null));
        assert (cg.currentBoard.getChessPiece(new ChessPosition(1, 4)).getPossibleMoves(cg.currentBoard, false).size() == 1);
        System.out.println("Test Black 2 passed!");
    }
}
