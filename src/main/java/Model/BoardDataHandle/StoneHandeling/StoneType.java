package Model.StoneHandeling;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;

import java.util.ArrayList;

public abstract class StoneType {
    private BoardDataHandler board;
    private boolean isWhite;
    private String stoneTypeName;
    private int stoneTypeIndex;

    public ArrayList<ChessMove> getPossibleMoves(int i, int j){
        return new ArrayList<ChessMove>();
    }

}
