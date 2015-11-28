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
    private int numChildren;
    private char whosTurn;
    private Action action;
    private int utilityValue;
    private State parent;
    private ArrayList<State> children;


    public State(char[][] board, char whosTurn) {
        copyBoard(board);
        this.whosTurn = whosTurn;
        numChildren = countChildrenStates();
        action = new Action();
        children = new ArrayList<State>();
    }

    private void copyBoard(char[][] board) {
        this.board = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public void addChild(State child) {
        children.add(child);
    }

    public int getNumChildArray() {
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

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public char getWhosTurn() {
        return whosTurn;
    }

    public void setWhosTurn(char whosTurn) {
        this.whosTurn = whosTurn;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    private int countChildrenStates() {

        int children = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 'e') {
                    children++;
                }
            }
        }

        return children;
    }

    public char[][] getBoardCopy() {

        char[][] stateCopy = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stateCopy[i][j] = board[i][j];
            }
        }

        return stateCopy;
    }

    public char[][] getBoard() {
        return board;
    }
}
