package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class Pawn extends StoneType {

    private BoardDataHandler board;
    private boolean isWhite = true;
    private String stoneTypeName = "";
    private int stoneTypeIndex = -1;

    public ArrayList<ChessMove> getPossibleMoves(int i, int j){
        //System.out.println("Checking moves for pawn..");
        ArrayList<ChessMove> pMoves = new ArrayList<ChessMove>();
        int offset;
        if(isWhite){
            offset = 1;
        } else {
            offset = -1;
        }

        for(int ii = -1; ii < 2; ii++){


            //one step ahead, not diagonal
            // stone change on last field must be considered
            if(ii == 0 && board.isEmptyField(i , j + offset)){
                //System.out.println("Nondiagonal check..");

                if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite))){

                    if(! (j + offset == 7 || j + offset == 0)) pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite));
                    //stone change
                    if(j + offset == 7 || j + offset == 0){

                        int stoneIndexStart = (isWhite ? 3 : 4);

                        for(int stoneIndexOff = stoneIndexStart; stoneIndexOff < 11; stoneIndexOff += 2){
                            pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, ii + i, j + offset, isWhite, board.getStoneHandler().getStoneTypeForIndex(stoneIndexStart + stoneIndexOff)));
                        }

                    }

                }

            } else {
                //System.out.println("Diagonal check..");

                //diagonal move, not en passant
                //System.out.println("Checking diagonal move..");
                //System.out.println((isWhite? "White " : "Black ") + "pawn on (" + i +", " + j + ") check for (" + (i + ii) + ", " + (j + offset) + ")");

                if (board.isBoardField(i + ii, j + offset)){
                    if (!(ii == 0) && !board.isEmptyField(i + ii, j + offset) && (board.isWhiteField(i + ii, j + offset) ^ isWhite)) {
                        //System.out.println("Check kingthreatening..");
                        if (! board.checkKingThreateningMove
                                (new ChessMove(stoneTypeIndex,
                                        board.getFieldValue(
                                                i + ii,
                                                j + offset),
                                        i,
                                        j,
                                        ii + i,
                                        j + offset,
                                        isWhite))){
                            //System.out.println("There should be moves..");
                            //System.out.println("Checked kingthreatening..");
                            if (!(j + offset == 7 || j + offset == 0)) {
                               // System.out.println("Move found. Move is added.");
                                pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite));
                            } else {
                                //System.out.println("Move found. Move is added. Its a typechangemove.");
                                int stoneIndexStart = (isWhite ? 3 : 4);
                                for (int stoneIndexOff = stoneIndexStart; stoneIndexOff < 11; stoneIndexOff += 2) {
                                    pMoves.add(new ChessMove(stoneTypeIndex, board.getFieldValue(i + ii, j + offset), i, j, ii + i, j + offset, isWhite, board.getStoneHandler().getStoneTypeForIndex(stoneIndexStart + stoneIndexOff)));
                                }
                            }
                    } //else System.out.println("Considered move is kingthreatening.");
                    } //else System.out.println("Move not possible. Field not empty or of same color.");
                } //else System.out.println("Move not possible, no boardfield.");
                //System.out.println("Diagonal check finished..");
            }


        }



        //strike en passant
        int dirOffSet;
        if(! board.getMoveHistory().isEmpty()){
            if(board.getMoveHistory().peek().isEnPassantPawn()){
                ChessMove npMove = board.getMoveHistory().peek();
                for(int lOR = 0; lOR < 2; lOR++){
                    dirOffSet = (lOR == 0 ? -1 : 1);
                    if((i + dirOffSet == npMove.getIndexIA() && j  == npMove.getIndexJA())){
                        if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i, j,  i + dirOffSet, j + offset, isWhite).setEnPassantStrike()))
                        pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j,  i + dirOffSet, j + offset, isWhite).setEnPassantStrike());
                        else break;
                    }
                }
            }
        }

        //skip a field

        int pawnRow = (isWhite ? 1 : 6);
        int skippedRow = (isWhite ? 2 : 5);
        int targetRow = (isWhite ? 3 : 4);
        boolean skipFieldsThreatened;
        if((j == pawnRow && board.isEmptyField(i, targetRow) && board.isEmptyField(i, skippedRow))){
            if(! board.checkKingThreateningMove(new ChessMove(stoneTypeIndex, 0, i,j, i, targetRow, isWhite))) {
                board.setFieldValue(i, skippedRow, (isWhite ? 1 : 2));
                skipFieldsThreatened = board.getStoneHandler().fieldIsThreatened(i, skippedRow);
                board.setFieldValue(i, skippedRow, 0);
                if(!skipFieldsThreatened) pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, i, targetRow, isWhite));
                else pMoves.add(new ChessMove(stoneTypeIndex, 0, i, j, i, targetRow, isWhite).setEnPassantPawn());
            }
        }

        return pMoves;
    }


    @Override
    public int getStoneTypeIndex() {
        return stoneTypeIndex;
    }

    public Pawn(BoardDataHandler cBoard, boolean sWhite){
        board = cBoard;
        isWhite = sWhite;
        stoneTypeName = "Pawn " + (sWhite ? "White" : "Black");
        stoneTypeIndex = (sWhite ? 1 : 2);
    }

}
