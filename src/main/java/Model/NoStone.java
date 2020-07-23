package Model;

import java.util.ArrayList;

public class NoStone extends StoneType {

    private ChessBoard board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public NoStone(ChessBoard cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "EmptyField";
        stoneTypeIndex = (sWhite ? 9 : 10);
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j) {
        ArrayList<ChessMove> pMoves = new ArrayList<>();
        return pMoves;
    }
}
