package brad;


/**
 * Created by Brad on 11/26/2015.
 *
 * Represents an action or move on a tic tac toe game
 */
public class Action {

    private int row;
    private int col;
    private char image;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setImage(char c) {
        image = c;
    }

    public char getImage() {
        return image;
    }
}
