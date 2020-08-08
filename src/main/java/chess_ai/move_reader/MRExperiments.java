package chess_ai.move_reader;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;
import View.CLIView;
import chess_ai.ChessAI;

import java.util.ArrayList;
import java.util.Scanner;

public class MRExperiments {


    public MRExperiments(){

    }

    public static void showGameSequence(String sequence){
        MoveReader reader = new MoveReader();
        ArrayList<ChessMove> moves = reader.stringToMoves(sequence);
        Chessboard board = new Chessboard();
        CLIView view = new CLIView(board);
        view.drawBoard();
        for (ChessMove move : moves){
            board.makeMove(move);
            view.drawBoard();
        }
    }

    public static void learnFromFile(){
        ChessAI ai = new ChessAI();
        ai.trainWithFile("C:\\Users\\User\\pauleChess\\src\\main\\resources\\chessgames.txt");
        System.out.println("Learned sucessfully.");
    }

}
