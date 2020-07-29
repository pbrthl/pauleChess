package Model.BoardDataHandle;

import Model.BoardDataHandle.Helperclasses.ChessTuple;
import Model.BoardDataHandle.StoneHandeling.StoneHandler;
import Model.BoardDataHandle.StoneHandeling.StoneType;

import java.util.ArrayList;
import java.util.Stack;

public class BoardDataHandler {


    //stores information about the chessBoard constellation
    private int[][] chessBoard = new int[8][8];

    // sequence of chessmoves that were made
    private Stack<ChessMove> moveHistory = new Stack<>();

    private StoneHandler stoneHandler = new StoneHandler(this);

    private boolean whiteKingMovedYet = false;
    private boolean blackKingMovedYet = false;
    private boolean leftWhiteTowerMovedYet = false;
    private boolean rightWhiteTowerMovedYet = false;
    private boolean leftBlackTowerMovedYet = false;
    private boolean rightBlackTowerMovedYet = false;

    public boolean kingMovedYet(boolean white){
        return (white ? whiteKingMovedYet : blackKingMovedYet);
    }

    public boolean towerMovedYet(boolean white, boolean left){
        return (white ? (left ? leftWhiteTowerMovedYet : rightWhiteTowerMovedYet) : (left ? leftBlackTowerMovedYet : rightBlackTowerMovedYet));
    }

    public void setKingMoved(boolean white, boolean val){
        if(white) whiteKingMovedYet = val;
        else blackKingMovedYet = val;
    }

    public void setTowerMoved(boolean white, boolean left, boolean val){
        if(white){
            if(left) leftWhiteTowerMovedYet = val;
            else rightWhiteTowerMovedYet = val;
        } else {
            if(left) leftBlackTowerMovedYet = val;
            else rightBlackTowerMovedYet = val;
        }
    }

    public boolean isEmptyField(int i, int j){
        if(i < 0 || i > 7 || j < 0 || j > 7) return false;
        return (chessBoard[i][j] == 0);
    }

    public int getFieldValue(int i, int j){
        if(! isBoardField(i, j)) return -1;
        return chessBoard[i][j];
    }

    public boolean isWhiteField(int i, int j){
        return (chessBoard[i][j] % 2) == 1;
    }

    public void setFieldValue(int ico, int jco, int fval){
        if(! isBoardField(ico, jco)) return;
        chessBoard[ico][jco] = fval;
    }

    public StoneHandler getStoneHandler(){
        return stoneHandler;
    }

    public ArrayList<ChessMove> getMovesForField(int ic, int jc){
        return stoneHandler.getMovesForField(ic, jc);
    }


    public void initializeChessBoard(){
        //index 0_0 is A1

        for(int i = 0; i < 8; i++){

            //The pawns..

            chessBoard[i][1] = 1;
            chessBoard[i][6] = 2;
        }

        //Towers
        chessBoard[0][0] = 7;
        chessBoard[0][7] = 8;

        //horses
        chessBoard[1][0] = 5;
        chessBoard[1][7] = 6;


        // the bishops
        chessBoard[2][0] = 3;
        chessBoard[2][7] = 4;

        //Queens
        chessBoard[3][0] = 9;
        chessBoard[3][7] = 10;

        //Kings
        chessBoard[4][0] = 11;
        chessBoard[4][7] = 12;

        // bishops
        chessBoard[5][0] = 3;
        chessBoard[5][7] = 4;

        //horses
        chessBoard[6][0] = 5;
        chessBoard[6][7] = 6;

        //Towers
        chessBoard[7][0] = 7;
        chessBoard[7][7] = 8;

        //Empty fields
        for(int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                chessBoard[i][j] = 0;
            }
        }

        whiteKingMovedYet = false;
        blackKingMovedYet = false;
        leftWhiteTowerMovedYet = false;
        rightWhiteTowerMovedYet = false;
        leftBlackTowerMovedYet = false;
        rightBlackTowerMovedYet = false;

        moveHistory = new Stack<>();

    }

    public void moveStone(ChessMove move){
        stoneHandler.moveStone(move);
    }

    public void undoMove(ChessMove move){
        stoneHandler.undoChessMove(move);
    }

    public boolean isBoardField(int ic, int jc){
        return 0 <= ic && ic < 8 && 0 <= jc && jc < 8;
    }

    public boolean isPawn(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 1 || chessBoard[ic][jc] == 2){
                return true;
            }
        }
        return false;
    }

    public boolean isBishop(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 3 || chessBoard[ic][jc] == 4){
                return true;
            }
        }
        return false;
    }

    public StoneType getStonetype(int i, int j){
        return stoneHandler.getStoneType(i, j);
    }


    public boolean isHorse(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 5 || chessBoard[ic][jc] == 6){
                return true;
            }
        }
        return false;
    }


    public boolean isTower(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 7 || chessBoard[ic][jc] == 8){
                return true;
            }
        }
        return false;
    }


    public boolean isQueen(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 9 || chessBoard[ic][jc] == 10){
                return true;
            }
        }
        return false;
    }

    public boolean isKing(int ic, int jc){
        if(isBoardField(ic, jc)){
            if(chessBoard[ic][jc] == 11 || chessBoard[ic][jc] == 12){
                return true;
            }
        }
        return false;
    }

    public boolean checkKingThreateningMove(ChessMove move){
        moveStone(move);
        ChessTuple kingsPosition = (move.isWhite() ? stoneHandler.getWhiteKingPosition() : stoneHandler.getBlackKingPosition());
        boolean isThreateningKing =  stoneHandler.fieldIsThreatened(kingsPosition.getI(), kingsPosition.getJ());
        undoMove(move);
        return isThreateningKing;
    }

    public ArrayList<ChessMove> getPossiblePlayerMoves(boolean playerWhite){
        ArrayList<ChessMove> pMoves = new ArrayList<>();
        for(int ii = 0; ii < 8; ii++){
            for(int ji = 0; ji < 8; ji++){

                if(chessBoard[ii][ji] != 0 && ! (isWhiteField(ii, ji) ^ playerWhite)){
                    for(ChessMove move : getMovesForField(ii, ji)){
                        pMoves.add(move);
                    }

                }
            }
        }
        return pMoves;
    }


    public BoardDataHandler(){
        initializeChessBoard();
    }


    public Stack<ChessMove> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(Stack<ChessMove> moveHistory) {
        this.moveHistory = moveHistory;
    }
}
