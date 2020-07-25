package Model.BoardDataHandle.StoneHandeling.Stonetypes;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.Helperclasses.ChessTuple;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;

public class StoneHandler {

    private BoardDataHandler board;
    private ChessTuple whiteKingPosition = new ChessTuple(4, 0);
    private ChessTuple blackKingPosition =  new ChessTuple(4, 7);

    //private ArrayList<StoneType> stoneTypes = new ArrayList<StoneType>();
    StoneType stoneTypes[];

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



        //stoneTypes = new ArrayList<StoneType>();

        /*stoneTypes.add( new NoStone(ini, true));
        stoneTypes.add( new Pawn(ini, true));
        stoneTypes.add( new Pawn(ini, false));
        stoneTypes.add( new Bishop(ini, true));
        stoneTypes.add(  new Bishop(ini, false));
        stoneTypes.add(  new Horse(ini, true));
        stoneTypes.add(  new Horse(ini, false));
        stoneTypes.add( new Tower(ini, true));
        stoneTypes.add( new Tower(ini, false));
        stoneTypes.add( new Queen(ini, true));
        stoneTypes.add( new Queen(ini, false));
        stoneTypes.add( new King(ini, true));
        stoneTypes.add( new King(ini, false));*/

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
        board.setFieldValue(move.getIndexIB(), move.getIndexJB(), 0);
        board.setFieldValue(move.getIndexIA(), move.getIndexJA(), move.getMovedTypeIndex());
        if(move.getMovedTypeIndex() == 11 || move.getMovedTypeIndex() == 12){
            if(move.getMovedTypeIndex() == 11){
                blackKingPosition = new ChessTuple(move.getIndexIA(), move.getIndexJA());
            } else {
                whiteKingPosition = new ChessTuple(move.getIndexIA(), move.getIndexJA());
            }
        }
    }


    public void undoChessMove(ChessMove move) {

        board.setFieldValue(move.getIndexIB(), move.getIndexJB(), move.getMovedTypeIndex() );
        board.setFieldValue(move.getIndexIA(), move.getIndexJA(), move.getTargetTypeIndex());

        if(move.getMovedTypeIndex() == 11 || move.getMovedTypeIndex() == 12){
            if(move.getMovedTypeIndex() == 11){
                blackKingPosition = new ChessTuple(move.getIndexIB(), move.getIndexJB());
            } else {
                whiteKingPosition = new ChessTuple(move.getIndexIB(), move.getIndexJB());
            }
        }

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
        for(int io = -1; io < 2; io++){
            for(int jo = -1; jo < 2; jo++){
                if(board.isKing(ic + io, jc + jo)){
                    if(! (io == 0 && ic == 0)){
                        return true;
                    }
                }
            }
        }

        //Threat by horse?
        int hoffsett = 0;
        for(int idirections = 0; idirections < 2; idirections++){
            hoffsett = (idirections == 0 ? -3 : 3);
            if(board.isHorse(ic + hoffsett, jc + 1)  ){
                if(board.isWhiteField(ic + hoffsett, jc + 1) ^ wStone) return true;
            } else if(board.isHorse(ic + hoffsett, jc - 1)){
                if(board.isWhiteField(ic + hoffsett, jc + 1) ^ wStone) return true;
            }
        }

        for(int jdirections = 0; jdirections < 2; jdirections++){
            hoffsett = (jdirections == 0 ? -3 : 3);
            if(board.isHorse(ic + 1, jc + hoffsett)  ){
                if(board.isWhiteField(ic + 1, jc + hoffsett) ^ wStone) return true;
            } else if(board.isHorse(ic - 1, jc +  hoffsett)){
                if(board.isWhiteField(ic - 1, jc +  hoffsett) ^ wStone) return true;
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
                     if(! board.isBoardField(ic + idiomul * dio, jc + jdiomul * jc)) break;
                     if(! board.isEmptyField(ic + idiomul * dio, jc + jdiomul * jc) && ! board.isBishop(ic + idiomul * dio, jc + jdiomul * jc)) break;
                     if(board.isBishop(ic + idiomul * dio, jc + jdiomul * jc)){
                         if(board.isWhiteField(ic + idiomul * dio, jc + jdiomul * jc) ^ wStone){
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
                    if(! board.isBoardField(ic + idiomul * dio, jc + jdiomul * jc)) break;
                    if(! board.isEmptyField(ic + idiomul * dio, jc + jdiomul * jc) && ! board.isQueen(ic + idiomul * dio, jc + jdiomul * jc)) break;
                    if(board.isQueen(ic + idiomul * dio, jc + jdiomul * jc)){
                        if(board.isWhiteField(ic + idiomul * dio, jc + jdiomul * jc) ^ wStone){
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
