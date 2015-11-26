package brad;

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
    private char who;
    private Action[] actions;

    public State(char[][] board, char who) {
        this.board = board;
        this.who = who;
        numChildren = countChildrenStates();
        Action.generateActions(this);
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
    }

    public char getWho() {
        return who;
    }

    public void setWho(char who) {
        this.who = who;
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

    public char[][] getBoard() {

        char[][] stateCopy = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stateCopy[i][j] = board[i][j];
            }
        }

        return stateCopy;
    }
}
