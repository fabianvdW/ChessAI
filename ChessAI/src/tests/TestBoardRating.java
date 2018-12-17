package tests;

import chess.ChessGameStatus;
import chess.bitboards.BitBoard;
import chess.uci.advancedclient.BoardRating;
import chess.uci.advancedclient.ChessAIOneGoInstance;
import helpers.FENLoader;

public class TestBoardRating {
    public static void main(String[] args) {
        final String board[][] = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}};
        BitBoard bb =BitBoard.toBitBoard(board,true);
        bb.initBoard();
        bb.status=ChessGameStatus.INGAME;
        System.out.println(BoardRating.getBoardRating(bb,0));
    }
}
