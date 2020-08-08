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

    public  void setFieldValue(int i, int j, int val){
        board.setFieldValue(i, j, val);
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

    public ArrayList<ChessMove> getPossibleMovesForPlayer(boolean white){
        return board.getPossiblePlayerMoves(white);
    }

    public int getStoneTypeIndex(int i, int j){
        return board.getFieldValue(i, j);
    }

    public int getNumberOfMove(){
        return board.getMoves() + 1;
    }

    public boolean whiteKingCheck(){
        return board.kingIsThreatened(true);
    }

    public boolean blackKingCheck(){
        return board.kingIsThreatened(true);
    }

    public void undoMove(ChessMove move){
        board.undoMove(move);
    }

    public  void ignoreKingThread(){
        board.ignoreKingThread();
    }

    public StoneType getStonetypeForIndex(int i){
        return board.getStoneHandler().getStoneTypeForIndex(i);
    }

    public void dontIgnoreKingThread(){
        board.dontIgnoreKingThread();
    }

    public void reinitialize(){
        board.initializeChessBoard();
    }

}
