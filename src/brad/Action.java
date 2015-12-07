package brad;


/**
 * Created by Brad on 11/26/2015.
 *
 * Represents an action or move on a tic tac toe game
 */
public class Action {

    private int row;
    private int col;
    private char symbol;

    public int row() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int column() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setSymbol(char c) {
        symbol = c;
    }

    public char symbol() {
        return symbol;
    }
}
