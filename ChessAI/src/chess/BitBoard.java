package chess;

import chess.pieces.*;
import helpers.Constants;
import helpers.StringColor;

public class BitBoard {
    long whitePawns;
    long whiteKing;
    long whiteKnights;
    long whiteBishops;
    long whiteRooks;
    long whiteQueens;
    long blackPawns;
    long blackKing;
    long blackKnights;
    long blackBishops;
    long blackRooks;
    long blackQueens;
    String moveHistory;
    BitBoardMoves bm;
    boolean move;

    public BitBoard() {
        this.whitePawns = 0x000000000000FF00L;
        this.whiteKing = 0x0000000000000008L;
        this.whiteKnights = 0x0000000000000042L;
        this.whiteBishops = 0x0000000000000024L;
        this.whiteRooks = 0x0000000000000081L;
        this.whiteQueens = 0x0000000000000010L;
        this.blackPawns = 0x00FF000000000000L;
        this.blackKing = 0x0800000000000000L;
        this.blackKnights = 0x4200000000000000L;
        this.blackBishops = 0x2400000000000000L;
        this.blackRooks = 0x8100000000000000L;
        this.blackQueens = 0x1000000000000000L;
        this.moveHistory = "";
        move = true;
    }

    public BitBoard(ChessPiece[][] arr, boolean move) {
        for (int i = 0; i < 64; i++) {
            whitePawns <<= 1;
            whiteKnights <<= 1;
            whiteBishops <<= 1;
            whiteKing <<= 1;
            whiteRooks <<= 1;
            whiteQueens <<= 1;

            blackPawns <<= 1;
            blackKnights <<= 1;
            blackBishops <<= 1;
            blackKing <<= 1;
            blackRooks <<= 1;
            blackQueens <<= 1;
            ChessPiece cp = arr[i % 8][i / 8];
            if (cp instanceof Rook) {
                if (cp.color == ChessColor.WHITE) {
                    whiteRooks += 1L;
                } else {
                    blackRooks += 1L;
                }
            } else if (cp instanceof Bishop) {
                if (cp.color == ChessColor.WHITE) {
                    whiteBishops += 1L;
                } else {
                    blackBishops += 1L;
                }
            } else if (cp instanceof King) {
                if (cp.color == ChessColor.WHITE) {
                    whiteKing += 1L;
                } else {
                    blackKing += 1L;
                }
            } else if (cp instanceof Knight) {
                if (cp.color == ChessColor.WHITE) {
                    whiteKnights += 1L;
                } else {
                    blackKnights += 1L;
                }
            } else if (cp instanceof Pawn) {
                if (cp.color == ChessColor.WHITE) {
                    whitePawns += 1L;
                } else {
                    blackPawns += 1L;
                }
            } else if (cp instanceof Queen) {
                if (cp.color == ChessColor.WHITE) {
                    whiteQueens += 1L;
                } else {
                    blackQueens += 1L;
                }
            }
        }
        this.moveHistory = "";
        this.move = move;
    }

    public BitBoard(ChessPiece[][] arr, String moveHistory, boolean move) {
        this(arr, move);
        this.moveHistory = moveHistory;
    }

    public void initBoard() {
        this.bm = new BitBoardMoves(this, move);
    }

    public static void main(String[] args) {
        timeTest();
        /*String chessBoard[][] = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", "p", "P", "p", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}};
        BitBoard bb = BitBoard.toBitBoard(chessBoard, true);
        bb.moveHistory = "4143";
        bb.initBoard();
        System.out.println(bb.bm.possibleMoves);*/
        //System.out.println(bb.toString());
        //ChessBoard cb = new ChessBoard();
        //System.out.println(cb.toString());
        //BitBoard bb = new BitBoard(cb.getBoard(), true);
        /*
        bb= new BitBoard();
        bb.initBoard();
        System.out.println(bb.toString());

        System.out.println(bb.bm.possibleMoves);
        */
        //System.out.println(BitBoard.getOneBitBoardString(bb.blackBishops));
        //System.out.println(bb.getBitBoardString());
    }

    public static void timeTest() {
        int times = 1000000;
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            BitBoard bb = new BitBoard();
            bb.initBoard();
        }
        long t1 = System.currentTimeMillis();
        System.out.println("Time: " + (t1 - t0));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append("|");
            for (int j = 0; j < 8; j++) {
                int shift = 63 - (j + i * 8);
                sb.append("\t");
                if (((this.whitePawns >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.PAWN_REPRESENTATION_WHITE);
                }
                if (((this.blackPawns >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.PAWN_REPRESENTATION_BLACK);
                }
                if (((this.whiteBishops >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.BISHOP_REPRESENTATION_WHITE);
                }
                if (((this.blackBishops >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.BISHOP_REPRESENTATION_BLACK);
                }
                if (((this.whiteKnights >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KNIGHT_REPRESENTATION_WHITE);
                }
                if (((this.blackKnights >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KNIGHT_REPRESENTATION_BLACK);
                }
                if (((this.whiteKing >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KING_REPRESENTATION_WHITE);
                }
                if (((this.blackKing >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KING_REPRESENTATION_BLACK);
                }
                if (((this.whiteRooks >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.ROOK_REPRESENTATION_WHITE);
                }
                if (((this.blackRooks >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.ROOK_REPRESENTATION_BLACK);
                }
                if (((this.whiteQueens >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.QUEEN_REPRESENTATION_WHITE);
                }
                if (((this.blackQueens >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.QUEEN_REPRESENTATION_BLACK);
                }
                sb.append(StringColor.RESET);
                sb.append("\t");
                if (j != 7) {
                    sb.append("|");
                }
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    public String getBitBoardString() {
        StringBuilder sb = new StringBuilder();
        sb.append("whitePawns Long: " + String.format("0x%016X", whitePawns));
        sb.append("\nwhiteKing Long: " + String.format("0x%016X", whiteKing));
        sb.append("\nwhiteKnights Long: " + String.format("0x%016X", whiteKnights));
        sb.append("\nwhiteBishops Long: " + String.format("0x%016X", whiteBishops));
        sb.append("\nwhiteRooks Long: " + String.format("0x%016X", whiteRooks));
        sb.append("\nwhiteQueens Long: " + String.format("0x%016X", whiteQueens));

        sb.append("\nblackPawns Long: " + String.format("0x%016X", blackPawns));
        sb.append("\nblackKing Long: " + String.format("0x%016X", blackKing));
        sb.append("\nblackKnights Long: " + String.format("0x%016X", blackKnights));
        sb.append("\nblackBishops Long: " + String.format("0x%016X", blackBishops));
        sb.append("\nblackRooks Long: " + String.format("0x%016X", blackRooks));
        sb.append("\nblackQueens Long: " + String.format("0x%016X", blackQueens));
        return sb.toString();
    }

    public static String getOneBitBoardString(long bb) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append("|");
            for (int j = 0; j < 8; j++) {
                int shift = 63 - (j + i * 8);
                sb.append("\t");
                if (((bb >>> shift) & 1) == 1) {
                    sb.append("X");
                }
                sb.append("\t");
                if (j != 7) {
                    sb.append("|");
                }
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    public static BitBoard toBitBoard(String[][] board, boolean move) {
        ChessPiece[][] arr = new ChessPiece[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                String s = board[y][x];
                if (s.equals("r")) {
                    arr[x][y] = new Rook(ChessColor.BLACK, null, null);
                }
                if (s.equals("n")) {
                    arr[x][y] = new Knight(ChessColor.BLACK, null, null);
                }
                if (s.equals("b")) {
                    arr[x][y] = new Bishop(ChessColor.BLACK, null, null);
                }
                if (s.equals("q")) {
                    arr[x][y] = new Queen(ChessColor.BLACK, null, null);
                }
                if (s.equals("k")) {
                    arr[x][y] = new King(ChessColor.BLACK, null, null);
                }
                if (s.equals("p")) {
                    arr[x][y] = new Pawn(ChessColor.BLACK, null, null);
                }
                if (s.equals("R")) {
                    arr[x][y] = new Rook(ChessColor.WHITE, null, null);
                }
                if (s.equals("N")) {
                    arr[x][y] = new Knight(ChessColor.WHITE, null, null);
                }
                if (s.equals("B")) {
                    arr[x][y] = new Bishop(ChessColor.WHITE, null, null);
                }
                if (s.equals("Q")) {
                    arr[x][y] = new Queen(ChessColor.WHITE, null, null);
                }
                if (s.equals("K")) {
                    arr[x][y] = new King(ChessColor.WHITE, null, null);
                }
                if (s.equals("P")) {
                    arr[x][y] = new Pawn(ChessColor.WHITE, null, null);
                }
            }
        }
        return new BitBoard(arr, move);
    }
}

class BitBoardMoves {
    public final static long[] FILES = {0x8080808080808080L, 0x4040404040404040L, 0x2020202020202020L, 0x1010101010101010L, 0x0808080808080808L, 0x0404040404040404L, 0x0202020202020202L, 0x0101010101010101L};
    public final static int FILE_A = 0;
    public final static int FILE_B = 1;
    public final static int FILE_C = 2;
    public final static int FILE_D = 3;
    public final static int FILE_E = 4;
    public final static int FILE_F = 5;
    public final static int FILE_G = 6;
    public final static int FILE_H = 7;
    public final static long[] RANKS = {0xFF00000000000000L, 0x00FF000000000000L, 0x0000FF0000000000L, 0x000000FF00000000L, 0x00000000FF000000L, 0x0000000000FF0000L, 0x000000000000FF00L, 0x00000000000000FFL};
    public final static int RANK_1 = 7;
    public final static int RANK_2 = 6;
    public final static int RANK_3 = 5;
    public final static int RANK_4 = 4;
    public final static int RANK_5 = 3;
    public final static int RANK_6 = 2;
    public final static int RANK_7 = 1;
    public final static int RANK_8 = 0;

    //Generated at runtime
    public BitBoard bb;
    //Black Pieces
    public long blackPieces;
    public long blackCantCapture;
    //White Pieces
    public long whitePieces;
    public long whiteCantCapture;

    public long emptySquares;

    boolean move;
    //X1 is in File
    //Y1 is in Rank
    //Moves are of type x1,y1,x2,y2 if they are not a) Castles, b) En passants x1x2 Space E, c) Promotion Moves: x1x2 and then Q,R,B,N and then P
    String possibleMoves;

    public BitBoardMoves(BitBoard bb, boolean move) {
        this.bb = bb;
        this.move = move;
        this.emptySquares = ~(bb.whiteKing | bb.whiteQueens | bb.whiteRooks | bb.whiteBishops | bb.whiteKnights | bb.whitePawns | bb.blackKing | bb.blackQueens | bb.blackRooks | bb.blackBishops | bb.blackKnights | bb.blackPawns);
        if (move) {
            //White cant capture those figures, Black King is in there to prevent illegal moves!
            whiteCantCapture = ~(bb.whitePawns | bb.whiteKnights | bb.whiteBishops | bb.whiteRooks | bb.whiteQueens | bb.whiteKing | bb.blackKing);
            blackPieces = bb.blackBishops | bb.blackQueens | bb.blackRooks | bb.blackKnights | bb.blackPawns;//No Black King in there since it should not be captured
        } else {
            //Black cant capture those figures, White King is in there to prevent illegal moves!
            blackCantCapture = ~(bb.blackPawns | bb.blackKnights | bb.blackBishops | bb.blackRooks | bb.blackQueens | bb.blackKing | bb.whiteKing);
            whitePieces = bb.whiteBishops | bb.whiteQueens | bb.whiteRooks | bb.whiteKnights | bb.whitePawns;//No white King in there since it should not be captured
        }
        possibleMoves = possiblePawnMoves()
                + possibleKnightMoves()
                + possibleBishopMoves()
                + possibleRookMoves()
                + possibleQueenMoves()
                + possibleKingMoves();

    }

    public String possiblePawnMoves() {
        StringBuilder sb = new StringBuilder();
        if (move) {
            //Capture right BitBoard
            //Make sure there is a Black Piece on the field and we do not capture to the right on the A Destination File
            long moves = (bb.whitePawns << 7) & this.blackPieces & ~RANKS[RANK_8] & ~FILES[FILE_A];
            long firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 - 1) + (j / 8 + 1) + (j % 8) + (j / 8));
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }
            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());
            //Capture Left
            //Make sure there is a Black Piece on the field and we do not capture to the left on the H Destination File

            moves = (bb.whitePawns << 9) & this.blackPieces & ~RANKS[RANK_8] & ~FILES[FILE_H];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 + 1) + (j / 8 + 1) + (j % 8) + (j / 8));
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }

            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            //Move one forward
            //Make sure the square is empty
            moves = (bb.whitePawns << 8) & emptySquares & ~RANKS[RANK_8];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8) + (j / 8 + 1) + (j % 8) + (j / 8));
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }

            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            //Move two forward
            //Make sure the square is empty and the square on the rank below is empty aswell
            moves = (bb.whitePawns << 16) & emptySquares & (emptySquares << 8) & RANKS[RANK_4];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8) + (j / 8 + 2) + (j % 8) + (j / 8));
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }
            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            //PromotionMove, PromotionType
            //Pawn promotion by capture right
            moves = ((bb.whitePawns << 7)) & blackPieces & RANKS[RANK_8] & ~FILES[FILE_A];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                String pos = "" + (j % 8 - 1) + (j % 8);
                sb.append(pos + "QP");
                sb.append(pos + "RP");
                sb.append(pos + "BP");
                sb.append(pos + "NP");
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }
            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            //Pawn promotion by capture left
            moves = (bb.whitePawns << 9) & blackPieces & RANKS[RANK_8] & ~FILES[FILE_H];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                String pos = "" + (j % 8 + 1) + (j % 8);
                sb.append(pos + "QP");
                sb.append(pos + "RP");
                sb.append(pos + "BP");
                sb.append(pos + "NP");
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }

            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            moves = (bb.whitePawns << 8) & emptySquares & RANKS[RANK_8];
            firstPawn = moves & ~(moves - 1);
            while (firstPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(moves);
                int j = 63 - leadingZeros;
                String pos = "" + (j % 8) + (j % 8);
                sb.append(pos + "QP");
                sb.append(pos + "RP");
                sb.append(pos + "BP");
                sb.append(pos + "NP");
                moves &= ~firstPawn;
                firstPawn = moves & ~(moves - 1);
            }

            //System.out.println(BitBoard.getOneBitBoardString(moves));
            //System.out.println("SB: "+sb.toString());

            //En passants
            //Check if last move was pawn move with two forward
            if (bb.moveHistory.length() >= 4 && bb.moveHistory.charAt(bb.moveHistory.length() - 2) == bb.moveHistory.charAt(bb.moveHistory.length() - 4) && Math.abs(bb.moveHistory.charAt(bb.moveHistory.length() - 3) - bb.moveHistory.charAt(bb.moveHistory.length() - 1)) == 2) {
                int enPassantFile = (bb.moveHistory.charAt(bb.moveHistory.length() - 2)) - '0';
                //en passant right
                long deletedPawn = (bb.whitePawns >> 1) & bb.blackPawns & RANKS[RANK_5] & ~FILES[FILE_A] & FILES[enPassantFile];//Position of deleted Pawn
                if (deletedPawn != 0) {
                    int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                    int j = 63 - leadingZeros;
                    sb.append(""+(j % 8 - 1) + (j % 8) + " E");
                }
                //en passant left
                deletedPawn = (bb.whitePawns << 1) & bb.blackPawns & RANKS[RANK_5] & ~FILES[FILE_H] & FILES[enPassantFile];//Position of deleted Pawn
                if (deletedPawn != 0) {
                    int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                    int j = 63 - leadingZeros;
                    sb.append(""+(j % 8 + 1) + (j % 8) + " E");
                }
            }
        } else {

        }
        return sb.toString();
    }

    public String possibleKnightMoves() {
        return "";
    }

    public String possibleBishopMoves() {
        return "";
    }

    public String possibleRookMoves() {
        return "";
    }

    public String possibleQueenMoves() {
        return "";
    }

    public String possibleKingMoves() {
        return "";
    }


}
