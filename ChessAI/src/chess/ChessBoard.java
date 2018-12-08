package chess;

import chess.pieces.*;
import helpers.StringColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {


    public static final boolean ENABLE_STANDARD_PROMOTION_UNIT = true;
    public static final PromotionUnit STANDARD_PROMOTION_UNIT = PromotionUnit.QUEEN;


    private ChessPiece[] board;

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

    public Map<ChessPiece, List<ChessMove>> WHITE_MOVES;
    public Map<ChessPiece, List<ChessMove>> BLACK_MOVES;
    public boolean initialized;
    //Note that the List with the Moves of the enemy color does not contain Moves of the enemy King

    public ChessPiece[] getBoard() {
        return this.board;
    }

    public boolean outOfSync() {
        for (ChessPiece p : WHITE_PIECES) {
            if (!p.equals(this.getChessPiece(p.position))) {
                System.out.println("Piece " + p.toString() + " " + ChessLogic.toStringPosition(p.position)+ " not found on board");
                return false;
            }
        }
        for (ChessPiece p : BLACK_PIECES) {
            if (!p.equals(this.getChessPiece(p.position))) {
                System.out.println("Piece " + p.toString() + " " + ChessLogic.toStringPosition(p.position) + " not found on board");
                return false;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece p = this.getChessPiece(i*8+j);
                if (p != null) {
                    if (!WHITE_PIECES.contains(p) && !BLACK_PIECES.contains(p)) {
                        System.out.println("Piece " + p.toString() + " " +ChessLogic.toStringPosition(p.position)+ " not found in list");
                        return false;
                    }
                } else {
                    for (ChessPiece a : WHITE_PIECES) {
                        if (a.position== (i*8+j)) {
                            System.out.println("Piece " + p.toString() + " " + ChessLogic.toStringPosition(p.position) + " found, but board empty");
                            return false;
                        }
                    }
                    for (ChessPiece a : BLACK_PIECES) {
                        if (a.position==i*8+j) {
                            System.out.println("Piece " + p.toString() + " " + ChessLogic.toStringPosition(p.position) + " found, but board empty");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public ChessBoard() {
        board = new ChessPiece[64];
        this.WHITE_MOVES= new HashMap<>();
        this.BLACK_MOVES= new HashMap<>();
        this.initialized=false;
        WHITE_KING = new King(ChessColor.WHITE, 7*8+4, this);
        WHITE_QUEENS = new ArrayList<>();
        Queen WHITE_QUEEN = new Queen(ChessColor.WHITE, 7*8+3, this);
        WHITE_QUEENS.add(WHITE_QUEEN);
        WHITE_PAWNS = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            WHITE_PAWNS.add(new Pawn(ChessColor.WHITE, 6*8+i, this));
        }
        WHITE_ROOKS = new ArrayList<>();
        {
            WHITE_ROOKS.add(new Rook(ChessColor.WHITE,7*8, this));
            WHITE_ROOKS.add(new Rook(ChessColor.WHITE, 7*8+7, this));
        }
        WHITE_BISHOPS = new ArrayList<>();
        {
            WHITE_BISHOPS.add(new Bishop(ChessColor.WHITE, 7*8+2, this));
            WHITE_BISHOPS.add(new Bishop(ChessColor.WHITE, 5+7*8, this));
        }
        WHITE_KNIGHTS = new ArrayList<>();
        {
            WHITE_KNIGHTS.add(new Knight(ChessColor.WHITE, 7*8+1, this));
            WHITE_KNIGHTS.add(new Knight(ChessColor.WHITE, 7*8+6, this));
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

        BLACK_KING = new King(ChessColor.BLACK, 4, this);
        BLACK_QUEENS = new ArrayList<>();
        Queen BLACK_QUEEN = new Queen(ChessColor.BLACK, 3, this);
        BLACK_QUEENS.add(BLACK_QUEEN);
        BLACK_PAWNS = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            BLACK_PAWNS.add(new Pawn(ChessColor.BLACK, 8+i, this));
        }
        BLACK_ROOKS = new ArrayList<>();
        {
            BLACK_ROOKS.add(new Rook(ChessColor.BLACK, 0, this));
            BLACK_ROOKS.add(new Rook(ChessColor.BLACK, 7, this));
        }
        BLACK_BISHOPS = new ArrayList<>();
        {
            BLACK_BISHOPS.add(new Bishop(ChessColor.BLACK, 2, this));
            BLACK_BISHOPS.add(new Bishop(ChessColor.BLACK, 5, this));
        }
        BLACK_KNIGHTS = new ArrayList<>();
        {
            BLACK_KNIGHTS.add(new Knight(ChessColor.BLACK, 1, this));
            BLACK_KNIGHTS.add(new Knight(ChessColor.BLACK, 6, this));
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
        initMoves(ChessColor.WHITE);
    }

    public ChessBoard(ChessBoard cb) {
        this.board = new ChessPiece[64];
        this.WHITE_MOVES= new HashMap<>();
        this.BLACK_MOVES= new HashMap<>();
        this.initialized=false;
        this.WHITE_PAWNS = new ArrayList<>();
        this.WHITE_QUEENS = new ArrayList<>();
        this.WHITE_ROOKS = new ArrayList<>();
        this.WHITE_BISHOPS = new ArrayList<>();
        this.WHITE_KNIGHTS = new ArrayList<>();
        //Clone Pieces
        for (ChessPiece cp : cb.WHITE_PIECES) {
            if (cp instanceof King) {
                this.WHITE_KING = new King(ChessColor.WHITE, cb.WHITE_KING.position, this);
                this.WHITE_KING.moves = cp.moves;
            } else if (cp instanceof Queen) {
                Queen q = new Queen(ChessColor.WHITE, cp.position, this);
                q.moves = cp.moves;
                this.WHITE_QUEENS.add(q);
            } else if (cp instanceof Pawn) {
                Pawn p = new Pawn(ChessColor.WHITE, cp.position, this);
                p.moves = cp.moves;
                this.WHITE_PAWNS.add(p);
            } else if (cp instanceof Rook) {
                Rook r = new Rook(ChessColor.WHITE, cp.position, this);
                r.moves = cp.moves;
                this.WHITE_ROOKS.add(r);
            } else if (cp instanceof Bishop) {
                Bishop b = new Bishop(ChessColor.WHITE, cp.position, this);
                b.moves = cp.moves;
                this.WHITE_BISHOPS.add(b);
            } else if (cp instanceof Knight) {
                Knight k = new Knight(ChessColor.WHITE, cp.position, this);
                k.moves = cp.moves;
                this.WHITE_KNIGHTS.add(k);
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
        this.BLACK_QUEENS = new ArrayList<>();
        this.BLACK_ROOKS = new ArrayList<>();
        this.BLACK_BISHOPS = new ArrayList<>();
        this.BLACK_KNIGHTS = new ArrayList<>();
        //Clone Pieces
        for (ChessPiece cp : cb.BLACK_PIECES) {
            if (cp instanceof King) {
                this.BLACK_KING = new King(ChessColor.BLACK, cb.BLACK_KING.position, this);
                this.BLACK_KING.moves = cp.moves;
            } else if (cp instanceof Queen) {
                Queen q = new Queen(ChessColor.BLACK, cp.position, this);
                q.moves = cp.moves;
                this.BLACK_QUEENS.add(q);
            } else if (cp instanceof Pawn) {
                Pawn p = new Pawn(ChessColor.BLACK, cp.position, this);
                p.moves = cp.moves;
                this.BLACK_PAWNS.add(p);
            } else if (cp instanceof Rook) {
                Rook r = new Rook(ChessColor.BLACK, cp.position, this);
                r.moves = cp.moves;
                this.BLACK_ROOKS.add(r);
            } else if (cp instanceof Bishop) {
                Bishop b = new Bishop(ChessColor.BLACK, cp.position, this);
                b.moves = cp.moves;
                this.BLACK_BISHOPS.add(b);
            } else if (cp instanceof Knight) {
                Knight k = new Knight(ChessColor.BLACK, cp.position, this);
                k.moves = cp.moves;
                this.BLACK_KNIGHTS.add(k);
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
    public void initMoves(ChessColor move){
        List<ChessPiece> movePieces= move==ChessColor.WHITE?this.WHITE_PIECES:this.BLACK_PIECES;
        List<ChessPiece> enemyPieces= (move==ChessColor.WHITE?this.BLACK_PIECES:this.WHITE_PIECES);
        for(ChessPiece cp: enemyPieces){
            if(cp instanceof  King){
                continue;
            }
            List<ChessMove> moves= cp.getPossibleMoves(this,true);
            if(moves.isEmpty()){
                continue;
            }
            if(move==ChessColor.WHITE){
                this.BLACK_MOVES.put(cp,moves);
            }else{
                this.WHITE_MOVES.put(cp,moves);
            }
        }
        //System.out.println(this.BLACK_MOVES);
        for(ChessPiece cp: movePieces){
            List<ChessMove> moves= cp.getPossibleMoves(this,false);
            if(moves.isEmpty()){
                continue;
            }
            if(move==ChessColor.WHITE){
                this.WHITE_MOVES.put(cp,moves);
            }else{
                this.BLACK_MOVES.put(cp,moves);
            }
        }
        //System.out.println(this.WHITE_MOVES);
        this.initialized=true;
    }

    public ChessBoard applyChessMove(ChessMove cm, ChessColor move) {
        ChessBoard newBoard = new ChessBoard(this);
        ChessPiece oldPiece = null;
        if (cm.old != null) {
            oldPiece = newBoard.getChessPiece(cm.old.position);//Because of en passant.
            newBoard.setChessPiece(oldPiece.position, null);//Because of en passant.
        }
        ChessPiece movedPiece = newBoard.getChessPiece(cm.from);
        movedPiece.moves = cm.moved.moves;
        movedPiece.moves++;
        newBoard.setChessPiece(cm.from, null);
        newBoard.setChessPiece(cm.to, movedPiece);
        if (!(cm instanceof CastleMove)) {
            if (oldPiece != null) {
                oldPiece.onBoard = false;
                if (move == ChessColor.BLACK) {
                    newBoard.BLACK_PIECES.remove(oldPiece);
                } else {
                    newBoard.WHITE_PIECES.remove(oldPiece);
                }
            }
            //Pawn transforms into Queen
            if (movedPiece instanceof Pawn) {
                if (movedPiece.color == ChessColor.WHITE) {
                    if (movedPiece.position<8) {//Y==0, wenn Position <8
                        newBoard.WHITE_PIECES.remove(movedPiece);
                        movedPiece.onBoard = false;
                        PromotionMove pm = null;
                        if (cm instanceof PromotionMove) {
                            pm = (PromotionMove) cm;
                        } else if (ChessBoard.ENABLE_STANDARD_PROMOTION_UNIT) {
                            pm= new PromotionMove(cm.from,cm.to,cm.moved,cm.old,ChessBoard.STANDARD_PROMOTION_UNIT);
                        }else{
                            throw new RuntimeException("Did not specify Promotion Unit!");
                        }
                        newBoard.WHITE_PIECES.add(getPromotionPiece(pm,newBoard,movedPiece.position%8,ChessColor.WHITE));
                    }
                } else {
                    if (movedPiece.position>55) {//Wenn größer 55, ist y==08
                        newBoard.BLACK_PIECES.remove(movedPiece);
                        movedPiece.onBoard = false;
                        PromotionMove pm = null;
                        if (cm instanceof PromotionMove) {
                            pm = (PromotionMove) cm;
                        } else if (ChessBoard.ENABLE_STANDARD_PROMOTION_UNIT) {
                            pm= new PromotionMove(cm.from,cm.to,cm.moved,cm.old,ChessBoard.STANDARD_PROMOTION_UNIT);
                        }else{
                            throw new RuntimeException("Did not specify Promotion Unit!");
                        }
                        newBoard.BLACK_PIECES.add(getPromotionPiece(pm,newBoard,movedPiece.position%8+7*8,ChessColor.BLACK));
                    }
                }
            }
        } else {
            CastleMove castleMove = (CastleMove) cm;
            Rook r = (Rook) (newBoard.getChessPiece(castleMove.r.position));
            newBoard.setChessPiece(castleMove.r.position, null);
            r.moves += 1;
            if (r.position%8 == 0) {
                newBoard.setChessPiece(r.position+1, r);
            } else {
                newBoard.setChessPiece(r.position-1, r);
            }
        }
        newBoard.initMoves(move);
        return newBoard;
    }

    public ChessPiece getPromotionPiece(PromotionMove pm,ChessBoard cb, int cp,ChessColor cc) {
        if(pm.pm==PromotionUnit.QUEEN){
            return new Queen(cc,cp,cb);
        }else if(pm.pm==PromotionUnit.BISHOP){
            return new Bishop(cc,cp,cb);
        }else if(pm.pm==PromotionUnit.KNIGHT){
            return new Knight(cc,cp,cb);
        }else if(pm.pm==PromotionUnit.ROOK){
            return new Rook(cc,cp,cb);
        }
        throw new RuntimeException("Something went terribly wrong!");
    }

    public ChessPiece getChessPiece(int cpos) {
        return this.board[cpos];
    }

    public void setChessPiece(int cpos, ChessPiece cp) {
        this.board[cpos] = cp;
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
                ChessPiece cp = this.getChessPiece(i*8+j);
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
