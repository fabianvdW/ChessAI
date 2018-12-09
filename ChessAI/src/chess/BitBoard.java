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
        move=true;
    }

    public BitBoard(ChessPiece[][] arr,boolean move) {
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
        this.move=move;
    }

    public BitBoard(ChessPiece[][] arr, String moveHistory,boolean move) {
        this(arr,move);
        this.moveHistory = moveHistory;
    }

    public void initBoard() {
        this.bm = new BitBoardMoves(this,move);
    }

    public static void main(String[] args) {
        ChessBoard cb = new ChessBoard();
        //System.out.println(cb.toString());
        BitBoard bb = new BitBoard(cb.getBoard(),true);
        System.out.println(bb.toString());
        //System.out.println(bb.getBitBoardString());
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
    public final static int RANK_1 = 0;
    public final static int RANK_2 = 1;
    public final static int RANK_3 = 2;
    public final static int RANK_4 = 3;
    public final static int RANK_5 = 4;
    public final static int RANK_6 = 5;
    public final static int RANK_7 = 6;
    public final static int RANK_8 = 7;

    //Generated at runtime
    //Black Pieces
    public long blackPieces;
    public long blackCantCapture;
    //White Pieces
    public long whitePieces;
    public long whiteCantCapture;

    public long emptySquares;

    boolean move;
    String possibleMoves;

    public BitBoardMoves(BitBoard bb,boolean move) {
        this.move=move;
        this.emptySquares=~(bb.whiteKing|bb.whiteQueens|bb.whiteRooks|bb.whiteBishops|bb.whiteKnights|bb.whitePawns|bb.blackKing|bb.blackQueens|bb.blackRooks|bb.blackBishops|bb.blackKnights|bb.blackPawns);
        if(move){
            //White cant capture those figures, Black King is in there to prevent illegal moves!
            whiteCantCapture=~(bb.whitePawns|bb.whiteKnights|bb.whiteBishops|bb.whiteRooks|bb.whiteQueens|bb.whiteKing|bb.blackKing);
            blackPieces=bb.blackBishops|bb.blackQueens|bb.blackRooks|bb.blackKnights|bb.blackKing|bb.blackPawns;
        }else{
            //Black cant capture those figures, White King is in there to prevent illegal moves!
            blackCantCapture=~(bb.blackPawns|bb.blackKnights|bb.blackBishops|bb.blackRooks|bb.blackQueens|bb.blackKing|bb.whiteKing);
            whitePieces=bb.whiteBishops|bb.whiteQueens|bb.whiteRooks|bb.whiteKnights|bb.whiteKing|bb.whitePawns;
        }
        possibleMoves = possiblePawnMoves()
                + possibleKnightMoves()
                + possibleBishopMoves()
                + possibleRookMoves()
                + possibleQueenMoves()
                + possibleKingMoves();

    }

    public String possiblePawnMoves() {
        return "";
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
