package chess;

import chess.pieces.ChessPiece;
import chess.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    public ChessBoard currentBoard;
    public ChessColor move;
    public ChessColor winner;
    public ChessGameStatus status;
    public List<ChessMove> moveHistory;
    public List<ChessBoard> boardHistory;
    public int fiftyDrawMoves;

    public ChessGame() {
        this.moveHistory = new ArrayList<>();
        this.boardHistory = new ArrayList<>();
        this.move = ChessColor.WHITE;
        this.status = ChessGameStatus.INGAME;
        this.currentBoard = new ChessBoard();
        this.boardHistory.add(this.currentBoard);
        this.fiftyDrawMoves=0;
    }


    //TODO Stellungswiederholung
    public void applyChessMove(ChessMove cm) {
        this.moveHistory.add(cm);
        this.fiftyDrawMoves+=1;
        if (this.status == ChessGameStatus.INGAME) {
            ChessPiece movedPiece = this.currentBoard.getChessPiece(cm.from);
            if (movedPiece != null) {
                if (movedPiece.color == this.move) {
                    this.move = movedPiece.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
                    List<ChessMove> moves = movedPiece.getPossibleMoves(this.currentBoard, false);
                    if (moves.contains(cm)) {
                        if(movedPiece instanceof  Pawn){
                            fiftyDrawMoves=0;
                        }
                        int availablePieces= this.currentBoard.WHITE_PIECES.size()+this.currentBoard.BLACK_PIECES.size();
                        this.currentBoard = this.currentBoard.applyChessMove(cm, this.move);
                        if(this.currentBoard.WHITE_PIECES.size()+this.currentBoard.BLACK_PIECES.size()<availablePieces){
                            fiftyDrawMoves=0;
                        }
                    } else {
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
                    } else if(this.fiftyDrawMoves==100){
                        this.status=ChessGameStatus.DRAW;
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
    public void printBoardHistory(){
        for(ChessBoard cb: this.boardHistory){
            System.out.println(cb.toString());
            System.out.println(cb.outOfSync());
            System.out.println("");
        }
    }
    @Override
    public String toString() {
        return this.status + " , Move has Color: " + this.move + " Board: \n" + this.currentBoard.toString();
    }
}
