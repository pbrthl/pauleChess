package Model.Helperclasses;

public class ChessTuple {
    private int i;
    private int j;

    public ChessTuple(int ic, int jc){
        i = ic;
        j = jc;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setTuple(int ic, int jc){
        i = ic;
        j = jc;
    }
}
