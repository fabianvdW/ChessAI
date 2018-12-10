package chess;

import chess.pieces.*;
import helpers.Constants;
import helpers.StringColor;

public class BitBoard {
    public long whitePawns;
    public long whiteKing;
    public long whiteKnights;
    public long whiteBishops;
    public long whiteRooks;
    public long whiteQueens;
    public long blackPawns;
    public long blackKing;
    public long blackKnights;
    public long blackBishops;
    public long blackRooks;
    public long blackQueens;
    public String moveHistory;
    public BitBoardMoves bm;
    public boolean move;

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
        //timeTest();
        String chessBoard[][] = {
                {"r", "n", "b", "q", "k", "b", "n", "r"},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", "", " "},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {"R", "N", "B", "Q", "K", "B", "N", "R"}};
        BitBoard bb = BitBoard.toBitBoard(chessBoard, true);
        bb.initBoard();
        //System.out.println(bb.bm.possibleMoves);
        //System.out.println(BitBoard.getOneBitBoardString(bb.bm.horizontalAndVerticalMoves(36)));
        System.out.println(bb.bm.possibleMoves);
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
    public final static long[] DIAGONAL_MASK = {0x0000000000000001L, 0x0000000000000102L, 0x0000000000010204L, 0x0000000001020408L, 0x0000000102040810L, 0x0000010204081020L, 0x0001020408102040L, 0x0102040810204080L, 0x0204081020408000L, 0x0408102040800000L, 0x0810204080000000L, 0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L,};
    public final static long[] ANTIDIAGONAL_MASK = {0x0000000000000080L, 0x0000000000008040L, 0x0000000000804020L, 0x0000000080402010L, 0x0000008040201008L, 0x0000804020100804L, 0x0080402010080402L, 0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L, 0x0804020100000000L, 0x0402010000000000L, 0x0201000000000000L, 0x0100000000000000L,};
    public final static long[] KNIGHT_MOVES = {0x0020400000000000L, 0x0010a00000000000L, 0x0088500000000000L, 0x0044280000000000L, 0x0022140000000000L, 0x00110a0000000000L, 0x0008050000000000L, 0x0004020000000000L, 0x2000204000000000L, 0x100010a000000000L, 0x8800885000000000L, 0x4400442800000000L, 0x2200221400000000L, 0x1100110a00000000L, 0x0800080500000000L, 0x0400040200000000L, 0x4020002040000000L, 0xa0100010a0000000L, 0x5088008850000000L, 0x2844004428000000L, 0x1422002214000000L, 0x0a1100110a000000L, 0x0508000805000000L, 0x0204000402000000L, 0x0040200020400000L, 0x00a0100010a00000L, 0x0050880088500000L, 0x0028440044280000L, 0x0014220022140000L, 0x000a1100110a0000L, 0x0005080008050000L, 0x0002040004020000L, 0x0000402000204000L, 0x0000a0100010a000L, 0x0000508800885000L, 0x0000284400442800L, 0x0000142200221400L, 0x00000a1100110a00L, 0x0000050800080500L, 0x0000020400040200L, 0x0000004020002040L, 0x000000a0100010a0L, 0x0000005088008850L, 0x0000002844004428L, 0x0000001422002214L, 0x0000000a1100110aL, 0x0000000508000805L, 0x0000000204000402L, 0x0000000040200020L, 0x00000000a0100010L, 0x0000000050880088L, 0x0000000028440044L, 0x0000000014220022L, 0x000000000a110011L, 0x0000000005080008L, 0x0000000002040004L, 0x0000000000402000L, 0x0000000000a01000L, 0x0000000000508800L, 0x0000000000284400L, 0x0000000000142200L, 0x00000000000a1100L, 0x0000000000050800L, 0x0000000000020400L,};
    public final static long[] KING_MOVES = {0x40c0000000000000L, 0xa0e0000000000000L, 0x5070000000000000L, 0x2838000000000000L, 0x141c000000000000L, 0x0a0e000000000000L, 0x0507000000000000L, 0x0203000000000000L, 0xc040c00000000000L, 0xe0a0e00000000000L, 0x7050700000000000L, 0x3828380000000000L, 0x1c141c0000000000L, 0x0e0a0e0000000000L, 0x0705070000000000L, 0x0302030000000000L, 0x00c040c000000000L, 0x00e0a0e000000000L, 0x0070507000000000L, 0x0038283800000000L, 0x001c141c00000000L, 0x000e0a0e00000000L, 0x0007050700000000L, 0x0003020300000000L, 0x0000c040c0000000L, 0x0000e0a0e0000000L, 0x0000705070000000L, 0x0000382838000000L, 0x00001c141c000000L, 0x00000e0a0e000000L, 0x0000070507000000L, 0x0000030203000000L, 0x000000c040c00000L, 0x000000e0a0e00000L, 0x0000007050700000L, 0x0000003828380000L, 0x0000001c141c0000L, 0x0000000e0a0e0000L, 0x0000000705070000L, 0x0000000302030000L, 0x00000000c040c000L, 0x00000000e0a0e000L, 0x0000000070507000L, 0x0000000038283800L, 0x000000001c141c00L, 0x000000000e0a0e00L, 0x0000000007050700L, 0x0000000003020300L, 0x0000000000c040c0L, 0x0000000000e0a0e0L, 0x0000000000705070L, 0x0000000000382838L, 0x00000000001c141cL, 0x00000000000e0a0eL, 0x0000000000070507L, 0x0000000000030203L, 0x000000000000c040L, 0x000000000000e0a0L, 0x0000000000007050L, 0x0000000000003828L, 0x0000000000001c14L, 0x0000000000000e0aL, 0x0000000000000705L, 0x0000000000000302L,};


    //Generated at runtime
    public BitBoard bb;
    //Black Pieces
    public long blackPieces;
    public long blackCantCapture;
    //White Pieces
    public long whitePieces;
    public long whiteCantCapture;

    public long emptySquares;
    public long occupiedSquares;

    public boolean move;
    //X1 is in File
    //Y1 is in Rank
    //Moves are of type x1,y1,x2,y2 if they are not a) Castles, b) En passants x1x2 Space E, c) Promotion Moves: x1x2 and then Q,R,B,N and then P
    String possibleMoves;

    public BitBoardMoves(BitBoard bb, boolean move) {
        this.bb = bb;
        this.move = move;
        this.emptySquares = ~(bb.whiteKing | bb.whiteQueens | bb.whiteRooks | bb.whiteBishops | bb.whiteKnights | bb.whitePawns | bb.blackKing | bb.blackQueens | bb.blackRooks | bb.blackBishops | bb.blackKnights | bb.blackPawns);
        occupiedSquares = ~emptySquares;
        if (move) {
            //White cant capture those figures, Black King is in there to prevent illegal moves!
            whiteCantCapture = ~(bb.whitePawns | bb.whiteKnights | bb.whiteBishops | bb.whiteRooks | bb.whiteQueens | bb.whiteKing | bb.blackKing);
            blackPieces = bb.blackBishops | bb.blackQueens | bb.blackRooks | bb.blackKnights | bb.blackPawns;//No Black King in there since it should not be captured
        } else {
            //Black cant capture those figures, White King is in there to prevent illegal moves!
            blackCantCapture = ~(bb.blackPawns | bb.blackKnights | bb.blackBishops | bb.blackRooks | bb.blackQueens | bb.blackKing | bb.whiteKing);
            whitePieces = bb.whiteBishops | bb.whiteQueens | bb.whiteRooks | bb.whiteKnights | bb.whitePawns;//No white King in there since it should not be captured
        }
        if (move) {
            possibleMoves = possiblePawnMovesWhite(bb.whitePawns, bb.blackPawns, blackPieces)
                    + possibleKnightMoves(bb.whiteKnights, whiteCantCapture)
                    + possibleBishopMoves(bb.whiteBishops, whiteCantCapture)
                    + possibleRookMoves(bb.whiteRooks, whiteCantCapture)
                    + possibleQueenMoves(bb.whiteQueens, whiteCantCapture)
                    + possibleKingMoves(bb.whiteKing, whiteCantCapture);
        } else {
            possibleMoves = possiblePawnMovesBlack(bb.blackPawns, bb.whitePawns, whitePieces)
                    + possibleKnightMoves(bb.blackKnights, blackCantCapture)
                    + possibleBishopMoves(bb.blackBishops, blackCantCapture)
                    + possibleRookMoves(bb.blackRooks, blackCantCapture)
                    + possibleQueenMoves(bb.blackQueens, blackCantCapture)
                    + possibleKingMoves(bb.blackKing, blackCantCapture);
        }

    }

    public long horizontalAndVerticalMoves(int index) {
        //Hyperbla Quintessence
        index = 63 - index;
        //Reverse index since we are working on x and y
        long binaryS = 1L << (index);
        long reverseBinaryS = Long.reverse(binaryS);
        //Possibilities on one axis are Left to the Piece or right to the Piece on that axis
        //Left is occupied^(occupied-2*s), Right is: (occupied' ^(o'-2s'))', put it together
        //Mask it accordingly for Vertical and Horizontal
        long possibilitiesHorizontal = ((this.occupiedSquares - 2 * binaryS) ^ Long.reverse(Long.reverse(this.occupiedSquares) - (2 * reverseBinaryS)));
        long occupiedAndFileMasks = this.occupiedSquares & FILES[7 - index % 8];

        long possibilitiesVertical = ((occupiedAndFileMasks) - (2 * binaryS)) ^ Long.reverse(Long.reverse(occupiedAndFileMasks) - (2 * reverseBinaryS));
        return (possibilitiesHorizontal & RANKS[7 - index / 8]) | possibilitiesVertical & FILES[7 - index % 8];

    }

    public long diagonalAndAntiDiagonalMoves(int index) {
        //Hyperbola Quintessence
        index = 63 - index;
        long binaryS = 1L << index;
        long reverseBinaryS = Long.reverse(binaryS);
        //Possibilities on one axis are Left to the Piece or right to the Piece on that axis
        //Left is occupied^(occupied-2*s), Right is: (occupied' ^(o'-2s'))', put it together
        //Mask it accordingly for Diagonal and Anti-Diagonal
        long occupiedAndDiagonal = this.occupiedSquares & DIAGONAL_MASK[index / 8 + index % 8];
        long possibilitiesDiagonal = (occupiedAndDiagonal - (2 * binaryS)) ^ Long.reverse(Long.reverse(occupiedAndDiagonal) - (2 * reverseBinaryS));
        long occupiedAndAntiDiagonal = this.occupiedSquares & ANTIDIAGONAL_MASK[index / 8 + 7 - index % 8];
        long possibilitiesAntiDiagonal = (occupiedAndAntiDiagonal - (2 * binaryS)) ^ Long.reverse(Long.reverse(occupiedAndAntiDiagonal) - (2 * reverseBinaryS));
        return (possibilitiesDiagonal & DIAGONAL_MASK[index / 8 + index % 8]) | possibilitiesAntiDiagonal & ANTIDIAGONAL_MASK[index / 8 + 7 - index % 8];
    }

    public String possiblePawnMovesBlack(long blackPawns, long whitePawns, long whitePieces) {
        StringBuilder sb = new StringBuilder();
        //Capture right BitBoard
        //Make sure there is a Black Piece on the field and we do not capture to the right on the A Destination File
        long moves;
        moves = (blackPawns >>> 7) & whitePieces & ~RANKS[RANK_1] & ~FILES[FILE_H];
        long firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            sb.append("" + (j % 8 + 1) + (j / 8 - 1) + (j % 8) + (j / 8));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }
        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());
        //Capture Left
        //Make sure there is a Black Piece on the field and we do not capture to the left on the H Destination File
        moves = (blackPawns >>> 9) & whitePieces & ~RANKS[RANK_1] & ~FILES[FILE_A];
        firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            sb.append("" + (j % 8 - 1) + (j / 8 - 1) + (j % 8) + (j / 8));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }

        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());

        //Move one forward
        //Make sure the square is empty
        moves = (blackPawns >>> 8) & emptySquares & ~RANKS[RANK_1];
        firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            sb.append("" + (j % 8) + (j / 8 - 1) + (j % 8) + (j / 8));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }

        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());

        //Move two forward
        //Make sure the square is empty and the square on the rank below is empty aswell
        moves = (blackPawns >>> 16) & emptySquares & (emptySquares >>> 8) & RANKS[RANK_5];
        firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            sb.append("" + (j % 8) + (j / 8 - 2) + (j % 8) + (j / 8));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }
        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());

        //PromotionMove, PromotionType
        //Pawn promotion by capture right
        moves = ((blackPawns >>> 7)) & whitePieces & RANKS[RANK_1] & ~FILES[FILE_H];
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

        //Pawn promotion by capture left
        moves = (blackPawns >>> 9) & whitePieces & RANKS[RANK_1] & ~FILES[FILE_A];
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

        moves = (whitePawns >>> 8) & emptySquares & RANKS[RANK_1];
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
            long deletedPawn = (blackPawns << 1) & whitePawns & RANKS[RANK_4] & ~FILES[FILE_H] & FILES[enPassantFile];//Position of deleted Pawn
            if (deletedPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 + 1) + (j % 8) + " E");
            }
            //en passant left
            deletedPawn = (blackPawns >>> 1) & whitePawns & RANKS[RANK_4] & ~FILES[FILE_A] & FILES[enPassantFile];//Position of deleted Pawn
            if (deletedPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 - 1) + (j % 8) + " E");
            }
        }

        return sb.toString();
    }

    public String possiblePawnMovesWhite(long whitePawns, long blackPawns, long blackPieces) {
        StringBuilder sb = new StringBuilder();
        //Capture right BitBoard
        //Make sure there is a Black Piece on the field and we do not capture to the right on the A Destination File
        long moves;
        moves = (whitePawns << 7) & blackPieces & ~RANKS[RANK_8] & ~FILES[FILE_A];
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
        moves = (whitePawns << 9) & blackPieces & ~RANKS[RANK_8] & ~FILES[FILE_H];
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
        moves = (whitePawns << 8) & emptySquares & ~RANKS[RANK_8];
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
        moves = (whitePawns << 16) & emptySquares & (emptySquares << 8) & RANKS[RANK_4];
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
        moves = ((whitePawns << 7)) & blackPieces & RANKS[RANK_8] & ~FILES[FILE_A];
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
        moves = (whitePawns << 9) & blackPieces & RANKS[RANK_8] & ~FILES[FILE_H];
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

        moves = (whitePawns << 8) & emptySquares & RANKS[RANK_8];
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
            long deletedPawn = (whitePawns >> 1) & blackPawns & RANKS[RANK_5] & ~FILES[FILE_A] & FILES[enPassantFile];//Position of deleted Pawn
            if (deletedPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 - 1) + (j % 8) + " E");
            }
            //en passant left
            deletedPawn = (whitePawns << 1) & blackPawns & RANKS[RANK_5] & ~FILES[FILE_H] & FILES[enPassantFile];//Position of deleted Pawn
            if (deletedPawn != 0) {
                int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
                int j = 63 - leadingZeros;
                sb.append("" + (j % 8 + 1) + (j % 8) + " E");
            }
        }

        return sb.toString();
    }

    public String possibleKnightMoves(long knights, long cantCapture) {
        StringBuilder sb = new StringBuilder();
        long knightPossibility = knights & ~(knights - 1);
        long possibility;
        //Iterate through every Knight
        while (knightPossibility != 0) {
            //Use the pre initialized array to find out where the knight can move!
            int knightLocation = 63 - Long.numberOfTrailingZeros(knightPossibility);
            possibility = KNIGHT_MOVES[knightLocation] & cantCapture;
            //Go through every possible move and add.
            long knightDestinationBB = possibility & ~(possibility - 1);
            while (knightDestinationBB != 0) {
                int knightDestination = 63 - Long.numberOfTrailingZeros(knightDestinationBB);
                sb.append("" + (knightLocation % 8) + (knightLocation / 8) + (knightDestination % 8) + (knightDestination / 8));
                possibility &= ~knightDestinationBB;
                knightDestinationBB = possibility & ~(possibility - 1);
            }
            knights &= ~knightPossibility;
            knightPossibility = knights & ~(knights - 1);
        }
        return sb.toString();
    }

    public String possibleBishopMoves(long bishops, long cantCapture) {
        StringBuilder sb = new StringBuilder();
        long wBClone = bishops;
        long bishop = bishops & ~(bishops - 1);
        long possibilityOfMoving;
        //Go through every bishop
        while (bishop != 0) {
            //For every bishop, go through every Position
            int bishopLocation = 63 - Long.numberOfTrailingZeros(bishop);
            possibilityOfMoving = diagonalAndAntiDiagonalMoves(bishopLocation) & cantCapture;
            long position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            while (position != 0) {
                int index = Long.numberOfTrailingZeros(position);
                int j = 63 - index;
                sb.append("" + (bishopLocation % 8) + (bishopLocation / 8) + (j % 8) + (j / 8));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~bishop;
            bishop = wBClone & ~(wBClone - 1);
        }

        return sb.toString();
    }

    public String possibleRookMoves(long rooks, long cantCapture) {
        StringBuilder sb = new StringBuilder();
        long wBClone = rooks;
        long rook = rooks & ~(rooks - 1);
        long possibilityOfMoving;
        //Go through every Rook
        while (rook != 0) {
            //For every Rook, go through every Position
            int rookLocation = 63 - Long.numberOfTrailingZeros(rook);
            possibilityOfMoving = horizontalAndVerticalMoves(rookLocation) & cantCapture;
            long position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            while (position != 0) {
                int index = Long.numberOfTrailingZeros(position);
                int j = 63 - index;
                sb.append("" + (rookLocation % 8) + (rookLocation / 8) + (j % 8) + (j / 8));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~rook;
            rook = wBClone & ~(wBClone - 1);
        }

        return sb.toString();
    }

    public String possibleQueenMoves(long queens, long cantCapture) {
        StringBuilder sb = new StringBuilder();
        long wBClone = queens;
        long queen = queens & ~(queens - 1);
        long possibilityOfMoving;
        //Go through every Queen
        while (queen != 0) {
            //For every Queen, go through every Position
            int queenLocation = 63 - Long.numberOfTrailingZeros(queen);
            possibilityOfMoving = (diagonalAndAntiDiagonalMoves(queenLocation) | horizontalAndVerticalMoves(queenLocation)) & cantCapture;
            long position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            while (position != 0) {
                int index = Long.numberOfTrailingZeros(position);
                int j = 63 - index;
                sb.append("" + (queenLocation % 8) + (queenLocation / 8) + (j % 8) + (j / 8));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~queen;
            queen = wBClone & ~(wBClone - 1);
        }
        return sb.toString();
    }

    public String possibleKingMoves(long king, long cantCapture) {


        StringBuilder sb = new StringBuilder();
        int kingLocation = 63 - Long.numberOfTrailingZeros(king);
        //Use the pre initialized array and get the possible moves for the king location
        long possibility = KING_MOVES[kingLocation] & cantCapture;
        //Go through every possible move
        long kingDestinationBB = possibility & ~(possibility - 1);
        while (kingDestinationBB != 0) {
            int kingDestination = 63 - Long.numberOfTrailingZeros(kingDestinationBB);
            sb.append("" + (kingLocation % 8) + (kingLocation / 8) + (kingDestination % 8) + (kingDestination / 8));
            possibility &= ~kingDestinationBB;
            kingDestinationBB = possibility & ~(possibility - 1);
        }
        return sb.toString();
    }

    public long unsafeForColor(long WP, long WN, long WB, long WR, long WQ, long WK, long BP, long BN, long BB, long BR, long BQ, long BK, boolean color) {
        long unsafe;
        if (color) {
            //White to the right
            unsafe = ((WP << 7)) & ~FILES[FILE_A];
            //White to the left
            unsafe |= ((WP << 9)) & ~FILES[FILE_H];
        } else {
            unsafe = ((BP >>> 7)) & ~FILES[FILE_H];
            unsafe |= ((BP >>> 9)) & ~FILES[FILE_A];
        }
        return unsafe;
    }


}
