package Model;

import java.util.ArrayList;

public abstract class StoneType {
    private ChessBoard board;
    private boolean isWhite;
    private String stoneTypeName;
    private int stoneTypeIndex;

    public ArrayList<ChessMove> getPossibleMoves(int i, int j){
        return new ArrayList<ChessMove>();
    }

}
