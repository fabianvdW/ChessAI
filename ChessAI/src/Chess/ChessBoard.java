package Chess;

import Chess.pieces.*;
import helpers.StringColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class ChessBoard {
    private ChessPiece[][] board;

    public List<ChessPiece> WHITE_PIECES;
    public ChessPiece WHITE_KING;
    public List<ChessPiece> WHITE_QUEENS;
    public List<ChessPiece> WHITE_PAWNS;
    public List<ChessPiece> WHITE_ROOKS;
    public List<ChessPiece> WHITE_BISHOPS;
    public List<ChessPiece> WHITE_KNIGHTS;

    public List<ChessPiece> BLACK_PIECES;
    public ChessPiece BLACK_KING;
    public List<ChessPiece> BLACK_QUEENS;
    public List<ChessPiece> BLACK_PAWNS;
    public List<ChessPiece> BLACK_ROOKS;
    public List<ChessPiece> BLACK_BISHOPS;
    public List<ChessPiece> BLACK_KNIGHTS;

    public ChessPiece[][] getBoard() {
        return this.board;
    }

    public boolean outOfSync() {
        for (ChessPiece p : WHITE_PIECES) {
            if (!p.equals(this.getChessPiece(p.position))) {
                System.out.println("Piece " + p.toString() + " " + p.position.toString() + " not found on board");
                return false;
            }
        }
        for (ChessPiece p : BLACK_PIECES) {
            if (!p.equals(this.getChessPiece(p.position))) {
                System.out.println("Piece " + p.toString() + " " + p.position.toString() + " not found on board");
                return false;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece p = this.getChessPiece(new ChessPosition(i, j));
                if (p != null) {
                    if (!WHITE_PIECES.contains(p) && !BLACK_PIECES.contains(p)) {
                        System.out.println("Piece " + p.toString() + " " + p.position.toString() + " not found in list");
                        return false;
                    }
                } else {
                    for (ChessPiece a : WHITE_PIECES) {
                        if (a.position.equals(new ChessPosition(i, j))) {
                            System.out.println("Piece " + p.toString() + " " + p.position.toString() + " found, but board empty");
                            return false;
                        }
                    }
                    for (ChessPiece a : BLACK_PIECES) {
                        if (a.position.equals(new ChessPosition(i, j))) {
                            System.out.println("Piece " + p.toString() + " " + p.position.toString() + " found, but board empty");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public ChessBoard() {
        board = new ChessPiece[8][8];
        WHITE_KING = new King(ChessColor.WHITE, new ChessPosition(4, 7), this);
        WHITE_QUEENS= new ArrayList<>();
        Queen WHITE_QUEEN = new Queen(ChessColor.WHITE, new ChessPosition(3, 7), this);
        WHITE_QUEENS.add(WHITE_QUEEN);
        WHITE_PAWNS = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            WHITE_PAWNS.add(new Pawn(ChessColor.WHITE, new ChessPosition(i, 6), this));
        }
        WHITE_ROOKS = new ArrayList<>();
        {
            WHITE_ROOKS.add(new Rook(ChessColor.WHITE, new ChessPosition(0, 7), this));
            WHITE_ROOKS.add(new Rook(ChessColor.WHITE, new ChessPosition(7, 7), this));
        }
        WHITE_BISHOPS = new ArrayList<>();
        {
            WHITE_BISHOPS.add(new Bishop(ChessColor.WHITE, new ChessPosition(2, 7), this));
            WHITE_BISHOPS.add(new Bishop(ChessColor.WHITE, new ChessPosition(5, 7), this));
        }
        WHITE_KNIGHTS = new ArrayList<>();
        {
            WHITE_KNIGHTS.add(new Knight(ChessColor.WHITE, new ChessPosition(1, 7), this));
            WHITE_KNIGHTS.add(new Knight(ChessColor.WHITE, new ChessPosition(6, 7), this));
        }
        {
            WHITE_PIECES = new ArrayList<>();
            WHITE_PIECES.add(WHITE_KING);
            WHITE_PIECES.addAll(WHITE_QUEENS);
            WHITE_PIECES.addAll(WHITE_PAWNS);
            WHITE_PIECES.addAll(WHITE_ROOKS);
            WHITE_PIECES.addAll(WHITE_BISHOPS);
            WHITE_PIECES.addAll(WHITE_KNIGHTS);
        }

        BLACK_KING = new King(ChessColor.BLACK, new ChessPosition(4, 0), this);
        BLACK_QUEENS= new ArrayList<>();
        Queen BLACK_QUEEN = new Queen(ChessColor.BLACK, new ChessPosition(3, 0), this);
        BLACK_QUEENS.add(BLACK_QUEEN);
        BLACK_PAWNS = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            BLACK_PAWNS.add(new Pawn(ChessColor.BLACK, new ChessPosition(i, 1), this));
        }
        BLACK_ROOKS = new ArrayList<>();
        {
            BLACK_ROOKS.add(new Rook(ChessColor.BLACK, new ChessPosition(0, 0), this));
            BLACK_ROOKS.add(new Rook(ChessColor.BLACK, new ChessPosition(7, 0), this));
        }
        BLACK_BISHOPS = new ArrayList<>();
        {
            BLACK_BISHOPS.add(new Bishop(ChessColor.BLACK, new ChessPosition(2, 0), this));
            BLACK_BISHOPS.add(new Bishop(ChessColor.BLACK, new ChessPosition(5, 0), this));
        }
        BLACK_KNIGHTS = new ArrayList<>();
        {
            BLACK_KNIGHTS.add(new Knight(ChessColor.BLACK, new ChessPosition(1, 0), this));
            BLACK_KNIGHTS.add(new Knight(ChessColor.BLACK, new ChessPosition(6, 0), this));
        }
        {
            BLACK_PIECES = new ArrayList<>();
            BLACK_PIECES.add(BLACK_KING);
            BLACK_PIECES.addAll(BLACK_QUEENS);
            BLACK_PIECES.addAll(BLACK_PAWNS);
            BLACK_PIECES.addAll(BLACK_ROOKS);
            BLACK_PIECES.addAll(BLACK_BISHOPS);
            BLACK_PIECES.addAll(BLACK_KNIGHTS);
        }
    }

    public ChessBoard(ChessBoard cb) {
        this.board = new ChessPiece[8][8];
        this.WHITE_PAWNS = new ArrayList<>();
        this.WHITE_QUEENS= new ArrayList<>();
        this.WHITE_ROOKS = new ArrayList<>();
        this.WHITE_BISHOPS = new ArrayList<>();
        this.WHITE_KNIGHTS = new ArrayList<>();
        //Clone Pieces
        for (ChessPiece cp : cb.WHITE_PIECES) {
            if (cp instanceof King) {
                this.WHITE_KING = new King(ChessColor.WHITE, cb.WHITE_KING.position.clone(), this);
            } else if(cp instanceof Queen) {
                this.WHITE_QUEENS.add(new Queen(ChessColor.WHITE,cp.position.clone(),this));
            }else if (cp instanceof Pawn){
                this.WHITE_PAWNS.add(new Pawn(ChessColor.WHITE,cp.position.clone(),this));
            }else if(cp instanceof Rook){
                this.WHITE_ROOKS.add(new Rook(ChessColor.WHITE,cp.position,this));
            }else if(cp instanceof Bishop){
                this.WHITE_BISHOPS.add(new Bishop(ChessColor.WHITE,cp.position.clone(),this));
            }else if(cp instanceof Knight){
                this.WHITE_KNIGHTS.add(new Knight(ChessColor.WHITE,cp.position.clone(),this));
            }
        }
        {
            WHITE_PIECES = new ArrayList<>();
            WHITE_PIECES.add(WHITE_KING);
            WHITE_PIECES.addAll(WHITE_QUEENS);
            WHITE_PIECES.addAll(WHITE_PAWNS);
            WHITE_PIECES.addAll(WHITE_ROOKS);
            WHITE_PIECES.addAll(WHITE_BISHOPS);
            WHITE_PIECES.addAll(WHITE_KNIGHTS);
        }

        this.BLACK_PAWNS = new ArrayList<>();
        this.BLACK_QUEENS= new ArrayList<>();
        this.BLACK_ROOKS = new ArrayList<>();
        this.BLACK_BISHOPS = new ArrayList<>();
        this.BLACK_KNIGHTS = new ArrayList<>();
        //Clone Pieces
        for (ChessPiece cp : cb.BLACK_PIECES) {
            if (cp instanceof King) {
                this.BLACK_KING = new King(ChessColor.BLACK, cb.BLACK_KING.position.clone(), this);
            } else if(cp instanceof Queen) {
                this.BLACK_QUEENS.add(new Queen(ChessColor.BLACK,cp.position.clone(),this));
            }else if (cp instanceof Pawn){
                this.BLACK_PAWNS.add(new Pawn(ChessColor.BLACK,cp.position.clone(),this));
            }else if(cp instanceof Rook){
                this.BLACK_ROOKS.add(new Rook(ChessColor.BLACK,cp.position,this));
            }else if(cp instanceof Bishop){
                this.BLACK_BISHOPS.add(new Bishop(ChessColor.BLACK,cp.position.clone(),this));
            }else if(cp instanceof Knight){
                this.BLACK_KNIGHTS.add(new Knight(ChessColor.BLACK,cp.position.clone(),this));
            }
        }
        {
            BLACK_PIECES = new ArrayList<>();
            BLACK_PIECES.add(BLACK_KING);
            BLACK_PIECES.addAll(BLACK_QUEENS);
            BLACK_PIECES.addAll(BLACK_PAWNS);
            BLACK_PIECES.addAll(BLACK_ROOKS);
            BLACK_PIECES.addAll(BLACK_BISHOPS);
            BLACK_PIECES.addAll(BLACK_KNIGHTS);
        }
    }

    public ChessPiece getChessPiece(ChessPosition cpos) {
        return this.board[cpos.getX()][cpos.getY()];
    }

    public void setChessPiece(ChessPosition cpos, ChessPiece cp) {
        this.board[cpos.getX()][cpos.getY()] = cp;
        if (cp != null) {
            cp.position = cpos;
            cp.onBoard = true;
        }
    }


    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s += StringColor.RESET;
                if (j == 0) {
                    s += "|";
                }
                s += "\t";
                ChessPiece cp = this.getChessPiece(new ChessPosition(j, i));
                if (cp == null) {
                    s += "";
                } else {
                    if (cp.color == ChessColor.WHITE) {
                        s += StringColor.BLACK;

                    } else {
                        s += StringColor.YELLOW;
                    }
                    s += cp.representation;
                }
                s += StringColor.RESET;
                s += "\t";
                s += "|";
            }
            s += "\n";
        }
        return s;
    }
}
