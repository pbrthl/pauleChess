package View;

import Model.BoardDataHandle.BoardDataHandler;
import Model.BoardDataHandle.ChessMove;
import Model.BoardDataHandle.StoneHandeling.Stonetypes.NoStone;
import Model.Chessboard;

public abstract class ChessView {

    private Chessboard board;

    public void drawBoard(){}

    public ChessMove askForMove(boolean white){
        return  new ChessMove(0,0,0,0,0,0, true);
    }

}
