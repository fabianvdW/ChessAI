package Chess;

import Chess.pieces.ChessPiece;
import Chess.pieces.Pawn;
import Chess.pieces.Queen;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    public ChessBoard currentBoard;
    public ChessColor move;
    public ChessColor winner;
    public ChessGameStatus status;
    public List<ChessMove> moveHistory;
    public List<ChessBoard> boardHistory;
    public ChessPlayer player1;
    public ChessPlayer player2;

    public ChessGame(ChessPlayer player1, ChessPlayer player2) {
        this.moveHistory = new ArrayList<>();
        this.boardHistory = new ArrayList<>();
        this.move = ChessColor.WHITE;
        this.status = ChessGameStatus.INGAME;
        this.currentBoard = new ChessBoard();
        this.boardHistory.add(this.currentBoard);
        this.player1 = player1;
        this.player2 = player2;
    }

    public void playGame() {

    }

    //TODO Stellungswiederholung, Promotion aussuchen, 50Zuege-Regel
    public void applyChessMove(ChessMove cm) {
        this.moveHistory.add(cm);
        if (this.status == ChessGameStatus.INGAME) {
            ChessPiece movedPiece = this.currentBoard.getChessPiece(cm.from);
            if (movedPiece != null) {
                if (movedPiece.color == this.move) {
                    this.move = movedPiece.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
                    List<ChessMove> moves = movedPiece.getPossibleMoves(this.currentBoard, false);
                    if (moves.contains(cm)) {
                        this.currentBoard = this.currentBoard.applyChessMove(cm, this.move);
                    } else {
                        //TODO write specific exception
                        throw new RuntimeException("Illegal Move requested: Piece " + movedPiece.representation + " wants to move to " + cm.to.toString() + " from " + cm.from.toString());
                    }
                    //StaleMate
                    if (ChessLogic.getAllPossibleMoves(this.currentBoard, this.move).isEmpty()) {
                        this.status = ChessGameStatus.DRAW;
                    }
                    //CheckMate
                    if (ChessLogic.isCheckMate(this.currentBoard, this.move)) {
                        this.status = (movedPiece.color == ChessColor.WHITE ? ChessGameStatus.WHITEWIN : ChessGameStatus.BLACKWIN);
                        this.winner = movedPiece.color;
                    } else if (this.currentBoard.WHITE_PIECES.size() == 1 && this.currentBoard.BLACK_PIECES.size() == 1) {//Lack of Material draw
                        this.status = ChessGameStatus.DRAW;
                    }
                } else {
                    throw new RuntimeException("Move is for the wrong color!");
                }
            } else {
                throw new RuntimeException("Cant move null piece!");
            }
        } else {
            throw new RuntimeException("Cant apply any moves when not ingame!");
        }
        this.boardHistory.add(this.currentBoard);
    }

    @Override
    public String toString() {
        return this.status + " , Move has Color: " + this.move + " Board: \n" + this.currentBoard.toString();
    }
}
