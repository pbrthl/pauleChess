package chess_ai;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;
import chess_ai.NeuralNetwork.NeuralNetwork;
import chess_ai.game_config_converter.GameConfigurationConverter;
import chess_ai.move_reader.ChessPartieData;
import chess_ai.move_reader.MoveReader;

public class ChessAI {

    NeuralNetwork nnet;

    public ChessAI(){
        NeuralNetwork nnet = new NeuralNetwork(71, 300, 1, 3);
    }

    public void trainWithChessGame(boolean whitePlayer, ChessMove[] gameMoves){
        Chessboard board = new Chessboard();
        boolean whiteMove = true;
        double[] trainVecGoodMove = new double[71];
        double[] trainVecBadMove = new double[71];
        double[] trainVecCombined = new double[141];
        double[] trainVecYOne = new double[1];
        double[] trainVecYTwo = new double[1];
        trainVecYOne[0] = 1;
        trainVecYTwo[0] = 0;
        for(ChessMove move : gameMoves){
            if(! (whiteMove ^ whitePlayer)){
                board.makeMove(move);
                trainVecGoodMove = GameConfigurationConverter.getAIRepresentation(board, whitePlayer);
                board.undoMove(move);
                for(ChessMove alternativeMove : board.getPossibleMovesForPlayer(whitePlayer)){
                    if( move.getIndexIB() != alternativeMove.getIndexIB() || move.getIndexJB() != alternativeMove.getIndexJB() || move.getIndexIA() != alternativeMove.getIndexIA() || move.getIndexJA() != alternativeMove.getIndexJA()){
                        board.makeMove(alternativeMove);
                        trainVecBadMove = GameConfigurationConverter.getAIRepresentation(board, whitePlayer);
                        board.undoMove(alternativeMove);
                        trainVecCombined = GameConfigurationConverter.combineAIRepresentation(trainVecGoodMove, trainVecBadMove);
                        nnet.train(trainVecCombined, trainVecYOne);
                        trainVecCombined = GameConfigurationConverter.combineAIRepresentation(trainVecBadMove, trainVecGoodMove);
                        nnet.train(trainVecCombined, trainVecYTwo);
                    }

                }

            }
            board.makeMove(move);
            whiteMove = ! whiteMove;
        }
    }

    public void trainWithFile(String filename){
        //Chessboard board = new Chessboard();
        MoveReader reader = new MoveReader();
        for(ChessPartieData partie : reader.readChessPartiesFromFile(filename)){
            trainWithChessGame(partie.isWhiteWon(), (ChessMove[]) partie.getGameMoves().toArray());
        }

    }

    public void saveNeuralNetwork(){

    }

    public void readNeuralNetwork(){

    }

}
