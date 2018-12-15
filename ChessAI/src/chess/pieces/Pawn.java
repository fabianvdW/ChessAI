package chess.pieces;

import chess.*;


import java.util.*;

public class Pawn extends ChessPiece {
    public ChessVector[] minimalUnit;
    public int incrementor;

    public Pawn(ChessColor color, ChessPosition position, ChessBoard board) {
        super(color, position, board);
        if (this.color == ChessColor.WHITE) {
            this.representation = "\u2659";
        } else {
            this.representation = "\u265F";
        }
        incrementor = this.color == ChessColor.WHITE ? -1 : 1;
        this.minimalUnit = new ChessVector[4];
        this.minimalUnit[0] = new ChessVector(0, incrementor);
        this.minimalUnit[1] = new ChessVector(0, 2 * incrementor);
        this.minimalUnit[2] = new ChessVector(1, incrementor);
        this.minimalUnit[3] = new ChessVector(-1, incrementor);
    }


    @Override
    public List<ChessMove> getPossibleMoves(ChessBoard b, boolean pinFlag) {
        List<ChessMove> result = new ArrayList<>();
        if (b.initialized) {
            if (this.color == ChessColor.WHITE) {
                return b.WHITE_MOVES.getOrDefault(this, result);
            } else {
                return b.BLACK_MOVES.getOrDefault(this, result);
            }
        }
        ChessColor enemyColor = this.color == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        ChessPiece myKing = this.color == ChessColor.WHITE ? b.WHITE_KING : b.BLACK_KING;
        for (int i = 0; i < this.minimalUnit.length; i++) {
            ChessVector cv = this.minimalUnit[i];
            ChessPosition cp = this.position.addChessVector(cv);
            if (cp == null) {
                continue;
            }
            ChessPiece cpPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(this.position.clone(), cp, this, cpPiece);
            if (!pinFlag && ChessLogic.isPositionThreatened(myKing.position, cm, b, enemyColor)) {
                continue;
            }
            if (i == 0) {
                if (cpPiece == null) {
                    result.add(cm);
                }
            } else if (i == 1) {
                if(this.moves==0&&b.getChessPiece(cp)==null&& b.getChessPiece(cp.addChessVector(new ChessVector(0,-this.incrementor)))==null){
                    result.add(cm);
                }
            } else {
                if (cpPiece != null && cpPiece.color == enemyColor) {
                    result.add(cm);
                } else if (cpPiece == null) {
                    //En Passant
                    if (ChessLogic.canEnPassant(b, this, cv, enemyColor)) {
                        cm.old = b.getChessPiece(cp.addChessVector(new ChessVector(0, -this.incrementor)));
                        result.add(cm);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pawn) {
            Pawn b = (Pawn) o;
            return b.position.equals(this.position);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return (this.position.getX()+this.position.getY()*8)*(this.color==ChessColor.WHITE?2:1);
    }

}
