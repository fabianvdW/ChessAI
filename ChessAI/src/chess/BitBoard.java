package chess;

import chess.pieces.*;
import helpers.Constants;
import helpers.StringColor;

import java.util.ArrayList;
import java.util.List;

public class BitBoard {
    public long[] whitePieces;
    public final static int WHITE_PAWNS = 0;
    public final static int WHITE_KING = 1;
    public final static int WHITE_KNIGHTS = 2;
    public final static int WHITE_BISHOPS = 3;
    public final static int WHITE_ROOKS = 4;
    public final static int WHITE_QUEENS = 5;

    public long[] blackPieces;
    public final static int BLACK_PAWNS = 0;
    public final static int BLACK_KING = 1;
    public final static int BLACK_KNIGHTS = 2;
    public final static int BLACK_BISHOPS = 3;
    public final static int BLACK_ROOKS = 4;
    public final static int BLACK_QUEENS = 5;

    public long enPassant;

    public boolean castleWK;
    public boolean castleWQ;
    public boolean castleBK;
    public boolean castleBQ;
    public List<BitBoardMove> moveHistory;
    public BitBoardMoves bm;
    public boolean move;

    public BitBoard() {
        this.whitePieces = new long[6];
        this.whitePieces[WHITE_PAWNS] = 0x000000000000FF00L;
        this.whitePieces[WHITE_KING] = 0x0000000000000008L;
        this.whitePieces[WHITE_KNIGHTS] = 0x0000000000000042L;
        this.whitePieces[WHITE_BISHOPS] = 0x0000000000000024L;
        this.whitePieces[WHITE_ROOKS] = 0x0000000000000081L;
        this.whitePieces[WHITE_QUEENS] = 0x0000000000000010L;
        this.blackPieces[BLACK_PAWNS] = 0x00FF000000000000L;
        this.blackPieces[BLACK_KING] = 0x0800000000000000L;
        this.blackPieces[BLACK_KNIGHTS] = 0x4200000000000000L;
        this.blackPieces[BLACK_BISHOPS] = 0x2400000000000000L;
        this.blackPieces[BLACK_ROOKS] = 0x8100000000000000L;
        this.blackPieces[BLACK_QUEENS] = 0x1000000000000000L;
        this.enPassant = 0L;
        this.castleBK = true;
        this.castleBQ = true;
        this.castleWK = true;
        this.castleWQ = true;
        this.moveHistory = new ArrayList<>();
        move = true;
    }

    public BitBoard(ChessPiece[][] arr, boolean move) {
        whitePieces = new long[6];
        for (int i = 0; i < 64; i++) {
            whitePieces[WHITE_PAWNS] <<= 1;
            whitePieces[WHITE_KNIGHTS] <<= 1;
            whitePieces[WHITE_BISHOPS] <<= 1;
            whitePieces[WHITE_KING] <<= 1;
            whitePieces[WHITE_ROOKS] <<= 1;
            whitePieces[WHITE_QUEENS] <<= 1;

            blackPieces[BLACK_PAWNS] <<= 1;
            blackPieces[BLACK_KNIGHTS] <<= 1;
            blackPieces[BLACK_BISHOPS] <<= 1;
            blackPieces[BLACK_KING] <<= 1;
            blackPieces[BLACK_ROOKS] <<= 1;
            blackPieces[BLACK_QUEENS] <<= 1;
            ChessPiece cp = arr[i % 8][i / 8];
            if (cp instanceof Rook) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_ROOKS] += 1L;
                } else {
                    blackPieces[BLACK_ROOKS] += 1L;
                }
            } else if (cp instanceof Bishop) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_BISHOPS] += 1L;
                } else {
                    blackPieces[BLACK_BISHOPS] += 1L;
                }
            } else if (cp instanceof King) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_KING] += 1L;
                } else {
                    blackPieces[BLACK_KING] += 1L;
                }
            } else if (cp instanceof Knight) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_KNIGHTS] += 1L;
                } else {
                    blackPieces[BLACK_KING] += 1L;
                }
            } else if (cp instanceof Pawn) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_PAWNS] += 1L;
                } else {
                    blackPieces[BLACK_PAWNS] += 1L;
                }
            } else if (cp instanceof Queen) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[WHITE_QUEENS] += 1L;
                } else {
                    blackPieces[BLACK_QUEENS] += 1L;
                }
            }
        }
        this.enPassant = 0L;
        this.castleWK = true;
        this.castleWQ = true;
        this.castleBQ = true;
        this.castleBK = true;
        this.moveHistory = new ArrayList<>();
        this.move = move;
    }

    public BitBoard(ChessPiece[][] arr, List<BitBoardMove> moveHistory, boolean move) {
        this(arr, move);
        this.moveHistory = moveHistory;
        if (this.moveHistory.size() >= 1) {
            BitBoardMove bbm = this.moveHistory.get(this.moveHistory.size() - 1);
            if (bbm.x1 == bbm.x2 && Math.abs(bbm.y1 - bbm.y2) == 2) {
                this.enPassant = BitBoardMoves.FILES[bbm.x1];
            }
        }
    }

    public BitBoard(long[] whitePieces, long[] blackPieces, long enPassant, boolean castleWK, boolean castleWQ, boolean castleBK, boolean castleBQ, List<BitBoardMove> moveHistory, boolean move) {
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        if (whitePieces.length != 6 || blackPieces.length != 6) {
            throw new RuntimeException("Wrong format!");
        }

        this.enPassant = enPassant;
        this.castleWK = castleWK;
        this.castleWQ = castleWQ;
        this.castleBK = castleBK;
        this.castleBQ = castleBQ;
        this.moveHistory = moveHistory;
        this.move = move;
    }

    public BitBoard makeMove(BitBoardMove move) {
        long[] whitePiecesCopy = whitePieces.clone();
        long[] blackPiecesCopy = blackPieces.clone();
        List<BitBoardMove> moveHistoryClone = new ArrayList<>(moveHistory);
        moveHistoryClone.add(move);
        if (move.desc == ' ') {
            //Regular move
            int start = move.x1 + move.y1 * 8;
            int end = move.x2 + move.y2 * 8;
            long[] myPieces;
            long[] enemyPieces;
            if (move.color) {
                myPieces = whitePieces;
                enemyPieces= blackPieces;
            } else {
                myPieces = blackPieces;
                enemyPieces=whitePieces;
            }
            for(long l: myPieces){
                if(((l>>>start)&1)==1){

                }
            }

        } else if (move.desc == 'E') {
            //En passant
        } else if (move.desc == 'C') {
            //Castle
        } else if (move.desc == 'Q') {

        } else if (move.desc == 'R') {

        } else if (move.desc == 'B') {

        } else if (move.desc == 'N') {

        } else {
            System.out.println("Wrong move!");
            System.exit(-1);
        }
        //Check if castling rights are revoked!
        boolean castleWKCopy = castleWK;
        boolean castleWQCopy = castleWQ;
        boolean castleBKCopy = castleBK;
        boolean castleBQCopy = castleBQ;

        //Check if an enpassant possibility was created!
        long enPassantCopy = 0L;

        return new BitBoard(whitePiecesCopy,blackPiecesCopy,enPassantCopy,castleWKCopy,castleWQCopy,castleBKCopy,castleBQCopy,moveHistoryClone,!this.move);
    }

    public void initBoard() {
        this.bm = new BitBoardMoves(this, move);
    }


    public static void main(String[] args) {
        timeTest();
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
                if (((this.whitePieces[WHITE_PAWNS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.PAWN_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_PAWNS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.PAWN_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[WHITE_BISHOPS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.BISHOP_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_BISHOPS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.BISHOP_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[WHITE_KNIGHTS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KNIGHT_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_KNIGHTS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KNIGHT_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[WHITE_KING] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KING_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_KING] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KING_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[WHITE_ROOKS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.ROOK_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_ROOKS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.ROOK_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[WHITE_QUEENS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.QUEEN_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BLACK_QUEENS] >> shift) & 1) == 1) {
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
        sb.append("whitePawns Long: " + String.format("0x%016X", whitePieces[WHITE_PAWNS]));
        sb.append("\nwhiteKing Long: " + String.format("0x%016X", whitePieces[WHITE_KING]));
        sb.append("\nwhiteKnights Long: " + String.format("0x%016X", whitePieces[WHITE_KNIGHTS]));
        sb.append("\nwhiteBishops Long: " + String.format("0x%016X", whitePieces[WHITE_BISHOPS]));
        sb.append("\nwhiteRooks Long: " + String.format("0x%016X", whitePieces[WHITE_ROOKS]));
        sb.append("\nwhiteQueens Long: " + String.format("0x%016X", whitePieces[WHITE_QUEENS]));

        sb.append("\nblackPawns Long: " + String.format("0x%016X", blackPieces[BLACK_PAWNS]));
        sb.append("\nblackKing Long: " + String.format("0x%016X", blackPieces[BLACK_KING]));
        sb.append("\nblackKnights Long: " + String.format("0x%016X", blackPieces[BLACK_KNIGHTS]));
        sb.append("\nblackBishops Long: " + String.format("0x%016X", blackPieces[BLACK_BISHOPS]));
        sb.append("\nblackRooks Long: " + String.format("0x%016X", blackPieces[BLACK_ROOKS]));
        sb.append("\nblackQueens Long: " + String.format("0x%016X", blackPieces[BLACK_QUEENS]));
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

    public boolean kingIsThreatend;
    //X1 is in File
    //Y1 is in Rank
    //Moves are of type x1,y1,x2,y2 if they are not a) Castles, b) En passants x1x2 Space E, c) Promotion Moves: x1x2 and then Q,R,B,N and then P
    List<BitBoardMove> possibleMoves;

    public BitBoardMoves(BitBoard bb, boolean move) {
        this.bb = bb;
        this.move = move;
        this.emptySquares = ~(bb.whitePieces[BitBoard.WHITE_KING] | bb.whitePieces[BitBoard.WHITE_QUEENS] | bb.whitePieces[BitBoard.WHITE_ROOKS] | bb.whitePieces[BitBoard.WHITE_BISHOPS] | bb.whitePieces[BitBoard.WHITE_KNIGHTS] | bb.whitePieces[BitBoard.WHITE_PAWNS] | bb.blackPieces[BitBoard.BLACK_KING] | bb.blackPieces[BitBoard.BLACK_QUEENS] | bb.blackPieces[BitBoard.BLACK_ROOKS] | bb.blackPieces[BitBoard.BLACK_BISHOPS] | bb.blackPieces[BitBoard.BLACK_KNIGHTS] | bb.blackPieces[BitBoard.BLACK_PAWNS]);
        occupiedSquares = ~emptySquares;
        //White cant capture those figures, Black King is in there to prevent illegal moves!
        if (move) {
            whiteCantCapture = ~(bb.whitePieces[BitBoard.WHITE_PAWNS] | bb.whitePieces[BitBoard.WHITE_KNIGHTS] | bb.whitePieces[BitBoard.WHITE_BISHOPS] | bb.whitePieces[BitBoard.WHITE_ROOKS] | bb.whitePieces[BitBoard.WHITE_QUEENS] | bb.whitePieces[BitBoard.WHITE_KING] | bb.blackPieces[BitBoard.BLACK_KING]);
            blackPieces = bb.blackPieces[BitBoard.BLACK_BISHOPS] | bb.blackPieces[BitBoard.BLACK_QUEENS] | bb.blackPieces[BitBoard.BLACK_ROOKS] | bb.blackPieces[BitBoard.BLACK_KNIGHTS] | bb.blackPieces[BitBoard.BLACK_PAWNS];//No Black King in there since it should not be captured
        } else {
            //Black cant capture those figures, White King is in there to prevent illegal moves!
            blackCantCapture = ~(bb.blackPieces[BitBoard.BLACK_PAWNS] | bb.blackPieces[BitBoard.BLACK_KNIGHTS] | bb.blackPieces[BitBoard.BLACK_BISHOPS] | bb.blackPieces[BitBoard.BLACK_ROOKS] | bb.blackPieces[BitBoard.BLACK_QUEENS] | bb.blackPieces[BitBoard.BLACK_KING] | bb.whitePieces[BitBoard.WHITE_KING]);
            whitePieces = bb.whitePieces[BitBoard.WHITE_BISHOPS] | bb.whitePieces[BitBoard.WHITE_QUEENS] | bb.whitePieces[BitBoard.WHITE_ROOKS] | bb.whitePieces[BitBoard.WHITE_KNIGHTS] | bb.whitePieces[BitBoard.WHITE_PAWNS];//No white King in there since it should not be captured
        }

        //Wenn König gerade Bedroht ist, alle Pieces auf Pin überprüfen
        //Sonst, nur König überprüfen
        if (move) {
            possibleMoves = new ArrayList<>(100);
            possibleMoves.addAll(possiblePawnMovesWhite(bb.whitePieces[BitBoard.WHITE_PAWNS], bb.blackPieces[BitBoard.BLACK_PAWNS], blackPieces));
            possibleMoves.addAll(possibleKnightMoves(bb.whitePieces[BitBoard.WHITE_KNIGHTS], whiteCantCapture, move));
            possibleMoves.addAll(possibleBishopMoves(bb.whitePieces[BitBoard.WHITE_BISHOPS], whiteCantCapture, move));
            possibleMoves.addAll(possibleRookMoves(bb.whitePieces[BitBoard.WHITE_ROOKS], whiteCantCapture, move));
            possibleMoves.addAll(possibleQueenMoves(bb.whitePieces[BitBoard.WHITE_QUEENS], whiteCantCapture, move));
            possibleMoves.addAll(possibleKingMoves(bb.whitePieces[BitBoard.WHITE_KING], whiteCantCapture, move));
        } else {
            possibleMoves = new ArrayList<>(100);
            possibleMoves.addAll(possiblePawnMovesBlack(bb.blackPieces[BitBoard.BLACK_PAWNS], bb.whitePieces[BitBoard.WHITE_PAWNS], whitePieces));
            possibleMoves.addAll(possibleKnightMoves(bb.blackPieces[BitBoard.BLACK_KNIGHTS], blackCantCapture, move));
            possibleMoves.addAll(possibleBishopMoves(bb.blackPieces[BitBoard.BLACK_BISHOPS], blackCantCapture, move));
            possibleMoves.addAll(possibleRookMoves(bb.blackPieces[BitBoard.BLACK_ROOKS], blackCantCapture, move));
            possibleMoves.addAll(possibleQueenMoves(bb.blackPieces[BitBoard.BLACK_QUEENS], blackCantCapture, move));
            possibleMoves.addAll(possibleKingMoves(bb.blackPieces[BitBoard.BLACK_KING], blackCantCapture, move));
        }

    }

    public long unsafeForWhite() {
        return unsafeForColor(true, bb.blackPieces[BitBoard.BLACK_PAWNS], bb.blackPieces[BitBoard.BLACK_KNIGHTS], bb.blackPieces[BitBoard.BLACK_QUEENS], bb.blackPieces[BitBoard.BLACK_BISHOPS], bb.blackPieces[BitBoard.BLACK_ROOKS], bb.blackPieces[BitBoard.BLACK_KING]);
    }

    public long unsafeForBlack() {
        return unsafeForColor(false, bb.whitePieces[BitBoard.WHITE_PAWNS], bb.whitePieces[BitBoard.WHITE_KNIGHTS], bb.whitePieces[BitBoard.WHITE_QUEENS], bb.whitePieces[BitBoard.WHITE_BISHOPS], bb.whitePieces[BitBoard.WHITE_ROOKS], bb.whitePieces[BitBoard.WHITE_KING]);
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

    public List<BitBoardMove> possibleCastle(boolean move, long myKing, long myRooks, boolean castleK, boolean castleQ) {
        List<BitBoardMove> res = new ArrayList<>(2);
        if (castleK) {
            if (move) {
                res.add(new BitBoardMove(4, 6, true, 'C'));
            } else {
                res.add(new BitBoardMove(4, 6, false, 'C'));
            }
        }
        if (castleQ) {
            if (move) {
                res.add(new BitBoardMove(4, 2, true, 'C'));
            } else {
                res.add(new BitBoardMove(4, 2, false, 'C'));
            }
        }
        return res;
    }

    public List<BitBoardMove> possiblePawnMovesBlack(long blackPawns, long whitePawns, long whitePieces) {
        List<BitBoardMove> res = new ArrayList<>(16);
        //Capture right BitBoard
        //Make sure there is a Black Piece on the field and we do not capture to the right on the A Destination File
        long moves;
        moves = (blackPawns >>> 7) & whitePieces & ~RANKS[RANK_1] & ~FILES[FILE_H];
        long firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 + 1, j / 8 - 1, j % 8, j / 8, false));
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
            res.add(new BitBoardMove(j % 8 - 1, j / 8 - 1, j % 8, j / 8, false));
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
            res.add(new BitBoardMove(j % 8, j / 8 - 1, j % 8, j / 8, false));
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
            res.add(new BitBoardMove(j % 8, j / 8 - 2, j % 8, j / 8, false));
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
            int x1 = j % 8 + 1;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, false, 'Q'));
            res.add(new BitBoardMove(x1, x2, false, 'R'));
            res.add(new BitBoardMove(x1, x2, false, 'B'));
            res.add(new BitBoardMove(x1, x2, false, 'N'));
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
            int x1 = j % 8 - 1;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, false, 'Q'));
            res.add(new BitBoardMove(x1, x2, false, 'R'));
            res.add(new BitBoardMove(x1, x2, false, 'B'));
            res.add(new BitBoardMove(x1, x2, false, 'N'));
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
            int x1 = j % 8;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, false, 'Q'));
            res.add(new BitBoardMove(x1, x2, false, 'R'));
            res.add(new BitBoardMove(x1, x2, false, 'B'));
            res.add(new BitBoardMove(x1, x2, false, 'N'));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }

        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());

        //En passants
        //Check if last move was pawn move with two forward
        //en passant right
        long deletedPawn = (blackPawns << 1) & whitePawns & RANKS[RANK_4] & ~FILES[FILE_H] & bb.enPassant;//Position of deleted Pawn
        if (deletedPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 + 1, j % 8, false, 'E'));
        }
        //en passant left
        deletedPawn = (blackPawns >>> 1) & whitePawns & RANKS[RANK_4] & ~FILES[FILE_A] & bb.enPassant;//Position of deleted Pawn
        if (deletedPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 - 1, j % 8, false, 'E'));
        }

        return res;
    }

    public List<BitBoardMove> possiblePawnMovesWhite(long whitePawns, long blackPawns, long blackPieces) {
        List<BitBoardMove> res = new ArrayList<>(16);
        //Capture right BitBoard
        //Make sure there is a Black Piece on the field and we do not capture to the right on the A Destination File
        long moves;
        moves = (whitePawns << 7) & blackPieces & ~RANKS[RANK_8] & ~FILES[FILE_A];
        long firstPawn = moves & ~(moves - 1);
        while (firstPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(moves);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 - 1, j / 8 + 1, j % 8, j / 8, true));
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
            res.add(new BitBoardMove(j % 8 + 1, j / 8 + 1, j % 8, j / 8, true));
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
            res.add(new BitBoardMove(j % 8, j / 8 + 1, j % 8, j / 8, true));
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
            res.add(new BitBoardMove(j % 8, j / 8 + 2, j % 8, j / 8, true));
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
            int x1 = j % 8 - 1;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, true, 'Q'));
            res.add(new BitBoardMove(x1, x2, true, 'R'));
            res.add(new BitBoardMove(x1, x2, true, 'B'));
            res.add(new BitBoardMove(x1, x2, true, 'N'));
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
            int x1 = j % 8 + 1;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, true, 'Q'));
            res.add(new BitBoardMove(x1, x2, true, 'R'));
            res.add(new BitBoardMove(x1, x2, true, 'B'));
            res.add(new BitBoardMove(x1, x2, true, 'N'));
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
            int x1 = j % 8;
            int x2 = j % 8;
            res.add(new BitBoardMove(x1, x2, true, 'Q'));
            res.add(new BitBoardMove(x1, x2, true, 'R'));
            res.add(new BitBoardMove(x1, x2, true, 'B'));
            res.add(new BitBoardMove(x1, x2, true, 'N'));
            moves &= ~firstPawn;
            firstPawn = moves & ~(moves - 1);
        }

        //System.out.println(BitBoard.getOneBitBoardString(moves));
        //System.out.println("SB: "+sb.toString());

        //En passants
        //Check if last move was pawn move with two forward
        //en passant right
        long deletedPawn = (whitePawns >> 1) & blackPawns & RANKS[RANK_5] & ~FILES[FILE_A] & bb.enPassant;//Position of deleted Pawn
        if (deletedPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 - 1, j % 8, true, 'E'));
        }
        //en passant left
        deletedPawn = (whitePawns << 1) & blackPawns & RANKS[RANK_5] & ~FILES[FILE_H] & bb.enPassant;//Position of deleted Pawn
        if (deletedPawn != 0) {
            int leadingZeros = Long.numberOfTrailingZeros(deletedPawn);
            int j = 63 - leadingZeros;
            res.add(new BitBoardMove(j % 8 + 1, j % 8, true, 'E'));
        }

        return res;
    }

    public List<BitBoardMove> possibleKnightMoves(long knights, long cantCapture, boolean color) {
        List<BitBoardMove> res = new ArrayList<>(16);
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
                res.add(new BitBoardMove(knightLocation % 8, knightLocation / 8, knightDestination % 8, knightDestination / 8, color));
                possibility &= ~knightDestinationBB;
                knightDestinationBB = possibility & ~(possibility - 1);
            }
            knights &= ~knightPossibility;
            knightPossibility = knights & ~(knights - 1);
        }
        return res;
    }

    public List<BitBoardMove> possibleBishopMoves(long bishops, long cantCapture, boolean color) {
        List<BitBoardMove> res = new ArrayList<>(30);
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
                res.add(new BitBoardMove(bishopLocation % 8, bishopLocation / 8, j % 8, j / 8, color));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~bishop;
            bishop = wBClone & ~(wBClone - 1);
        }

        return res;
    }

    public List<BitBoardMove> possibleRookMoves(long rooks, long cantCapture, boolean color) {
        List<BitBoardMove> res = new ArrayList<>(30);
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
                res.add(new BitBoardMove(rookLocation % 8, rookLocation / 8, j % 8, j / 8, color));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~rook;
            rook = wBClone & ~(wBClone - 1);
        }

        return res;
    }

    public List<BitBoardMove> possibleQueenMoves(long queens, long cantCapture, boolean color) {
        List<BitBoardMove> res = new ArrayList<>(31);
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
                res.add(new BitBoardMove(queenLocation % 8, queenLocation / 8, j % 8, j / 8, color));
                possibilityOfMoving &= ~position;
                position = possibilityOfMoving & ~(possibilityOfMoving - 1);
            }
            wBClone &= ~queen;
            queen = wBClone & ~(wBClone - 1);
        }
        return res;
    }

    public List<BitBoardMove> possibleKingMoves(long king, long cantCapture, boolean color) {
        List<BitBoardMove> res = new ArrayList<>(8);
        int kingLocation = 63 - Long.numberOfTrailingZeros(king);
        //Use the pre initialized array and get the possible moves for the king location
        long possibility = KING_MOVES[kingLocation] & cantCapture;
        //Go through every possible move
        long kingDestinationBB = possibility & ~(possibility - 1);
        while (kingDestinationBB != 0) {
            int kingDestination = 63 - Long.numberOfTrailingZeros(kingDestinationBB);
            res.add(new BitBoardMove(kingLocation % 8, kingLocation / 8, kingDestination % 8, kingDestination / 8, color));
            possibility &= ~kingDestinationBB;
            kingDestinationBB = possibility & ~(possibility - 1);
        }
        return res;
    }

    public long unsafeForColor(boolean color, long enemyPawns, long enemyKnights, long enemyQueen, long enemyBishop, long enemyRook, long enemyKing) {
        long unsafe;
        if (!color) {
            //White to the right
            unsafe = ((enemyPawns << 7)) & ~FILES[FILE_A];
            //White to the left
            unsafe |= ((enemyPawns << 9)) & ~FILES[FILE_H];
        } else {
            unsafe = ((enemyPawns >>> 7)) & ~FILES[FILE_H];
            unsafe |= ((enemyPawns >>> 9)) & ~FILES[FILE_A];
        }
        long i = enemyKnights & ~(enemyKnights - 1);
        //Go through every knight
        while (i != 0) {
            int knightLocation = 63 - Long.numberOfTrailingZeros(i);
            unsafe |= KNIGHT_MOVES[knightLocation];
            enemyKnights &= ~i;
            i = enemyKnights & ~(enemyKnights - 1);
        }

        //Diagonal pieces
        long QB = enemyQueen | enemyBishop;
        i = QB & ~(QB - 1);
        //Go through every diagonal piece
        while (i != 0) {
            int location = 63 - Long.numberOfTrailingZeros(i);
            unsafe |= diagonalAndAntiDiagonalMoves(location);
            QB &= ~i;
            i = QB & ~(QB - 1);
        }

        //Vertical Horizontal Pieces
        long QR = enemyQueen | enemyRook;
        i = QR & ~(QR - 1);
        //Go through every horizontal piece
        while (i != 0) {
            int location = 63 - Long.numberOfTrailingZeros(i);
            unsafe |= horizontalAndVerticalMoves(location);
            QR &= ~i;
            i = QR & ~(QR - 1);
        }
        //Enemy king
        int kingLocation = 63 - Long.numberOfTrailingZeros(enemyKing);
        unsafe |= KING_MOVES[kingLocation];

        return unsafe;
    }


}

class BitBoardMove {

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public boolean color;
    public char desc;

    public BitBoardMove(int x1, int y1, int x2, int y2, boolean color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.desc = ' ';
        this.color = color;
    }

    public BitBoardMove(int x1, int x2, boolean color, char desc) {
        this.x1 = x1;
        this.x2 = x2;
        this.color = color;
        this.desc = desc;
    }
}