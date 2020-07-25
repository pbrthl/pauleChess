package Model.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.StoneHandeling.StoneType;

import java.util.ArrayList;

public class Horse extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public Horse(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Horse " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 5 : 6);
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j){
        ArrayList<ChessMove> pMoves = new ArrayList<>();

        int iOff;
        int jOff;
        int iMul = 1;
        int jMul = 1;

        for(int axeInd = 0; axeInd < 2; axeInd ++){
            iOff = (axeInd == 0 ? 3 : 1);
            jOff = (axeInd == 0 ? 1 : 3);

            for(int mulIndI = 0; mulIndI < 2; mulIndI ++){
                iMul += -1;
                for(int mulIndJ = 0; mulIndJ < 2; mulIndJ ++){
                    jMul *= -1;


                    if(board.isBoardField(i + iOff * iMul, j + jOff * jMul)){
                        if(board.isEmptyField(i + iOff * iMul, j + jOff * jMul) || (board.isWhiteField(i + iOff * iMul, j + jOff * jMul) ^ isWhite)){
                            if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(i + iOff * iMul, j + jOff * jMul), i, j, i + iOff * iMul, j + jOff * jMul, isWhite))){
                                pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + iOff * iMul, j + jOff * jMul), i, j, i + iOff * iMul, j + jOff * jMul, isWhite));
                            }
                        }
                    }



                }
            }

        }



        return pMoves;
    }


}
