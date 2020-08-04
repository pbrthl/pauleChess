package Model.BoardDataHandle.StoneHandeling;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.Helperclasses.ChessTuple;
import Model.BoardDataHandle.StoneHandeling.Stonetypes.*;

import java.util.ArrayList;

public class StoneHandler {

    private BoardDataHandler board;
    private ChessTuple whiteKingPosition = new ChessTuple(4, 0);
    private ChessTuple blackKingPosition =  new ChessTuple(4, 7);



    private StoneType stoneTypes[];

    public StoneType getStoneTypeForIndex(int stIndex){
        return stoneTypes[stIndex];
    }

    public StoneHandler(BoardDataHandler ini){
        board = ini;
        stoneTypes = new StoneType[13];

        stoneTypes[0] = new NoStone(ini, true);
        stoneTypes[1] =  new Pawn(ini, true);
        stoneTypes[2] =  new Pawn(ini, false);
        stoneTypes[3] =  new Bishop(ini, true);
        stoneTypes[4] =   new Bishop(ini, false);
        stoneTypes[5] =   new Horse(ini, true);
        stoneTypes[6] =   new Horse(ini, false);
        stoneTypes[7] =  new Tower(ini, true);
        stoneTypes[8] =  new Tower(ini, false);
        stoneTypes[9] =  new Queen(ini, true);
        stoneTypes[10] =  new Queen(ini, false);
        stoneTypes[11] =  new King(ini, true);
        stoneTypes[12] =  new King(ini, false);

    };

    public ChessTuple getWhiteKingPosition() {
        return whiteKingPosition;
    }


    public void setWhiteKingPosition(ChessTuple whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public ChessTuple getBlackKingPosition() {
        return blackKingPosition;
    }

    public void setBlackKingPosition(ChessTuple blackKingPosition) {
        this.blackKingPosition = blackKingPosition;
    }

    public void moveStone(ChessMove move){



        if(! board.isBoardField(move.getIndexIA(), move.getIndexJA()) || ! board.isBoardField(move.getIndexIB(), move.getIndexJB())) return;

        board.getMoveHistory().push(move);

        if(! move.isRojade()) {

            if(move.getMovedTypeIndex() == 7 || move.getMovedTypeIndex() == 8){
                int towColumn;
                int row = (move.isWhite() ? 0 : 7);
                if(move.getIndexJB() == row){
                    for(int possTower = 0; possTower < 2; possTower++){
                        towColumn = (possTower == 0 ? 0 : 7);
                        if(move.getIndexIB() == towColumn && move.getIndexJB() == row) board.setTowerMoved(move.isWhite(), possTower == 0, true);
                    }
                }
            }

            board.setFieldValue(move.getIndexIB(), move.getIndexJB(), 0);
            board.setFieldValue(move.getIndexIA(), move.getIndexJA(), move.getMovedTypeIndex());
            if (move.getMovedTypeIndex() == 11 || move.getMovedTypeIndex() == 12) {
                board.setKingMoved(move.isWhite(), true);
                if (move.getMovedTypeIndex() == 11) {
                    whiteKingPosition = new ChessTuple(move.getIndexIA(), move.getIndexJA());
                } else {
                    blackKingPosition = new ChessTuple(move.getIndexIA(), move.getIndexJA());
                }
            } else if(move.isEnPassantStrike()){
                board.setFieldValue(move.getIndexIA(), move.getIndexJB(), 0);
            }
        } else {

            //Rojade

            int row = (move.isWhite() ? 0 : 7);
            board.setKingMoved(move.isWhite(), true);
            board.setTowerMoved(move.isWhite(), (move.getIndexIB() > move.getIndexIA()), true);
            board.setFieldValue((move.getIndexIB() < move.getIndexIA() ?  5 : 3), row, (move.isWhite() ? 7 : 8));
            board.setFieldValue((move.getIndexIB() < move.getIndexIA() ? 6 : 2), row, (move.isWhite() ? 11 : 12));
            board.setFieldValue(4, row,  0);
            board.setFieldValue((move.getIndexIB() < move.getIndexIA() ? 7 : 0), row,0);

        }

        if( ! (move.getChangeType().getStoneTypeIndex() == 0)){
            board.setFieldValue(move.getIndexIA(), move.getIndexJA(), move.getChangeType().getStoneTypeIndex());
        }

    }


    public void undoChessMove(ChessMove move) {

        board.setFieldValue(move.getIndexIB(), move.getIndexJB(), move.getMovedTypeIndex() );
        board.setFieldValue(move.getIndexIA(), move.getIndexJA(), move.getTargetTypeIndex());
        board.getMoveHistory().pop();

        if(move.getMovedTypeIndex() == 11 || move.getMovedTypeIndex() == 12){

            if(move.isFirstMoveKing()){
                board.setKingMoved(move.isWhite(), false);
            }

            if(move.getMovedTypeIndex() == 11){
                whiteKingPosition = new ChessTuple(move.getIndexIB(), move.getIndexJB());
            } else {
                blackKingPosition = new ChessTuple(move.getIndexIB(), move.getIndexJB());
            }

            if(move.isRojade()){
                boolean leftTower = (move.getIndexIA() < move.getIndexIB());
                int row = (move.isWhite() ? 0 : 7);
                board.setKingMoved(move.isWhite(), false);
                board.setTowerMoved(move.isWhite(), leftTower, false);
                board.setFieldValue(4, row, (move.isWhite() ? 11 : 12));
                board.setFieldValue((leftTower ? 0 : 7), row, (move.isWhite() ? 7 : 8));
                board.setFieldValue((leftTower ? 1 : 5), row, 0);
                board.setFieldValue((leftTower ? 2 : 6), row, 0);

            }
        } else if(move.getMovedTypeIndex() == 7 || move.getMovedTypeIndex() == 8){

            if(move.isFirstMoveLeftTower() || move.isFirstMoveRightTower()){
                board.setTowerMoved(move.isWhite(), move.isFirstMoveLeftTower(), false);
            }

        }
    }

    public StoneType getStoneType(int ic, int jc){
        return stoneTypes[board.getFieldValue(ic, jc)];
    }

    public ArrayList<ChessMove> getMovesForField(int ic, int jc){
        if(! board.isBoardField(ic, jc)) return new ArrayList<>();
        return stoneTypes[board.getFieldValue(ic, jc)].getPossibleMoves(ic, jc);
    }


    public boolean fieldIsThreatened(int ic, int jc){
        if(! board.isBoardField(ic, jc)) return false;
        boolean wStone = board.isWhiteField(ic, jc);
        boolean threatened = false;


        //Threat by pawns?

        int offset = (wStone ? 1 : - 1);
        int iOffset = -1;
        for(int lindex = 0; lindex < 2; lindex++){
            if(board.isPawn(ic + iOffset, jc + offset)){
                if(board.isWhiteField(ic + iOffset, jc + offset) ^ wStone){
                    return true;
                }
            }
            iOffset *= -1;
        }


        //ThreatByKings
        int kingI;
        int kingJ;
        for(int io = -1; io < 2; io++){
            for(int jo = -1; jo < 2; jo++){
                kingI = ic + io;
                kingJ = jc + jo;
                if(board.isBoardField(kingI, kingJ)){
                    if(board.isKing(kingI, kingJ) ){
                        if(board.isWhiteField(kingI, kingJ) ^ wStone){
                            if(! (io == 0 && jc == 0)){
                                return true;
                            }
                        }

                    }
                }
            }
        }


        //Threat by horse?




        int iOff;
        int jOff;
        int iMul = 1;
        int jMul = 1;

        for(int axeInd = 0; axeInd < 2; axeInd ++){
            iOff = (axeInd == 0 ? 2 : 1);
            jOff = (axeInd == 0 ? 1 : 2);

            for(int mulIndI = 0; mulIndI < 2; mulIndI ++){
                iMul *= -1;
                for(int mulIndJ = 0; mulIndJ < 2; mulIndJ ++){
                    jMul *= -1;


                    if(board.isBoardField(ic + iOff * iMul, jc + jOff * jMul)){
                        if(board.isHorse(ic + iOff * iMul, jc + jOff * jMul)){
                            if(board.isWhiteField(ic + iOff * iMul, jc + jOff * jMul) ^ wStone){
                                return true;
                            }
                        }
                    }

                }
            }

        }




        // Threat by Tower
        for(int io = 1; io <= (7 - ic); io++){

            if(! board.isEmptyField(ic + io, jc) && ! board.isTower(ic + io, jc)) break;

            if(board.isTower(ic + io, jc)){
                if(board.isWhiteField(ic + io, jc) ^ wStone) return true;
            }
        }

        for(int io = -1; io >= -ic; io--){

            if(! board.isEmptyField(ic + io, jc) && ! board.isTower(ic + io, jc)) break;

            if(board.isTower(ic + io, jc)){
                if(board.isWhiteField(ic + io, jc) ^ wStone) return true;
            }
        }



        for(int jo = 1; jo <= (7 - jc); jo++){

            if(! board.isEmptyField(ic , jc + jo) && ! board.isTower(ic , jc + jo)) break;

            if(board.isTower(ic , jc + jo)){
                if(board.isWhiteField(ic , jc + jo) ^ wStone) return true;
            }
        }


        for(int jo = -1; jo >= -jc; jo--){

            if(! board.isEmptyField(ic , jc + jo) && ! board.isTower(ic , jc + jo)) break;

            if(board.isTower(ic , jc + jo)){
                if(board.isWhiteField(ic, jc + jo) ^ wStone) return true;
            }
        }


        //Threat by bishop

        int idiomul = 1;
        int jdiomul = 1;


         for(int ausi = 0; ausi < 2; ausi ++){
             idiomul *= -1;
             for(int inind = 0; inind < 2; inind ++){
                 jdiomul *= -1;
                 for(int dio = 1;  dio <= 7; dio++){
                     if(! board.isBoardField(ic + idiomul * dio, jc + jdiomul * dio)) break;
                     if(! board.isEmptyField(ic + idiomul * dio, jc + jdiomul * dio) && ! board.isBishop(ic + idiomul * dio, jc + jdiomul * dio)) break;
                     if(board.isBishop(ic + idiomul * dio, jc + jdiomul * dio)){
                         if(board.isWhiteField(ic + idiomul * dio, jc + jdiomul * dio) ^ wStone){
                             return true;
                         } else break;
                     }
                 }
             }
         }


         //Threat by queen

        for(int ausi = 0; ausi < 2; ausi ++){
            idiomul *= -1;
            for(int inind = 0; inind < 2; inind ++){
                jdiomul *= -1;
                for(int dio = 1;  dio <= 7; dio++){
                    if(! board.isBoardField(ic + idiomul * dio, jc + jdiomul * dio)) break;
                    if(! board.isEmptyField(ic + idiomul * dio, jc + jdiomul * dio) && ! board.isQueen(ic + idiomul * dio, jc + jdiomul * dio)) break;
                    if(board.isQueen(ic + idiomul * dio, jc + jdiomul * dio)){
                        if(board.isWhiteField(ic + idiomul * dio, jc + jdiomul * dio) ^ wStone){
                            return true;
                        } else break;
                    }
                }
            }
        }



        for(int io = 1; io <= (7 - ic); io++){

            if(! board.isEmptyField(ic + io, jc) && ! board.isQueen(ic + io, jc)) break;

            if(board.isQueen(ic + io, jc)){
                if(board.isWhiteField(ic + io, jc) ^ wStone) return true;
            }
        }

        for(int io = -1; io >= -ic; io--){

            if(! board.isEmptyField(ic + io, jc) && ! board.isQueen(ic + io, jc)) break;

            if(board.isQueen(ic + io, jc)){
                if(board.isWhiteField(ic + io, jc) ^ wStone) return true;
            }
        }



        for(int jo = 1; jo <= (7 - jc); jo++){

            if(! board.isEmptyField(ic , jc + jo) && ! board.isQueen(ic , jc + jo)) break;

            if(board.isQueen(ic , jc + jo)){
                if(board.isWhiteField(ic , jc + jo) ^ wStone) return true;
            }
        }


        for(int jo = -1; jo >= -jc; jo--){

            if(! board.isEmptyField(ic , jc + jo) && ! board.isTower(ic , jc + jo)) break;

            if(board.isQueen(ic , jc + jo)){
                if(board.isWhiteField(ic, jc + jo) ^ wStone) return true;
            }
        }
        return threatened;
    }

}
