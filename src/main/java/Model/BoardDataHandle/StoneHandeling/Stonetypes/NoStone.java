package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class NoStone extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = 0;

    public NoStone(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "EmptyField";
        stoneTypeIndex = 0;
    }

    @Override
    public int getStoneTypeIndex() {
        return stoneTypeIndex;
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j) {
        ArrayList<ChessMove> pMoves = new ArrayList<>();
        return pMoves;
    }
}
