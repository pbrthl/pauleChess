package Model;

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
    private StoneType changeType;

    public ChessMove(int sType, int tType, int iIndexB, int jIndexB, int iIndexA, int jIndexA, boolean wStone){
        movedTypeIndex = sType;
        targetTypeIndex = tType;
        indexIB = iIndexB;
        indexJB = jIndexB;
        indexIA = iIndexA;
        indexJA = jIndexA;
        isWhite = wStone;
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
}
