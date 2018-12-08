package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessLogic {
    public static boolean isValidPosition(int cpos){
        return cpos>=0&& cpos<64;
    }

    public static List<ChessPiece> getThreats(int cpos, ChessBoard cb, ChessColor enemyColor, boolean stopAtOne) {
        ArrayList<ChessPiece> result = new ArrayList<>();
        List<ChessPiece> enemyPieces = (enemyColor == ChessColor.WHITE) ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        for (ChessPiece cPiece : enemyPieces) {
            if (cPiece instanceof King) {
                //Work out x and y parts
                int cPieceX= cPiece.position%8;
                int cPosX= cpos%8;
                int cPieceY= (cPiece.position-cPieceX)/8;
                int cPosY= (cpos-cPosX)/8;
                if (Math.abs(cPieceX-cPosX) <= 1 && Math.abs(cPieceY-cPosY) <= 1) {
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

    public static boolean threatensPosition(ChessPiece cPiece, ChessBoard cb, int cpos) {
        for (ChessMove cMove : cPiece.getPossibleMoves(cb, true)) {
            int cPos = cMove.to;
            if (cPos==cpos) {
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

    public static Map<ChessPiece, List<ChessMove>> getNewMoves(ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
        Map<ChessPiece, List<ChessMove>> newMoves = new HashMap<>();
        if (cm == null) {
            return newMoves;
        }
        List<ChessPiece> enemyPieces = enemyColor == ChessColor.WHITE ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        int freePosition = cm.from;
        int newPosition = cm.to;
        for (ChessPiece cp : enemyPieces) {
            if (cp instanceof King || cp instanceof Knight) {
                continue;
            } else if (cp instanceof Pawn) {
                //Free position can be in front of pawn, Pawn might be able to move forward once or twice now!
                List<ChessMove> newM = new ArrayList<>();
                Pawn p = (Pawn) cp;
                int p0 = p.position+p.minimalUnit[0];
                int p1 = p.position+p.minimalUnit[1];
                int p2 = p.position+p.minimalUnit[2];
                int p3 = p.position+p.minimalUnit[3];
                if (freePosition==p0) {
                    //One move is definitly new!
                    newM.add(new ChessMove(p.position, p0, p, null));

                    //2 moves if in starting position and other field also free
                    if (p.moves == 0 && cb.getChessPiece(p1) == null) {
                        newM.add(new ChessMove(p.position, p1, p, null));
                    }

                } else if (freePosition==p1) {
                    if (p.moves == 0 && cb.getChessPiece(p1) == null) {
                        newM.add(new ChessMove(p.position, p1, p, null));
                    }
                }
                if (freePosition==p2) {
                    //Check en passant
                    if (canEnPassant(cb, p, p.minimalUnit[2], enemyColor)) {
                        newM.add(new ChessMove(p.position, p2, p, cb.getChessPiece(p2-8*p.incrementor)));
                    }
                } else if (newPosition==p2) {
                    //Can capture
                    newM.add(new ChessMove(p.position, p2, cp, cm.moved));
                }
                if (freePosition==p3) {
                    //Check en passant
                    if (canEnPassant(cb, p, p.minimalUnit[3], enemyColor)) {
                        newM.add(new ChessMove(p.position, p3, p, cb.getChessPiece(p3-8*p.incrementor)));
                    }
                } else if (newPosition==p3) {
                    //Can capture
                    newM.add(new ChessMove(p.position, p3, cp, cm.moved));
                }

                newMoves.put(cp, newM);
            } else if (cp instanceof Rook || cp instanceof Queen || cp instanceof Bishop) {
                int cV = getValidMultiplier(cp, freePosition-cp.position);
                if (cV != Integer.MAX_VALUE) {
                    newMoves.put(cp, cycleThrough(cb, freePosition-cV, cV, enemyColor, null, cp, true));
                }
            } else {
                assert (false);
            }

        }
        return newMoves;
    }

    public static List<ChessMove> cycleThrough(ChessBoard b, int startingPosition, int cv, ChessColor enemyColor, ChessPiece myKing, ChessPiece me, boolean pinFlag) {
        List<ChessMove> result = new ArrayList<>();
        int cp = 0;
        int multiplier = 0;
        do {
            multiplier++;
            cp = startingPosition+cv*multiplier;
            if (!ChessLogic.isValidPosition(cp)) {
                break;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(startingPosition, cp, me, cPiece);
            if (cPiece != null && cPiece.color != enemyColor) {
                break;
            }
            if (!pinFlag && ChessLogic.isPositionThreatened(myKing.position, cm, b, enemyColor)) {
                if (cPiece != null && cPiece.color == enemyColor) {
                    break;
                }
                continue;
            }
            if (cPiece == null) {
                result.add(cm);
            } else if (cPiece.color == enemyColor) {
                result.add(cm);
                break;
            }
        } while (true);
        return result;

    }

    public static int getValidMultiplier(ChessPiece cp, int cpos) {
        if (!ChessLogic.isValidPosition(cpos)) {
            return Integer.MAX_VALUE;
        }
        if (cp instanceof Rook) {
            for (int i = 0; i < Rook.minimalUnit.length; i++) {
                int mU = Rook.minimalUnit[i];
                if (cpos%mU==0) {
                    return mU;
                }
            }
        } else if (cp instanceof Bishop) {
            for (int i = 0; i < Bishop.minimalUnit.length; i++) {
                int mU = Bishop.minimalUnit[i];
                if (cpos%mU==0) {
                    return mU;
                }
            }
        } else if (cp instanceof Queen) {
            for (int i = 0; i < Queen.minimalUnit.length; i++) {
                int mU = Queen.minimalUnit[i];
                if (cpos%mU==0) {
                    return mU;
                }
            }
        } else {
            assert (false);
        }
        return Integer.MAX_VALUE;
    }

    public static void augmentMoveList(ChessBoard cb, Map<ChessPiece, List<ChessMove>> newMoves, ChessColor enemyColor) {
        Map<ChessPiece, List<ChessMove>> toBeAugmented = enemyColor == ChessColor.WHITE ? cb.WHITE_MOVES : cb.BLACK_MOVES;
        for (ChessPiece cp : newMoves.keySet()) {
            List<ChessMove> existingMoves = toBeAugmented.get(cp);
            if (existingMoves != null) {
                existingMoves.addAll(newMoves.get(cp));
            } else {
                toBeAugmented.put(cp, newMoves.get(cp));
            }
        }
    }

    public static void removeNewMoves(ChessBoard cb, Map<ChessPiece, List<ChessMove>> newMoves, ChessColor enemyColor) {
        Map<ChessPiece, List<ChessMove>> toRemove = enemyColor == ChessColor.WHITE ? cb.WHITE_MOVES : cb.BLACK_MOVES;
        for (ChessPiece cp : newMoves.keySet()) {
            List<ChessMove> existingMoves = toRemove.get(cp);
            existingMoves.removeAll(newMoves.get(cp));
            if (existingMoves.isEmpty()) {
                toRemove.remove(cp);
            }
        }
    }

    public static boolean isPositionThreatened(int cp, ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
        applyChessMoveToBoardWithoutLogic(cm, cb);
        //Augment enemy Move List
        Map<ChessPiece, List<ChessMove>> newMoves = getNewMoves(cm, cb, enemyColor);
        augmentMoveList(cb, newMoves, enemyColor);
        boolean threat = !getThreats(cp, cb, enemyColor, true).isEmpty();
        removeNewMoves(cb, newMoves, enemyColor);
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

    public static boolean canEnPassant(ChessBoard b, Pawn p, int cv, ChessColor enemyColor) {
        int newPawnPosition = p.position+cv;
        int enPassantPosition = newPawnPosition-8*p.incrementor;
        ChessPiece cP2 = b.getChessPiece(enPassantPosition);
        if (cP2 instanceof Pawn && cP2.color == enemyColor && cP2.moves == 1 && (p.color == ChessColor.WHITE ? p.position-p.position%8 == 24 : p.position-p.position%8 == 32)) {
            return true;
        }


        return false;
    }
    public static String toStringPosition(int position){
        int xPart= position%8;
        int yPart= (position-xPart) /8;
        return "("+xPart+","+yPart+")";
    }
}
