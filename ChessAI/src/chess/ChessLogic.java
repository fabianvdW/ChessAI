package chess;

import chess.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<ChessPiece, List<ChessMove>> getNewMoves(ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
        Map<ChessPiece, List<ChessMove>> newMoves = new HashMap<>();
        if (cm == null) {
            return newMoves;
        }
        List<ChessPiece> enemyPieces = enemyColor == ChessColor.WHITE ? cb.WHITE_PIECES : cb.BLACK_PIECES;
        ChessPosition freePosition = cm.from;
        ChessPosition newPosition = cm.to;
        for (ChessPiece cp : enemyPieces) {
            if (cp instanceof King || cp instanceof Knight) {
                continue;
            } else if (cp instanceof Pawn) {
                //Free position can be in front of pawn, Pawn might be able to move forward once or twice now!
                List<ChessMove> newM = new ArrayList<>();
                Pawn p = (Pawn) cp;
                ChessPosition p0 = p.position.addChessVector(p.minimalUnit[0]);
                ChessPosition p1 = p.position.addChessVector(p.minimalUnit[1]);
                ChessPosition p2 = p.position.addChessVector(p.minimalUnit[2]);
                ChessPosition p3 = p.position.addChessVector(p.minimalUnit[3]);
                if (freePosition.equals(p0)) {
                    //One move is definitly new!
                    newM.add(new ChessMove(p.position.clone(), p0, p, null));

                    //2 moves if in starting position and other field also free
                    if (p.moves == 0 && cb.getChessPiece(p1) == null) {
                        newM.add(new ChessMove(p.position.clone(), p1, p, null));
                    }

                } else if (freePosition.equals(p1)) {
                    if (p.moves == 0 && cb.getChessPiece(p1) == null) {
                        newM.add(new ChessMove(p.position.clone(), p1, p, null));
                    }
                }
                if (freePosition.equals(p2)) {
                    //Check en passant
                    if (canEnPassant(cb, p, p.minimalUnit[2], enemyColor)) {
                        newM.add(new ChessMove(p.position.clone(), p2, p, cb.getChessPiece(p2.addChessVector(new ChessVector(0, -p.incrementor)))));
                    }
                } else if (newPosition.equals(p2)) {
                    //Can capture
                    newM.add(new ChessMove(p.position.clone(), p2, cp, cm.moved));
                }
                if (freePosition.equals(p3)) {
                    //Check en passant
                    if (canEnPassant(cb, p, p.minimalUnit[3], enemyColor)) {
                        newM.add(new ChessMove(p.position.clone(), p3, p, cb.getChessPiece(p3.addChessVector(new ChessVector(0, -p.incrementor)))));
                    }
                } else if (newPosition.equals(p3)) {
                    //Can capture
                    newM.add(new ChessMove(p.position.clone(), p3, cp, cm.moved));
                }

                newMoves.put(cp, newM);
            } else if (cp instanceof Rook || cp instanceof Queen || cp instanceof Bishop) {
                ChessVector cV = getValidMultiplier(cp, freePosition.addChessVector(new ChessVector(-cp.position.getX(), -cp.position.getY())));
                if (cV != null) {
                    newMoves.put(cp, cycleThrough(cb, new ChessPosition((int) (freePosition.getX() - cV.x), (int) (freePosition.getY() - cV.y)), cV, enemyColor, null, cp, true));
                }
            } else {
                assert (false);
            }

        }
        return newMoves;
    }

    public static List<ChessMove> cycleThrough(ChessBoard b, ChessPosition startingPosition, ChessVector cv, ChessColor enemyColor, ChessPiece myKing, ChessPiece me, boolean pinFlag) {
        List<ChessMove> result = new ArrayList<>();
        ChessPosition cp = null;
        int multiplier = 0;
        do {
            multiplier++;
            cp = startingPosition.addChessVector(cv.times(multiplier));
            if (cp == null) {
                break;
            }
            ChessPiece cPiece = b.getChessPiece(cp);
            ChessMove cm = new ChessMove(startingPosition.clone(), cp, me, cPiece);
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

    public static ChessVector getValidMultiplier(ChessPiece cp, ChessPosition cpos) {
        if (cpos == null) {
            return null;
        }
        ChessVector cv = cpos.toChessVector();
        if (cp instanceof Rook) {
            for (int i = 0; i < Rook.minimalUnit.length; i++) {
                ChessVector mU = Rook.minimalUnit[i];
                if (cv.isMultiplierOf(mU)) {
                    return mU;
                }
            }
        } else if (cp instanceof Bishop) {
            for (int i = 0; i < Bishop.minimalUnit.length; i++) {
                ChessVector mU = Bishop.minimalUnit[i];
                if (cv.isMultiplierOf(mU)) {
                    return mU;
                }
            }
        } else if (cp instanceof Queen) {
            for (int i = 0; i < Queen.minimalUnit.length; i++) {
                ChessVector mU = Queen.minimalUnit[i];
                if (cv.isMultiplierOf(mU)) {
                    return mU;
                }
            }
        } else {
            assert (false);
        }
        return null;
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

    public static boolean isPositionThreatened(ChessPosition cp, ChessMove cm, ChessBoard cb, ChessColor enemyColor) {
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

    public static boolean canEnPassant(ChessBoard b, Pawn p, ChessVector cv, ChessColor enemyColor) {
        ChessPosition newPawnPosition = p.position.addChessVector(cv);
        ChessPosition enPassantPosition = newPawnPosition.addChessVector(new ChessVector(0, -p.incrementor));
        ChessPiece cP2 = b.getChessPiece(enPassantPosition);
        if (cP2 instanceof Pawn && cP2.color == enemyColor && cP2.moves == 1 && (p.color == ChessColor.WHITE ? p.position.getY() == 3 : p.position.getY() == 4)) {
            return true;
        }


        return false;
    }
}
