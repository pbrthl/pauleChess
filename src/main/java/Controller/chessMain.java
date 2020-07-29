package Controller;

import Controller.GameControl.TestGameController;
import Model.Chessboard;
import View.CLIView;

import java.io.IOException;

public class chessMain {

    public static void main(String[] args) throws IOException {
        Chessboard board = new Chessboard();
        CLIView view = new CLIView(board);
        TestGameController controller = new TestGameController(board, view);

        controller.start();
    }



}
