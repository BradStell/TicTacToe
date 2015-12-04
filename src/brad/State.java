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
    public boolean done = false;
    private int size;


    public State(char[][] board, char whosTurn, int size) {

        this.whosTurn = whosTurn;
        numChildren = 0;
        action = new Action();
        children = new ArrayList<State>();
        this.size = size;
        copyBoard(board);
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

    public void addChild(State child) {
        children.add(child);
        numChildren++;
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

    public char[][] getBoardCopy() {

        char[][] stateCopy = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                stateCopy[i][j] = board[i][j];
            }
        }

        return stateCopy;
    }

    public char[][] getBoard() {
        return board;
    }
}
