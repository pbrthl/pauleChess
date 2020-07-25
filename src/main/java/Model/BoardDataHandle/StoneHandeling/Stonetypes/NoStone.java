package Model.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.StoneHandeling.StoneType;

import java.util.ArrayList;

public class NoStone extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public NoStone(BoardDataHandler cBoard, boolean sWhite){
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
