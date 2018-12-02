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
    public ChessGame(ChessPlayer player1, ChessPlayer player2){
        this.moveHistory = new ArrayList<>();
        this.boardHistory= new ArrayList<>();
        this.move = ChessColor.WHITE;
        this.status = ChessGameStatus.INGAME;
        this.currentBoard= new ChessBoard();
        this.player1=player1;
        this.player2=player2;
    }

    public void playGame(){

    }
    public void applyChessMove(ChessMove cm) {
        this.moveHistory.add(cm);
        this.boardHistory.add(this.currentBoard);
        this.currentBoard= new ChessBoard(this.currentBoard);
        if (this.status == ChessGameStatus.INGAME) {
            ChessPiece oldPiece = this.currentBoard.getChessPiece(cm.to);
            ChessPiece movedPiece= this.currentBoard.getChessPiece(cm.from);
            if(movedPiece.color == this.move ) {
                this.move = movedPiece.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
                List<ChessMove> moves = movedPiece.getPossibleMoves(this.currentBoard, false);
                if (moves.contains(cm)) {
                    this.currentBoard.setChessPiece(cm.from, null);
                    this.currentBoard.setChessPiece(cm.to, movedPiece);
                    if (oldPiece != null) {
                        oldPiece.onBoard = false;
                        if (this.move == ChessColor.BLACK) {
                            this.currentBoard.BLACK_PIECES.remove(oldPiece);
                        } else {
                            this.currentBoard.WHITE_PIECES.remove(oldPiece);
                        }
                    }

                    //Pawn transforms into Queen
                    if (movedPiece instanceof Pawn) {
                        if (movedPiece.color == ChessColor.WHITE) {
                            if (movedPiece.position.getY() == 0) {
                                this.currentBoard.WHITE_PIECES.remove(movedPiece);
                                movedPiece.onBoard = false;
                                this.currentBoard.WHITE_PIECES.add(new Queen(ChessColor.WHITE, new ChessPosition(movedPiece.position.getX(), 0), this.currentBoard));
                            }
                        } else {
                            if (movedPiece.position.getY() == 7) {
                                this.currentBoard.BLACK_PIECES.remove(movedPiece);
                                movedPiece.onBoard = false;
                                this.currentBoard.BLACK_PIECES.add(new Queen(ChessColor.BLACK, new ChessPosition(movedPiece.position.getX(), 7), this.currentBoard));
                            }
                        }
                    }
                    //StaleMate
                    if (ChessLogic.getAllPossibleMoves(this.currentBoard, this.move).isEmpty()) {
                        this.status = ChessGameStatus.DRAW;
                    }
                    //CheckMate
                    if (ChessLogic.isCheckMate(this.currentBoard, this.move)) {
                        this.status = (movedPiece.color == ChessColor.WHITE ? ChessGameStatus.WHITEWIN : ChessGameStatus.BLACKWIN);
                        this.winner = movedPiece.color;
                    } else if (this.currentBoard.WHITE_PIECES.size() == 1 && this.currentBoard.BLACK_PIECES.size() == 1) {                    //1-King endgame
                        this.status = ChessGameStatus.DRAW;
                    }
                } else {
                    //TODO write specific exception
                    System.out.println(moves);
                    throw new RuntimeException("Illegal Move requested: Piece " + movedPiece.representation + " wants to move to " + cm.to.toString() + " from " + cm.from.toString());
                }
            }else{
                throw new RuntimeException("Wrong color!");
            }
        } else {
            if (this.status == ChessGameStatus.INGAME) {
                throw new RuntimeException("Illegal Move, wrong player!");
            }
        }
    }
    @Override
    public String toString(){
        return this.status+" , Move has Color: "+this.move+" Board: \n"+this.currentBoard.toString();
    }
}
