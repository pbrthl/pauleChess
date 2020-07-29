package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class Queen extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public Queen(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Queen " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 9 : 10);
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j) {
        ArrayList<ChessMove> pMoves = new ArrayList<>();

        int iMul;
        int jMul;
        int icoord;
        int jcoord;

        for(int iaxe = 0; iaxe < 2; iaxe++){
            iMul = (iaxe == 0 ? 1 : - 1);

            for (int jAxe = 0; jAxe < 2; jAxe++){
                jMul = (jAxe == 0 ? 1 : -1);

                for(int steps = 1; steps <= 7; steps++){
                    icoord = i + iMul * steps;
                    jcoord = j + jMul * steps;
                    if( board.isBoardField(icoord, jcoord)){

                        if(board.isEmptyField(icoord, jcoord)){
                            if(! board.checkKingThreateningMove( new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite));
                        } else {

                            if(board.isWhiteField(icoord, jcoord) ^ isWhite){
                                if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite))){
                                    pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite));
                                } else break;
                            } else break;

                        }

                    } else break;
                }

            }

        }


        int iSteps;
        int jSteps;
        int stepDir;

        for(int axes = 0; axes < 2; axes++){
            iSteps = (axes == 0 ? 1 : 0);
            jSteps = (axes == 0 ? 0 : 1);

            for(int direcs = 0; direcs < 2; direcs++){
                stepDir = (direcs == 0 ? 1 : -1);

                for(int steps = 1; steps <= 7; steps++){

                    if(board.isBoardField(i + iSteps * stepDir * steps, j + jSteps * stepDir * steps)){
                        if(! board.isEmptyField(i + iSteps * stepDir * steps, j + jSteps * stepDir * steps)){
                            if(board.isWhiteField(i + iSteps * stepDir * steps, j + jSteps * stepDir * steps) ^ isWhite){
                                if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(i + iSteps * stepDir * steps, j + jSteps * stepDir * steps), i, j, i + iSteps * stepDir * steps, j + jSteps * stepDir * steps, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + iSteps * stepDir * steps, j + jSteps * stepDir * steps), i, j, i + iSteps * stepDir * steps, j + jSteps * stepDir * steps, isWhite));
                            } else break;
                        } else {
                            if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i, j, i + iSteps * stepDir * steps, j + jSteps * stepDir * steps, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, i + iSteps * stepDir * steps, j + jSteps * stepDir * steps, isWhite));
                        }
                    } else break;

                }

            }

        }


        return pMoves;
    }


}
