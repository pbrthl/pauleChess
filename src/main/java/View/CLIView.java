package View;

import Model.BoardDataHandle.ChessMove;
import Model.Chessboard;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends ChessView {

    private Chessboard board;

    Scanner sc = new Scanner(System.in);

    public CLIView(Chessboard iniBoard){
        board = iniBoard;
    }

    public void drawBoard(){

            String boardline = "\n\n\n\n";
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
            for(int j = 7; j >= 0; j--){
                boardline = (j + 1) + ":";
                for (int i = 0; i < 8; i++){
                    boardline = boardline + " " + drawField(i, j);
                    if(i == 7 ){
                        System.out.println(boardline);
                        System.out.println(" ");
                        boardline = "";

                    }
                }
            }
            System.out.println("------A-----------B-----------C-----------D-----------E-----------F-----------G-----------H------ ");


    }

    public ChessMove askForMove(boolean white){

        System.out.println("\n TURN OF " + (white ? "WHITE" : "BLACK") + " PLAYER. \n");

        String eingabe;
        int iIndexStone;
        int jIndexStone;
        String fieldString = "ABCDEFGH";
        String fieldStringTwo = "abcdefgh";
        boolean inputError = false;
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessMove move = new ChessMove(0, 0, 0, 0, 0, 0, false);

        while(true) {
            if(inputError){
                System.out.println("Invalid inpur. Try again.");
            }
            System.out.println("Which stone do you want to move?");
            System.out.println("Type the field name (A1, A2, .. A8, B1, ...B8, ... ... H8");
            eingabe = sc.next();
            if (fieldString.contains(eingabe.substring(0, 1)) || fieldStringTwo.contains(eingabe.substring(0, 1))) {
                iIndexStone = Math.max(fieldString.indexOf(eingabe.substring(0, 1)), fieldStringTwo.indexOf(eingabe.substring(0, 1)));
            } else {
                System.out.println("That is no column!");
                inputError = true;
                continue;
            }
            if("12345678".contains(eingabe.substring(1, 2))) {
                if (Integer.parseInt(eingabe.substring(1, 2)) < 9 && Integer.parseInt(eingabe.substring(1, 2)) > 0) {
                    jIndexStone = Integer.parseInt(eingabe.substring(1, 2)) - 1;
                } else {
                    System.out.println("No possible number for row");
                    inputError = true;
                    continue;
                }
            } else {
                System.out.println("No valid row-index.");
                inputError = true;
                continue;
            }

            if(! board.isEmptyField(iIndexStone, jIndexStone)){
                if(! (board.stoneIsWhite(iIndexStone, jIndexStone) ^ white)){
                    possibleMoves = board.getPossibleMovesForField(iIndexStone, jIndexStone);
                } else {
                    System.out.println("Thats not your stone, Dude!");
                    inputError = true;
                    continue;
                }
            } else {
                System.out.println("That is an empty Field.");
                inputError = true;
                continue;
            }
            if(possibleMoves.size() == 0){
                System.out.println("No moves possible with that stone.");
                inputError = true;
                continue;
            }
            break;
        }

        inputError = false;

        while(true){
            if(inputError){
                System.out.println("Invalid input. Try again.");
            }

            System.out.println("Where do you want to move the stone? ");


            eingabe = sc.next();
            if (fieldString.contains(eingabe.substring(0, 1)) || fieldStringTwo.contains(eingabe.substring(0, 1))) {
                iIndexStone = Math.max(fieldString.indexOf(eingabe.substring(0, 1)), fieldStringTwo.indexOf(eingabe.substring(0, 1)));
            } else {
                System.out.println("That is no column!");
                inputError = true;
                continue;
            }
            if("12345678".contains(eingabe.substring(1, 2))) {
                if (Integer.parseInt(eingabe.substring(1, 2)) < 9 && Integer.parseInt(eingabe.substring(1, 2)) > 0) {
                    jIndexStone = Integer.parseInt(eingabe.substring(1, 2)) - 1;
                } else {
                    System.out.println("No possible number for row");
                    inputError = true;
                    continue;
                }
            } else {
                System.out.println("No possible number for row");
                inputError = true;
                continue;
            }

            boolean moveFound = false;

            for(ChessMove pMove : possibleMoves){
                if(pMove.getIndexIA() == iIndexStone){
                    if(pMove.getIndexJA() == jIndexStone){
                        moveFound = true;
                        move = pMove;
                        break;
                    }
                }
            }

            if(! moveFound){
                inputError = true;
                continue;
            }

            break;
        }

        return move;

    }

    private String drawField(int i, int j){
        switch (board.getStoneTypeIndex(i, j)) {
            case 0:
                return (i % 2 == j % 2 ?  "|||||||||||" : "|________|");
            case 1:
                return "PawnWhite  ";
            case 2:
                return "PawnBlack  ";
            case 3:
                return "BishopWhite";
            case 4:
                return "BishopBlack";
            case 5:
                return "HorseWhite ";
            case 6:
                return "HorseBlack ";
            case 7:
                return "TowerWhite ";
            case 8:
                return "TowerBlack";
            case 9:
                return "QueenWhite ";
            case 10:
                return "QueenBlack ";
            case 11:
                return " KingWhite ";
            case 12:
                return "KingBlack ";
            default:
                return "Error - Field-value = " + board.getStoneType(i, j).getStoneTypeIndex();


        }
    }


}
