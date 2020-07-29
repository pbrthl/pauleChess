package Controller.GameControl;

import Model.Chessboard;
import View.CLIView;

public class TestGameController {

    private Chessboard board;
    private CLIView view;
    private boolean whiteTurn = true;

    public void start(){
        while(true){
            view.drawBoard();
            board.makeMove(view.askForMove(whiteTurn));
            whiteTurn = ! whiteTurn;
        }

    }

    public TestGameController(Chessboard cBoard, CLIView cView){
        board = cBoard;
        view = cView;
    };

}
