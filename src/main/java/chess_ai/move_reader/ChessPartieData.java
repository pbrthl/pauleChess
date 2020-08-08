package chess_ai.move_reader;

import Model.BoardDataHandle.ChessMove;

import java.util.ArrayList;

public class ChessPartieData {

    private boolean whiteWon;
    private ArrayList<ChessMove> gameMoves;

    public ChessPartieData(ArrayList<ChessMove> moves, boolean whiteIsWinner){
        gameMoves = moves;
        whiteWon = whiteIsWinner;
    }

    public boolean isWhiteWon() {
        return whiteWon;
    }

    public ArrayList<ChessMove> getGameMoves() {
        return gameMoves;
    }
}
