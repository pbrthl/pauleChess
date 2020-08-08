package chess_ai.game_config_converter;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;

public class GameConfigurationConverter {

    public static double[] getAIRepresentation(Chessboard board, boolean white){
        double[] representationArray = new double[71];
        for(int i = 0; i < 71; i++){
            representationArray[i] = -1;
        }
        double stoneValueWhite = 0;
        double stoneValueBlack = 0;
        int stoneTypeIndex = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                stoneTypeIndex = board.getStoneTypeIndex(i, j);
                representationArray[(white ? i * 8 + j : (7 - i) * 8 + (7 - j))] = (stoneTypeIndex == 0 ? 0 : (white ? (stoneTypeIndex % 2 == 1 ? stoneTypeIndex : stoneTypeIndex * - 1 ) : (stoneTypeIndex % 2 == 1 ? (stoneTypeIndex + 1) * - 1 : stoneTypeIndex - 1)));
                representationArray[(white ? i * 8 + j : (7 - i) * 8 + (7 - j))] = representationArray[(white ? i * 8 + j : (7 - i) * 8 + (7 - j))] * 10;
                if(stoneTypeIndex == 0){
                    break;
                } else {
                    if(stoneTypeIndex == 1 || stoneTypeIndex == 2){
                        if(stoneTypeIndex == 1){
                            stoneValueWhite += 100;
                        } else {
                            stoneValueBlack += 100;
                        }
                    } else {
                        if(stoneTypeIndex == 3 || stoneTypeIndex == 4){
                            if(stoneTypeIndex == 3){
                                stoneValueWhite += 250;
                            } else {
                                stoneValueBlack += 250;
                            }
                        } else {
                            if(stoneTypeIndex == 5 || stoneTypeIndex == 6){
                                if(stoneTypeIndex == 5){
                                    stoneValueWhite += 350;
                                } else {
                                    stoneValueBlack += 350;
                                }
                            } else {
                                if(stoneTypeIndex == 7 || stoneTypeIndex == 8){
                                    if(stoneTypeIndex == 7){
                                        stoneValueWhite += 500;
                                    } else {
                                        stoneValueBlack += 500;
                                    }
                                } else {
                                    if(stoneTypeIndex == 9 || stoneTypeIndex == 10){
                                        if(stoneTypeIndex == 9){
                                            stoneValueWhite += 900;
                                        } else {
                                            stoneValueBlack += 900;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        representationArray[64] = (white ? stoneValueWhite : stoneValueBlack);
        representationArray[65] = (white ? stoneValueBlack : stoneValueWhite);
        representationArray[66] = board.getNumberOfMove();
        representationArray[67] = ((white ? board.whiteKingCheck() : board.blackKingCheck()) ? 1 : 0);
        representationArray[68] = ((white ?  board.blackKingCheck()  : board.whiteKingCheck()) ? 1 : 0);
        representationArray[69] = (board.getPossibleMovesForPlayer(true).size() == 0 ||  board.getPossibleMovesForPlayer(false).size() == 0? 1 : 0);
        representationArray[70] = (white ? 1 : 0);



        return representationArray;
    }

    public static double [] combineAIRepresentation(double[] repOne, double[] repTwo){
        double[] retRepresentation = new double[141];
        for(int i = 0; i < 70; i++){
            retRepresentation[i] = repOne[i];
        }

        for(int i = 70; i < 141; i++){
            retRepresentation[i] = repTwo[i - 70];
        }
        return retRepresentation;
    }

    public static double[] getFirstRepresentationFragment(double[] rep){
        double[] representation = new double[71];
        for(int i = 0; i < 70; i++){
            representation[i] = rep[i];
        }
        representation[70] = rep[140];
        return  representation;
    }

    public static  double[] getSecondRepresentationFragment(double[] rep){
        double[] repre = new double[71];
        for(int i = 0; i < 71; i++){
            repre[i] = rep[i + 71];
        }
        return repre;
    }

}
