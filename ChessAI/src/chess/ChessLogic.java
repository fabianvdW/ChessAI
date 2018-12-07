package chess;

import chess.pieces.ChessPiece;
import chess.pieces.King;

import java.util.ArrayList;
import java.util.List;

public class ChessLogic {
    public static boolean isValidX(int x) {
        return x >= 0 && x < 8;
    }

    public static boolean isValidY(int y) {
        return y >= 0 && y < 8;
    }

    public static List<ChessPiece> getThreats(ChessPosition cpos, ChessBoard cb, ChessColor enemyColor, boolean stopAtOne) {
        ArrayList<ChessPiece> result = new ArrayList<>();
        List<ChessPiece> enemyPieces = (enemyColor == ChessColor.WHITE) ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        for (ChessPiece cPiece : enemyPieces) {
            if (cPiece instanceof King) {
                if (Math.abs(cPiece.position.getX() - cpos.getX()) <= 1 && Math.abs(cPiece.position.getY() - cpos.getY()) <= 1) {
                    result.add(cPiece);
                    if (stopAtOne) {
                        return result;
                    }
                }
            } else if (threatensPosition(cPiece, cb, cpos)) {
                result.add(cPiece);
                if (stopAtOne) {
                    return result;
                }
            }
        }
        return result;
    }

    public static boolean threatensPosition(ChessPiece cPiece, ChessBoard cb, ChessPosition cpos) {
        for (ChessMove cMove : cPiece.getPossibleMoves(cb, true)) {
            ChessPosition cPos = cMove.to;
            if (cPos.equals(cpos)) {
                return true;
            }
        }
        return false;
    }

    public static void applyChessMoveToBoardWithoutLogic(ChessMove cm, ChessBoard cb) {
        if (cm != null) {
            cb.setChessPiece(cm.from, null);
            cb.setChessPiece(cm.to, cm.moved);
            if (cm.old != null) {
                cm.old.onBoard = false;
            }
        }
    }

    public static void reverseChessMoveToBoardWithoutLogic(ChessMove cm, ChessBoard cb) {
        if (cm != null) {
            cb.setChessPiece(cm.to, cm.old);
            cb.setChessPiece(cm.from, cm.moved);
        }
    }

    //this depends on color
    public static List<ChessMove> getAllPossibleMoves(ChessBoard cb, ChessColor color) {
        List<ChessPiece> pieces = color == ChessColor.WHITE ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        List<ChessMove> moves = new ArrayList<>();
        for (ChessPiece cPiece : pieces) {
            if (cPiece.onBoard) {
                List<ChessMove> move = cPiece.getPossibleMoves(cb, false);
                moves.addAll(move);
            }
        }
        return moves;
    }

    public static boolean isPositionThreatened(ChessPosition cp, ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
        applyChessMoveToBoardWithoutLogic(cm, cb);
        boolean threat = !getThreats(cp, cb, enemyColor, true).isEmpty();
        reverseChessMoveToBoardWithoutLogic(cm, cb);
        return threat;
    }

    public static boolean isCheckMate(ChessBoard cb, ChessColor move) {
        ChessPiece k = (move == ChessColor.WHITE ? cb.WHITE_KING : cb.BLACK_KING);
        ChessColor enemyColor = (k.color == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE);
        List<ChessPiece> threatsToKing = getThreats(k.position, cb, enemyColor, false);
        if (!threatsToKing.isEmpty()) {
            if (k.getPossibleMoves(cb, false).isEmpty()) {
                for (ChessMove cm : getAllPossibleMoves(cb, move)) {
                    if (cm.moved.equals(k)) {
                        assert (false);
                        continue;
                    }
                    applyChessMoveToBoardWithoutLogic(cm, cb);
                    boolean noThreats = true;
                    for (ChessPiece cp : threatsToKing) {
                        if (cp.onBoard) {
                            if (threatensPosition(cp, cb, k.position)) {
                                noThreats = false;
                                break;
                            }
                        }
                    }
                    reverseChessMoveToBoardWithoutLogic(cm, cb);
                    if (noThreats) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
