package Model;

import java.util.ArrayList;

public class King extends StoneType {

    private ChessBoard board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public King(ChessBoard cBoard, boolean sWhite){
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
                        if(board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite))) continue;
                        else {
                            pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, icoord, jcoord, isWhite));
                        }
                    } else if(board.isWhiteField(icoord, jcoord) ^ isWhite){
                        if(board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite))) continue;
                        else {
                            pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(icoord, jcoord), i, j, icoord, jcoord, isWhite));
                        }
                    }

                } else continue;

            }

        }


        return pMoves;
    }

}
