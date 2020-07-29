package Model.BoardDataHandle;

import Model.BoardDataHandle.StoneHandeling.StoneType;
import Model.BoardDataHandle.StoneHandeling.Stonetypes.NoStone;
import Model.Chessboard;

public class ChessMove {
    private int movedTypeIndex = 0;
    private int indexIB = 0;
    private int indexJB = 0;
    private int indexIA = 0;
    private int indexJA = 0;
    private boolean isWhite = true;
    private int targetTypeIndex;
    private boolean rojade = false;
    private boolean typeChange = false;
    private StoneType changeType = new NoStone(new BoardDataHandler(), false);
    private boolean firstMoveKing = false;
    private boolean firstMoveLeftTower = false;
    private boolean firstMoveRightTower = false;
    private boolean enPassantPawn = false;
    private boolean enPassantStrike = false;

    public ChessMove(int sType, int tType, int iIndexB, int jIndexB, int iIndexA, int jIndexA, boolean wStone){
        movedTypeIndex = sType;
        targetTypeIndex = tType;
        indexIB = iIndexB;
        indexJB = jIndexB;
        indexIA = iIndexA;
        indexJA = jIndexA;
        isWhite = wStone;
    }

    public ChessMove setEnPassantPawn(){
        enPassantPawn = true;
        return this;
    }

    public ChessMove setEnPassantStrike(){
        enPassantStrike = true;
        return this;
    }


    public ChessMove(int sType, int tType, int iIndexB, int jIndexB, int iIndexA, int jIndexA, boolean wStone, boolean roj){
        movedTypeIndex = sType;
        targetTypeIndex = tType;
        indexIB = iIndexB;
        indexJB = jIndexB;
        indexIA = iIndexA;
        indexJA = jIndexA;
        rojade = roj;
        isWhite = wStone;
    }


    public ChessMove(int sType, int tType, int iIndexB, int jIndexB, int iIndexA, int jIndexA, boolean wStone, StoneType chng){
        movedTypeIndex = sType;
        targetTypeIndex = tType;
        indexIB = iIndexB;
        indexJB = jIndexB;
        indexIA = iIndexA;
        indexJA = jIndexA;
        changeType = chng;
        isWhite = wStone;
    }


    public int getMovedTypeIndex() {
        return movedTypeIndex;
    }

    public void setMovedTypeIndex(int movedTypeIndex) {
        this.movedTypeIndex = movedTypeIndex;
    }


    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public int getIndexIB() {
        return indexIB;
    }

    public void setIndexIB(int indexIB) {
        this.indexIB = indexIB;
    }

    public int getIndexJB() {
        return indexJB;
    }

    public ChessMove setFirstTowerMove(boolean left){
        if(left) firstMoveLeftTower = true;
        else firstMoveRightTower = true;
        return this;
    }

    public void setIndexJB(int indexJB) {
        this.indexJB = indexJB;
    }

    public int getIndexIA() {
        return indexIA;
    }

    public void setIndexIA(int indexIA) {
        this.indexIA = indexIA;
    }

    public int getIndexJA() {
        return indexJA;
    }

    public void setIndexJA(int indexJA) {
        this.indexJA = indexJA;
    }

    public StoneType getChangeType() {
        return changeType;
    }

    public int getTargetTypeIndex() {
        return targetTypeIndex;
    }

    public boolean isRojade() {
        return rojade;
    }

    public boolean isTypeChange() {
        return typeChange;
    }

    public boolean isFirstMoveKing() {
        return firstMoveKing;
    }

    public ChessMove setFirstMoveKing(boolean firstMoveKing) {
        this.firstMoveKing = firstMoveKing;
        return this;
    }

    public boolean isFirstMoveLeftTower() {
        return firstMoveLeftTower;
    }

    public void setFirstMoveLeftTower(boolean firstMoveLeftTower) {
        this.firstMoveLeftTower = firstMoveLeftTower;
    }

    public boolean isFirstMoveRightTower() {
        return firstMoveRightTower;
    }

    public void setFirstMoveRightTower(boolean firstMoveRightTower) {
        this.firstMoveRightTower = firstMoveRightTower;
    }

    public boolean isEnPassantPawn() {
        return enPassantPawn;
    }

    public void setEnPassantPawn(boolean enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public boolean isEnPassantStrike() {
        return enPassantStrike;
    }

    public void setEnPassantStrike(boolean enPassantStrike) {
        this.enPassantStrike = enPassantStrike;
    }
}
