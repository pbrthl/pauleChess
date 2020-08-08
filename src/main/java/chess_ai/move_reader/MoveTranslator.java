package chess_ai.move_reader;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;
import View.CLIView;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveTranslator {

    private Chessboard board;
    private String cols = "abcdefgh";
    private String rows = "12345678";
    private String stones = "BQNRK";
    String plus = "+";


    public MoveTranslator(Chessboard cB){
        board = cB;
    }

    //Function that returns a chessMove for an algebraic expression
    public ChessMove interpreteNotation(String moveNotation, boolean white){
        if(moveNotation.contains(plus) ){
            moveNotation  = moveNotation.replace(plus, "");
        } else if(moveNotation.contains("#")){
           moveNotation  = moveNotation.replace("#", "");
        }
        CLIView tstview = new CLIView(board);
        tstview.drawBoard();
        Scanner sc = new Scanner(System.in);
        MoveReader reader = new MoveReader();
        System.out.println("->" + moveNotation + "<-");
        ChessMove move;
        String[] moveNotSegs;
        if(moveNotation.contains("x")){
            // capturing move
            System.out.println(moveNotation + " contains x");
            moveNotSegs = moveNotation.split("x", 2);
            if(stones.contains(moveNotSegs[0].substring(0,1))){
                //nonPawn capture
                int stonetypeCapturing = getStonetype(moveNotSegs[0].substring(0, 1), white);
                int targetcoordI = getIFromCompleteCoord(moveNotSegs[1]);
                int targetcoordJ = getJFromCompleteCoord(moveNotSegs[1]);
                int stonetypeCaptured = board.getStoneTypeIndex(targetcoordI, targetcoordJ);
                int[] captureStoneCoords = getMovableStoneOfType(targetcoordI, targetcoordJ, stonetypeCapturing);
                if(captureStoneCoords.length > 2){
                    captureStoneCoords = findCoordsOfCorrectStone(captureStoneCoords, moveNotSegs[0].substring(1, moveNotSegs[0].length()));
                }
                return new ChessMove(stonetypeCapturing, stonetypeCaptured, captureStoneCoords[0], captureStoneCoords[1], targetcoordI, targetcoordJ, white);

            } else {
                //pawn capture -> check promotion and en passant
                int targetcoordI = getIFromCompleteCoord(moveNotSegs[1]);
                int targetcoordJ = getJFromCompleteCoord(moveNotSegs[1]);
                boolean enPassant = (board.getStoneTypeIndex(targetcoordI, targetcoordJ) == 0);
                boolean promotion = moveNotation.contains("=");
                int stonetypeCapturing = (white ? 1 : 2);
                int[] captureStoneCoords = getMovableStoneOfType(targetcoordI, targetcoordJ, stonetypeCapturing);
                if(captureStoneCoords.length > 2){
                    captureStoneCoords = findCoordsOfCorrectStone(captureStoneCoords, moveNotSegs[0].substring(0, moveNotSegs[0].length()));
                }
                if(! promotion){
                    ChessMove retMove = new ChessMove(stonetypeCapturing, (white ? 2 : 1), captureStoneCoords[0], captureStoneCoords[1], targetcoordI, targetcoordJ, white);
                    if(enPassant) retMove.setEnPassantStrike();
                    return retMove;
                } else {
                    return new ChessMove(stonetypeCapturing, (white ? 2 : 1), captureStoneCoords[0], captureStoneCoords[1], targetcoordI, targetcoordJ, white, board.getStonetypeForIndex(getStonetype(moveNotation.substring(moveNotation.indexOf("=") + 1, moveNotation.length()), white)));
                }
            }
        } else {
            //non capturing move

            if(moveNotation.contains("=")){
                moveNotSegs = moveNotation.split("=", 2);
                int targetcoordI = getIFromCompleteCoord(moveNotSegs[0]);
                int targetcoordJ = getJFromCompleteCoord(moveNotSegs[0]);
                return new ChessMove((white ? 1 : 2), 0, targetcoordI , targetcoordJ  + (white ? -1 : 1), targetcoordI, targetcoordJ, white, board.getStonetypeForIndex(getStonetype(moveNotation.substring(moveNotation.indexOf("=") + 1, moveNotation.length()), white)));
            } else {

                if(moveNotation.contains("O-O")){
                    return new ChessMove((white ? 11 : 12), 0, 4, (white ? 0 : 7), (moveNotation.contains("O-O-O") ? 1 : 6), (white ? 0 : 7), white, true);
                } else {
                    if(stones.contains(moveNotation.substring(0,1))){
                        //trivial non pawn move
                        if(moveNotation.length() - 1 > 2){
                            int stonetypeCapturing = getStonetype(moveNotation.substring(0, 1), white);
                            int targetcoordI = getIFromCompleteCoord(moveNotation.substring(moveNotation.length() - 2, moveNotation.length()));
                            int targetcoordJ = getJFromCompleteCoord(moveNotation.substring(moveNotation.length() - 2, moveNotation.length()));
                            int[] captureStoneCoords = getMovableStoneOfType(targetcoordI, targetcoordJ, stonetypeCapturing);
                            if(captureStoneCoords.length > 2) captureStoneCoords = findCoordsOfCorrectStone(captureStoneCoords, moveNotation.substring(1, moveNotation.length() - 2));
                            return new ChessMove(stonetypeCapturing, 0, captureStoneCoords[0], captureStoneCoords[1], targetcoordI, targetcoordJ, white);
                        } else {
                            int stonetypeCapturing = getStonetype(moveNotation.substring(0, 1), white);
                            int targetcoordI = getIFromCompleteCoord(moveNotation.substring(1, moveNotation.length()));
                            int targetcoordJ = getJFromCompleteCoord(moveNotation.substring(1, moveNotation.length()));
                            int[] captureStoneCoords = getMovableStoneOfType(targetcoordI, targetcoordJ, stonetypeCapturing);
                            //System.out.println("stonetype: " + moveNotation.substring(0, 1) + " (" + stonetypeCapturing + ")");
                            if(captureStoneCoords.length > 2){
                                captureStoneCoords = findCoordsOfCorrectStone(captureStoneCoords, moveNotation.substring(1, moveNotation.length()));
                            }
                            return new ChessMove(stonetypeCapturing, 0, captureStoneCoords[0], captureStoneCoords[1], targetcoordI, targetcoordJ, white);
                        }
                    } else {
                        int targetcoordI = getIFromCompleteCoord(moveNotation);
                        int targetcoordJ = getJFromCompleteCoord(moveNotation);
                        int stonetype = (white ? 1 : 2);
                        int offSet = (white ? -1 : 1);
                        if(board.getStoneTypeIndex(targetcoordI, targetcoordJ + offSet) == 0){
                            //pawn skipped a field
                            ChessMove skipMove = new ChessMove(stonetype, 0, targetcoordI, targetcoordJ + 2 * offSet, targetcoordI, targetcoordJ, white);
                            skipMove.setEnPassantPawn();
                            return skipMove;
                        } else {
                            //supertrivial pawn move =)
                            return new ChessMove(stonetype, 0, targetcoordI, targetcoordJ + offSet, targetcoordI, targetcoordJ, white);
                        }
                    }
                }

            }
        }
    }

    private int getStonetype(String abbr, boolean white){
        switch (abbr){
            case "B":
                return (white ? 3 : 4);
            case "N":
                return (white ? 5 : 6);
            case "R":
                return (white ? 7 : 8);
            case "Q" :
                return (white ? 9 : 10);
            case "K":
                return (white ? 11 : 12);
            default:
                return -1;
        }
    }

    private int[] getMovableStoneOfType(int i, int j, int stonetype){
        //System.out.println("Stonetype: " + stonetype + " should move to field (" + i +", " + j +"). Looking for moves..");
        //CLIView tstview = new CLIView(board);
        //tstview.drawBoard();
        boolean white = (stonetype % 2 == 1);
        board.ignoreKingThread();
        int previousFieldValue = board.getStoneTypeIndex(i, j);
        board.setFieldValue(i, j, (white ? stonetype + 1 : stonetype -1));
        ArrayList<ChessMove> mvs = board.getPossibleMovesForField(i, j);
        ArrayList<ChessMove> relMoves = new ArrayList<>();
        for(ChessMove move : mvs){
            if(move.getTargetTypeIndex() == stonetype){
                relMoves.add(move);
            }
        }
        int[] retArray = new int[relMoves.size() * 2];
        for(int ii = 0; ii < relMoves.size(); ii++){
            retArray[2 * ii] = relMoves.get(ii).getIndexIA();
            retArray[2 * ii + 1] = relMoves.get(ii).getIndexJA();
        }
        board.setFieldValue(i, j, previousFieldValue);
        board.dontIgnoreKingThread();
        return retArray;
    }

    private int getIFromCompleteCoord(String coord){
        return cols.indexOf(coord.substring(0, 1));
    }

    private int getJFromCompleteCoord(String coord){
        return Integer.parseInt(coord.substring(1, 2)) - 1;
    }

    private int[] findCoordsOfCorrectStone(int[] Scoords, String coordNotation){
        int[] retArray = new int[2];
        if(coordNotation.length() == 2){
            for(int i = 0; i < Scoords.length / 2; i++){
                if(Scoords[i * 2] == getIFromCompleteCoord(coordNotation)){
                    if(Scoords[i * 2 + 1] == getJFromCompleteCoord(coordNotation)){
                        retArray[0] = Scoords[i * 2];
                        retArray[0] = Scoords[i * 2 + 1];
                        return  retArray;
                    }
                }
            }
        } else {
            if(cols.contains(coordNotation.substring(0, 1))){
                int i = cols.indexOf(coordNotation.substring(0, 1));
                for(int jj = 0; jj < Scoords.length / 2; jj++){
                    if(Scoords[jj * 2 ] == i){
                        retArray[0] = i;
                        retArray[1] = Scoords[jj * 2 + 1];
                        return retArray;
                    }
                }
            } else {
                int jc = rows.indexOf(coordNotation.substring(0, 1));
                for(int i = 0; i < Scoords.length / 2; i++){
                    if(Scoords[i * 2 + 1] == jc){
                        retArray[0] = Scoords[i * 2];
                        retArray[1] = jc;
                        return retArray;
                    }
                }
            }
        }
        System.out.println("Problem with method findCoordsOfCorrectStone(int[], String) in Class MoveReader. Random Array returned.");
        return new int[2];
    }




}
