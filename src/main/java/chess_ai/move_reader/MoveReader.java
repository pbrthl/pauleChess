package chess_ai.move_reader;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveReader {

    private Chessboard board;
    MoveTranslator translator;

    public MoveReader(){
        board = new Chessboard();
        translator = new MoveTranslator(board);
    }

    public ArrayList<ChessMove> stringToMoves(String moveString){
        ArrayList<ChessMove> moves = new ArrayList<>();
        int indexNum;
        String halfMove;
        ChessMove mv;
        int movenr = 1;
        while(moveString.contains(".")){
            //System.out.println("\n Zug nummer " + movenr + "\n");
            indexNum = moveString.indexOf(".");
            moveString = moveString.substring(indexNum + 2, moveString.length());
            indexNum = moveString.indexOf(" ");
            halfMove = moveString.substring(0, indexNum);
            if(halfMove.contains("{")) break;
            mv = translator.interpreteNotation(halfMove, true);
            board.makeMove(mv);
            moves.add(mv);
            moveString = moveString.substring(indexNum + 1, moveString.length());
            indexNum = moveString.indexOf(" ");
            halfMove = moveString.substring(0, indexNum);
            if(halfMove.contains("{")) break;
            mv = translator.interpreteNotation(halfMove, false);
            board.makeMove(mv);
            moves.add(mv);
            movenr++;
        }
        board.reinitialize();
        return moves;
    }

    public ArrayList<ChessPartieData> readChessPartiesFromFile(String filepath){
        ArrayList<ChessPartieData> chessparties = new ArrayList<ChessPartieData>();
        try {
            File chesspartiedatafile = new File(filepath);
            System.out.println("New File..");
            Scanner myReader = new Scanner(chesspartiedatafile);
            System.out.println("New Reader..");
            boolean whiteWon = false;
            String line;
            int gamenum = 1;
            while (myReader.hasNextLine()) {
                line = myReader.nextLine();
                if(line.contains("[Result")){
                    System.out.println("\n\n\n\n Game " + gamenum + "\n\n\n\n");
                    gamenum++;
                    whiteWon = line.contains("1-0");
                    while(myReader.hasNextLine()){
                        line = myReader.nextLine();
                        if(line.contains("1. ")){
                            chessparties.add(new ChessPartieData(stringToMoves(line), whiteWon));
                            break;
                        }
                    }

                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File could not be found.");
            e.printStackTrace();
        }


        return new ArrayList<ChessPartieData>();
    }


}
