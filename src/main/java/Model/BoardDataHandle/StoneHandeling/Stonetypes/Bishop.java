package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class Bishop extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public Bishop(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Bishop " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 3 : 4);
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


        return pMoves;
    }


}
