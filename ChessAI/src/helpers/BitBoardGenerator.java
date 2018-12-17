package helpers;

import chess.*;
import chess.bitboards.BitBoard;
import chess.bitboards.BitBoardMoves;
import chess.pieces.King;
import chess.pieces.Knight;

import java.util.List;

public class BitBoardGenerator {
    public static void main(String[] args) {
        System.out.println(generateQuadrantBitBoards());
    }
    public static String generateQuadrantBitBoards(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        //Quadrant top left
        long leftFiles=(BitBoardMoves.FILES[BitBoardMoves.FILE_A]|BitBoardMoves.FILES[BitBoardMoves.FILE_B]|BitBoardMoves.FILES[BitBoardMoves.FILE_C]|BitBoardMoves.FILES[BitBoardMoves.FILE_D]);
        long rightFiles=(BitBoardMoves.FILES[BitBoardMoves.FILE_E]|BitBoardMoves.FILES[BitBoardMoves.FILE_F]|BitBoardMoves.FILES[BitBoardMoves.FILE_G]|BitBoardMoves.FILES[BitBoardMoves.FILE_H]);
        long topRanks=(BitBoardMoves.RANKS[BitBoardMoves.RANK_8]|BitBoardMoves.RANKS[BitBoardMoves.RANK_7]|BitBoardMoves.RANKS[BitBoardMoves.RANK_6]|BitBoardMoves.RANKS[BitBoardMoves.RANK_5]);
        long bottomRanks=(BitBoardMoves.RANKS[BitBoardMoves.RANK_4]|BitBoardMoves.RANKS[BitBoardMoves.RANK_3]|BitBoardMoves.RANKS[BitBoardMoves.RANK_2]|BitBoardMoves.RANKS[BitBoardMoves.RANK_1]);

        long topLeft=leftFiles&topRanks;
        long topRight=rightFiles&topRanks;
        long bottomLeft=leftFiles&bottomRanks;
        long bottomRight=rightFiles&bottomRanks;
        //System.out.println(BitBoard.getOneBitBoardString(topLeft));
        //System.out.println(BitBoard.getOneBitBoardString(topRight));
        //System.out.println(BitBoard.getOneBitBoardString(bottomLeft));
        //System.out.println(BitBoard.getOneBitBoardString(bottomRight));
        sb.append(String.format("0x%016x",topLeft)+ "L,");
        sb.append(String.format("0x%016x",topRight)+ "L,");
        sb.append(String.format("0x%016x",bottomLeft)+ "L,");
        sb.append(String.format("0x%016x",bottomRight)+ "L,");
        sb.append("};");
        return sb.toString();
    }
    public static String generateKingBitBoards(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < 64; i++) {
            ChessBoard cb = new ChessBoard();
            ChessLogic.clearBoard(cb);
            cb.setChessPiece(new ChessPosition(i % 8, i / 8), new King(ChessColor.WHITE, new ChessPosition(i % 8, i / 8), cb));
            cb.initialized = false;
            List<ChessMove> moves = cb.getChessPiece(new ChessPosition(i % 8, i / 8)).getPossibleMoves(cb, true);
            //System.out.println(moves.size());
            ChessBoard cb2 = new ChessBoard();
            ChessLogic.clearBoard(cb2);
            for (ChessMove cm : moves) {
                cb2.setChessPiece(cm.to, new Knight(ChessColor.WHITE, cm.to, cb2));
            }
            //System.out.println(cb2);
            sb.append(String.format("0x%016x", new BitBoard(cb2.getBoard(), true).whitePieces[BitBoard.KNIGHTS]) + "L,");
        }
        sb.append("};");
        return sb.toString();
    }

    public static String generateKnightBitBoards() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < 64; i++) {
            ChessBoard cb = new ChessBoard();
            ChessLogic.clearBoard(cb);
            cb.setChessPiece(new ChessPosition(i % 8, i / 8), new Knight(ChessColor.WHITE, new ChessPosition(i % 8, i / 8), cb));
            cb.initialized = false;
            List<ChessMove> moves = cb.getChessPiece(new ChessPosition(i % 8, i / 8)).getPossibleMoves(cb, true);
            //System.out.println(moves.size());
            ChessBoard cb2 = new ChessBoard();
            ChessLogic.clearBoard(cb2);
            for (ChessMove cm : moves) {
                cb2.setChessPiece(cm.to, new Knight(ChessColor.WHITE, cm.to, cb2));
            }
            //System.out.println(cb2);
            sb.append(String.format("0x%016x", new BitBoard(cb2.getBoard(), true).whitePieces[BitBoard.KNIGHTS]) + "L,");
        }
        sb.append("};");
        return sb.toString();
    }

    public static String generateDiagonalMask() {
        //File sind Spalten
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        long bb = 1L;
        sb.append(String.format("0x%016X", bb) + "L");
        sb.append(",");
        for (int i = 0; i < 7; i++) {
            bb <<= 7 - i;
            bb += 1L;
            bb <<= i + 1;
            sb.append(String.format("0x%016X", bb) + "L");
            sb.append(",");
        }
        for (int i = 0; i < 7; i++) {
            bb <<= 8;
            sb.append(String.format("0x%016X", bb) + "L");
            sb.append(",");
        }
        sb.append("};");
        return sb.toString();
    }

    public static String generateAntiDiagonalMask() {
        //File sind Spalten
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        long bb = 0x80L;
        sb.append(String.format("0x%016X", bb) + "L");
        sb.append(",");
        for (int i = 0; i < 7; i++) {
            bb <<= i + 2;
            bb += 1L;
            bb <<= 8 - i - 2;
            sb.append(String.format("0x%016X", bb) + "L");
            sb.append(",");
        }
        for (int i = 0; i < 7; i++) {
            bb <<= 8;
            sb.append(String.format("0x%016X", bb) + "L");
            sb.append(",");
        }
        sb.append("};");
        return sb.toString();
    }

    public static String generateFileBitBoards() {
        //File sind Spalten
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < 8; i++) {
            long bb = 0L;
            for (int j = 0; j < 64; j++) {
                bb <<= 1;
                if (j % 8 == i) {
                    bb += 1L;
                }
            }
            sb.append(String.format("0x%016X", bb) + "L");
            if (i != 7) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String generateRankBitBoards() {
        //File sind Spalten
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < 8; i++) {
            long bb = 0L;
            for (int j = 0; j < 64; j++) {
                bb <<= 1;
                if (j / 8 == i) {
                    bb += 1L;
                }
            }
            sb.append(String.format("0x%016X", bb) + "L");
            if (i != 7) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
