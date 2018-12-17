package helpers;

public class Constants {
    public final static String ROOK_REPRESENTATION_WHITE="\u2656";
    public final static String ROOK_REPRESENTATION_BLACK="\u265C";

    public final static String BISHOP_REPRESENTATION_WHITE="\u2657";
    public final static String BISHOP_REPRESENTATION_BLACK="\u265D";

    public final static String KNIGHT_REPRESENTATION_WHITE="\u2658";
    public final static String KNIGHT_REPRESENTATION_BLACK="\u265E";

    public final static String KING_REPRESENTATION_WHITE="\u2654";
    public final static String KING_REPRESENTATION_BLACK="\u265A";

    public final static String QUEEN_REPRESENTATION_WHITE="\u2655";
    public final static String QUEEN_REPRESENTATION_BLACK="\u265B";

    public final static String PAWN_REPRESENTATION_WHITE="\u2659";
    public final static String PAWN_REPRESENTATION_BLACK="\u265F";

    public final static String STANDARD_CHESS_BOARD[][] = {
            {"r", "n", "b", "q", "k", "b", "n", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"R", "N", "B", "Q", "K", "B", "N", "R"}};

    public final static boolean VERBOSE=false;

}
