package tests;

import chess.*;
import chess.pieces.Bishop;

import java.util.List;

public class TestPiece {
    public static void main(String[] args) {
        boolean end = true;
        while (end) {
            ChessGame cg = new ChessGame();
            while (cg.status == ChessGameStatus.INGAME) {
                List<ChessMove> availableMoves = ChessLogic.getAllPossibleMoves(cg.currentBoard, cg.move);
                ChessMove cm = availableMoves.get((int) (availableMoves.size() * Math.random()));
                cg.applyChessMove(cm);
                if (cm.moved instanceof Bishop) {
                    ChessBoard cb = cg.currentBoard;
                    int xDiff = cm.from.getX() - cm.to.getX();
                    int yDiff = cm.from.getY() - cm.to.getY();
                    int xIncrementor = 0;
                    int yIncrementor = 0;
                    if (xDiff != 0) {
                        if (xDiff < 0) {
                            xIncrementor = 1;
                        } else {
                            xIncrementor = -1;
                        }
                    }
                    if(yDiff!=0) {
                        if (yDiff < 0) {
                            yIncrementor = 1;
                        } else {
                            yIncrementor = -1;
                        }
                    }

                    ChessPosition from = cm.from.clone();
                    while (!from.equals(cm.to)) {
                        if (cg.currentBoard.getChessPiece(from) != null) {
                            System.out.println(cg.boardHistory.get(cg.boardHistory.size() - 2));
                            System.out.println(cg.currentBoard);
                            System.out.println(cm.moved.representation);
                            System.exit(0);
                        }

                        from = new ChessPosition(from.getX() + xIncrementor, from.getY() + yIncrementor);
                    }
                }
            }
        }
    }
}
