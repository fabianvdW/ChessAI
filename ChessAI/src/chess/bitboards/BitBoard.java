package chess.bitboards;

import chess.ChessColor;
import chess.ChessGameStatus;
import chess.ChessPosition;
import chess.pieces.*;
import chess.uci.advancedclient.ChessAIOneGoInstance;
import helpers.Constants;
import helpers.FENLoader;
import helpers.StringColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitBoard {
    public long[] whitePieces;
    public long[] blackPieces;

    public final static int PAWNS = 0;
    public final static int KING = 1;
    public final static int KNIGHTS = 2;
    public final static int BISHOPS = 3;
    public final static int ROOKS = 4;
    public final static int QUEENS = 5;

    public long enPassant;

    public boolean castleWK;
    public boolean castleWQ;
    public boolean hasCastledWhite;
    public boolean hasCastledBlack;
    public boolean castleBK;
    public boolean castleBQ;
    public List<BitBoardMove> moveHistory;
    public BitBoardMoves bm;
    public boolean move;
    public ChessGameStatus status;

    public BitBoard() {
        this.whitePieces = new long[6];
        this.whitePieces[PAWNS] = 0x000000000000FF00L;
        this.whitePieces[KING] = 0x0000000000000008L;
        this.whitePieces[KNIGHTS] = 0x0000000000000042L;
        this.whitePieces[BISHOPS] = 0x0000000000000024L;
        this.whitePieces[ROOKS] = 0x0000000000000081L;
        this.whitePieces[QUEENS] = 0x0000000000000010L;
        this.blackPieces = new long[6];
        this.blackPieces[PAWNS] = 0x00FF000000000000L;
        this.blackPieces[KING] = 0x0800000000000000L;
        this.blackPieces[KNIGHTS] = 0x4200000000000000L;
        this.blackPieces[BISHOPS] = 0x2400000000000000L;
        this.blackPieces[ROOKS] = 0x8100000000000000L;
        this.blackPieces[QUEENS] = 0x1000000000000000L;
        this.enPassant = 0L;
        this.castleBK = true;
        this.castleBQ = true;
        this.hasCastledBlack=false;
        this.castleWK = true;
        this.castleWQ = true;
        this.hasCastledWhite=false;
        this.moveHistory = new ArrayList<>();
        this.move = true;
        this.status = ChessGameStatus.INGAME;
    }

    public BitBoard(ChessPiece[][] arr, boolean move) {
        whitePieces = new long[6];
        blackPieces = new long[6];
        for (int i = 0; i < 64; i++) {
            whitePieces[PAWNS] <<= 1;
            whitePieces[KNIGHTS] <<= 1;
            whitePieces[BISHOPS] <<= 1;
            whitePieces[KING] <<= 1;
            whitePieces[ROOKS] <<= 1;
            whitePieces[QUEENS] <<= 1;

            blackPieces[PAWNS] <<= 1;
            blackPieces[KNIGHTS] <<= 1;
            blackPieces[BISHOPS] <<= 1;
            blackPieces[KING] <<= 1;
            blackPieces[ROOKS] <<= 1;
            blackPieces[QUEENS] <<= 1;
            ChessPiece cp = arr[i % 8][i / 8];
            if (cp instanceof Rook) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[ROOKS] += 1L;
                } else {
                    blackPieces[ROOKS] += 1L;
                }
            } else if (cp instanceof Bishop) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[BISHOPS] += 1L;
                } else {
                    blackPieces[BISHOPS] += 1L;
                }
            } else if (cp instanceof King) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[KING] += 1L;
                } else {
                    blackPieces[KING] += 1L;
                }
            } else if (cp instanceof Knight) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[KNIGHTS] += 1L;
                } else {
                    blackPieces[KNIGHTS] += 1L;
                }
            } else if (cp instanceof Pawn) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[PAWNS] += 1L;
                } else {
                    blackPieces[PAWNS] += 1L;
                }
            } else if (cp instanceof Queen) {
                if (cp.color == ChessColor.WHITE) {
                    whitePieces[QUEENS] += 1L;
                } else {
                    blackPieces[QUEENS] += 1L;
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
        if(this.moveHistory.size()>0&& this.moveHistory.get(this.moveHistory.size()-1).desc=='C'){
            if(move){
                hasCastledBlack=true;
            }else{
                hasCastledWhite=true;
            }
        }
        this.move = move;
        this.status = ChessGameStatus.INGAME;
    }


    public void initBoard() {
        this.bm = new BitBoardMoves(this, move);
        //Check status
        if (this.whitePieces[BitBoard.PAWNS] == 0 && this.whitePieces[BitBoard.ROOKS] == 0 && this.whitePieces[BitBoard.KNIGHTS] == 0 && this.whitePieces[BitBoard.BISHOPS] == 0 && this.whitePieces[BitBoard.QUEENS] == 0
                && this.blackPieces[BitBoard.PAWNS] == 0 && this.blackPieces[BitBoard.ROOKS] == 0 && this.blackPieces[BitBoard.KNIGHTS] == 0 && this.blackPieces[BitBoard.BISHOPS] == 0 && this.blackPieces[BitBoard.QUEENS] == 0) {
            //Lack of material draw
            this.status = ChessGameStatus.DRAW;
            return;
        }

        if (this.bm.legalMoves.size() == 0) {
            //Either Stalemate or Checkmate
            if (this.bm.kingIsThreatend) {
                if (this.move) {
                    this.status = ChessGameStatus.BLACKWIN;
                } else {
                    this.status = ChessGameStatus.WHITEWIN;
                }
            } else {
                this.status = ChessGameStatus.DRAW;
            }
        }
    }
    public static Map<ChessPiece,List<BitBoardMove>> mapLegalMovesToPieces(BitBoard bb){
        if(bb.bm==null){
            bb.initBoard();
        }
        Map<ChessPiece,List<BitBoardMove>> res = new HashMap<>();
        for(BitBoardMove bm: bb.bm.legalMoves){
            ChessPiece cp= BitBoard.getChessPiece(bb,bm.x1,bm.y1);
            if(res.containsKey(cp)){
                res.get(cp).add(bm);
            }else{
                List<BitBoardMove> list= new ArrayList<>();
                list.add(bm);
                res.put(cp,list);
            }
        }
        return res;
    }
    public static ChessPiece getChessPiece(BitBoard bb,int x,int y){
        int shift=63-x-8*y;
        if(((bb.whitePieces[BitBoard.KING]>>>shift)&1)!=0){
            return new King(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        if(((bb.whitePieces[BitBoard.PAWNS]>>>shift)&1)!=0){
            return new Pawn(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        if(((bb.whitePieces[BitBoard.BISHOPS]>>>shift)&1)!=0){
            return new Bishop(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        if(((bb.whitePieces[BitBoard.KNIGHTS]>>>shift)&1)!=0){
            return new Knight(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        if(((bb.whitePieces[BitBoard.ROOKS]>>>shift)&1)!=0){
            return new Rook(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        if(((bb.whitePieces[BitBoard.QUEENS]>>>shift)&1)!=0){
            return new Queen(ChessColor.WHITE,new ChessPosition(x,y),null);
        }
        
        if(((bb.blackPieces[BitBoard.KING]>>>shift)&1)!=0){
            return new King(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        if(((bb.blackPieces[BitBoard.PAWNS]>>>shift)&1)!=0){
            return new Pawn(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        if(((bb.blackPieces[BitBoard.BISHOPS]>>>shift)&1)!=0){
            return new Bishop(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        if(((bb.blackPieces[BitBoard.KNIGHTS]>>>shift)&1)!=0){
            return new Knight(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        if(((bb.blackPieces[BitBoard.ROOKS]>>>shift)&1)!=0){
            return new Rook(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        if(((bb.blackPieces[BitBoard.QUEENS]>>>shift)&1)!=0){
            return new Queen(ChessColor.BLACK,new ChessPosition(x,y),null);
        }
        return null;
    }
    public static List<BitBoard> playGame(BitBoard start) {
        List<BitBoard> boardHistory = new ArrayList<>();
        boardHistory.add(start);
        start.initBoard();
        do {
            start = start.makeLegalMove(start.bm.legalMoves.get((int) (start.bm.legalMoves.size() * Math.random())));
            boardHistory.add(start);
        } while (start.status == ChessGameStatus.INGAME);
        return boardHistory;
    }

    public BitBoard makeLegalMove(BitBoardMove bm) {
        if (this.bm == null) {
            this.initBoard();
        }
        if (this.status != ChessGameStatus.INGAME) {
            throw new RuntimeException("Game is already in end state!");
        }

        if (this.bm.legalMoves.contains(bm)) {
            //Move is legal
            BitBoard bb = this.bm.legalFollowingGameStates.get(bm);
            bb.initBoard();
            return bb;
        } else {
            throw new RuntimeException("Illegal move!");
        }
    }

    public long getOccupiedSquares() {
        return (this.whitePieces[BitBoard.KING] | this.whitePieces[BitBoard.QUEENS] | this.whitePieces[BitBoard.ROOKS] | this.whitePieces[BitBoard.BISHOPS] | this.whitePieces[BitBoard.KNIGHTS] | this.whitePieces[BitBoard.PAWNS] | this.blackPieces[BitBoard.KING] | this.blackPieces[BitBoard.QUEENS] | this.blackPieces[BitBoard.ROOKS] | this.blackPieces[BitBoard.BISHOPS] | this.blackPieces[BitBoard.KNIGHTS] | this.blackPieces[BitBoard.PAWNS]);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append("|");
            for (int j = 0; j < 8; j++) {
                int shift = 63 - (j + i * 8);
                sb.append("\t");
                if (((this.whitePieces[PAWNS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.PAWN_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[PAWNS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.PAWN_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[BISHOPS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.BISHOP_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[BISHOPS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.BISHOP_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[KNIGHTS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KNIGHT_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[KNIGHTS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KNIGHT_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[KING] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.KING_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[KING] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.KING_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[ROOKS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.ROOK_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[ROOKS] >> shift) & 1) == 1) {
                    sb.append(StringColor.YELLOW);
                    sb.append(Constants.ROOK_REPRESENTATION_BLACK);
                }
                if (((this.whitePieces[QUEENS] >> shift) & 1) == 1) {
                    sb.append(StringColor.BLACK);
                    sb.append(Constants.QUEEN_REPRESENTATION_WHITE);
                }
                if (((this.blackPieces[QUEENS] >> shift) & 1) == 1) {
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
        sb.append("whitePawns Long: " + String.format("0x%016X", whitePieces[PAWNS]));
        sb.append("\nwhiteKing Long: " + String.format("0x%016X", whitePieces[KING]));
        sb.append("\nwhiteKnights Long: " + String.format("0x%016X", whitePieces[KNIGHTS]));
        sb.append("\nwhiteBishops Long: " + String.format("0x%016X", whitePieces[BISHOPS]));
        sb.append("\nwhiteRooks Long: " + String.format("0x%016X", whitePieces[ROOKS]));
        sb.append("\nwhiteQueens Long: " + String.format("0x%016X", whitePieces[QUEENS]));

        sb.append("\nblackPawns Long: " + String.format("0x%016X", blackPieces[PAWNS]));
        sb.append("\nblackKing Long: " + String.format("0x%016X", blackPieces[KING]));
        sb.append("\nblackKnights Long: " + String.format("0x%016X", blackPieces[KNIGHTS]));
        sb.append("\nblackBishops Long: " + String.format("0x%016X", blackPieces[BISHOPS]));
        sb.append("\nblackRooks Long: " + String.format("0x%016X", blackPieces[ROOKS]));
        sb.append("\nblackQueens Long: " + String.format("0x%016X", blackPieces[QUEENS]));
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

