package brad;

import java.util.ArrayList;

/**
 * Created by Brad on 11/26/2015.
 *
 * Represents a board of the game, ie. where the x's and o's are as well
 * as how many children this board can produce.
 *
 */
public class State {

    private char[][] board;
    private int numChildren = 0;
    private char whosTurn;
    private Action action;
    private int utilityValue;
    private State parent;
    private ArrayList<State> children;
    public boolean done = false;
    private int size;
    private int childCount = 0;


    public State(char[][] board, char whosTurn, int size) {

        this.whosTurn = whosTurn;
        action = new Action();
        children = new ArrayList<State>();
        this.size = size;
        copyBoard(board);
        numChildren = calcNumChildren();
    }

    public int calcNumChildren() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 'e') {
                    count++;
                }
            }
        }
        return count;
    }

    public State getNextChild() {

        int counter = 0;
        boolean madeChild = false;
        State c = null;
        Action action = null;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 'e') {
                    if (counter == childCount) {

                        //make child ####################################################
                        action = new Action();

                        if (this.getWhosTurn() == MiniMax.MIN) {

                            board[row][col] = MiniMax.MIN;
                            action.setImage(MiniMax.MIN);
                            c = new State(board, MiniMax.MAX, size);

                        } else {

                            board[row][col] = MiniMax.MAX;
                            action.setImage(MiniMax.MAX);
                            c = new State(board, MiniMax.MIN, size);
                        }

                        action.setRow(row);
                        action.setCol(col);
                        c.setAction(action);
                        c.setParent(this);
                        children.add(c);
                        board[row][col] = 'e';
                        //make child ####################################################

                        childCount++;
                        madeChild = true;
                        break;
                    }
                    counter++;
                }
            }
            if (madeChild) {
                break;
            }
        }

        return c;
    }

    public void clearChildArrayList() {
        children.clear();
    }

    private void copyBoard(char[][] board) {
        this.board = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public State getParent() {
        return parent;
    }

    public int getNumChildArraySize() {
        return children.size();
    }

    public State getChild(int i) {
        return children.get(i);
    }

    public int getUtilityValue() {
        return utilityValue;
    }

    public void setUtilityValue(int utilityValue) {
        this.utilityValue = utilityValue;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public char getWhosTurn() {
        return whosTurn;
    }

    public char[][] getBoard() {
        return board;
    }
}
