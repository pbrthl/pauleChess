package Model;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class Chessboard {

    private BoardDataHandler board = new BoardDataHandler();


    public StoneType getStoneType(int i, int j){
        return board.getStonetype(i, j);
    }

    public boolean isWhiteField(int i, int j){
        return i % 2 != j % 2;
    }

    public void makeMove(ChessMove move){
        board.moveStone(move);
    }

    public boolean isEmptyField(int i, int j){
        return board.isEmptyField(i, j);
    }

    public boolean stoneIsWhite(int i, int j){
        return board.isWhiteField(i, j);
    }

    public ArrayList<ChessMove> getPossibleMovesForField(int i, int j){
        return board.getMovesForField(i, j);
    }

    public int getStoneTypeIndex(int i, int j){
        return board.getFieldValue(i, j);
    }





}
