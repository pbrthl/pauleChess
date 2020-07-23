package Model;

import java.util.ArrayList;

public class Pawn extends StoneType {

    private ChessBoard board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public ArrayList<ChessMove> getPossibleMoves(int i, int j){
        ArrayList<ChessMove> pMoves = new ArrayList<ChessMove>();
        int offset;
        if(isWhite){
            offset = 1;
        } else {
            offset = -1;
        }


        for(int ii = -1; ii < 2; ii++){
            if(ii == 0 && board.isEmptyField(ii, j + offset)){
                if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite));
            } else {
                if(! board.isEmptyField(i +ii, j + offset) && board.isWhiteField(i +ii, j + offset) ^ isWhite){
                    if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite));
                }
            }
        }

        if((j == 1 && isWhite && board.isEmptyField(i, 3))){
            if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i,j, i, 3, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, 0, i,j, i, 3, isWhite));
        }

        if((j == 6 && ! isWhite && board.isEmptyField(i, 4))){
            if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i,j, i, 4, isWhite))) pMoves.add(new ChessMove(stoneTypeIndex, 0, i,j, i, 4, isWhite));
        }

        return pMoves;
    }

    public Pawn(ChessBoard cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Pawn " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 1 : 2);
    }

}
