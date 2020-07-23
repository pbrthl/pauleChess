package Model;

import java.util.ArrayList;

public class Tower extends StoneType{

    private ChessBoard board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public Tower(ChessBoard cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Tower " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 7 : 8);
    }

    public ArrayList<ChessMove> getPossibleMoves(int i, int j) {
        ArrayList<ChessMove> pMoves = new ArrayList<>();
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
