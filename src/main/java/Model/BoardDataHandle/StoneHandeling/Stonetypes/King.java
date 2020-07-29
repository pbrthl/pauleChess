package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class King extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public King(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "King " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 11 : 12);
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j) {
        ArrayList<ChessMove> pMoves = new ArrayList<>();

        int icoord;
        int jcoord;

        for(int iAxis = -1; iAxis < 2; iAxis++){

            for(int jAxis = -1; jAxis < 2; jAxis++){

                if(iAxis == 0 && jAxis == 0) continue;
                icoord = i + iAxis;
                jcoord = j + jAxis;

                if(board.isBoardField(icoord, jcoord)){

                    if(board.isEmptyField(icoord, jcoord)){
                        if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite))) {

                            if(board.kingMovedYet(isWhite)) pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite));
                             else pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite).setFirstMoveKing(true));
                        }
                    } else if(board.isWhiteField(icoord, jcoord) ^ isWhite){
                        if(board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite))) continue;
                        else {
                            if(board.kingMovedYet(isWhite)) pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite));
                            else pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite).setFirstMoveKing(true));
                        }
                    }

                }

            }

        }
        //Rochade
        if(! board.kingMovedYet(isWhite) && ! board.getStoneHandler().fieldIsThreatened(i, j)){
            int row = (isWhite ? 0 : 7);
            int dir = 0;
            for(int directions = 0; directions < 2; directions++){
                if(! board.towerMovedYet(isWhite , (directions == 0 ))){
                    dir = (directions == 0 ? -1 : 1);
                    for(int steps = 1; steps < 5; steps++){
                        if(board.isEmptyField(i + steps * dir, row)){
                            if(board.getStoneHandler().fieldIsThreatened(i + steps * dir, row)) break;
                        }   else {

                            if(board.isTower(i + steps * dir, row) && ! board.getStoneHandler().fieldIsThreatened(i + steps * dir, row)){

                                if((directions == 0 && steps == 4) || (directions == 1 && steps == 3)){
                                    if(! board.getStoneHandler().fieldIsThreatened(i + steps * dir, row)){
                                        pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, i + 2 * dir, row, isWhite, true).setFirstMoveKing(true));
                                    }
                                }

                            } else break;

                        }
                    }
                }
            }
        }



        return pMoves;
    }

}
