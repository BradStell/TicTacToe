package brad;

import javafx.scene.layout.GridPane;

/**
 * Created by Brad on 11/7/2015.
 */
public class MiniMax {

    static final char MAX = 'x';
    static final char MIN = 'o';

    public static void Start(GridPane gameBoard, int size) {

        char[][] board = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
            }
        }

        // Call minimax with the state
        State state = new State(board, MIN);
        MiniMax_AlphaBetaPruning(state);
    }

    private static void MiniMax_AlphaBetaPruning(State state) {

        int sigma = MinValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static int MaxValue(State state, int alpha, int beta) {

        if (terminalTest(state)) {
            return utility(state);
        }

        int sigma = Integer.MIN_VALUE;

        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = getNextAvailableChild(state);

            sigma = Max(sigma, MinValue(child, alpha, beta));

            if (sigma >= beta) {
                return sigma;
            }

            alpha = Max(alpha, sigma);
        }

        return sigma;
    }

    private static int MinValue(State state, int alpha, int beta) {

        if (terminalTest(state)) {
            return utility(state);
        }

        int sigma = Integer.MAX_VALUE;

        // Generate all children
        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = getNextAvailableChild(state);

            sigma = Min(sigma, MaxValue(child, alpha, beta));

            if (sigma <= alpha) {
                return sigma;
            }

            beta = Min(beta, sigma);
        }

        return sigma;
    }

    private static boolean terminalTest(State state) {

        boolean terminalState = true;
        char[][] stateBoard = state.getBoard();


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (stateBoard[row][col] == 'e') {
                    terminalState = false;
                    break;
                }
            }
            if (!terminalState) {
                break;
            }
        }

        return terminalState;
    }

    private static int utility(State state) {

        int utility = 0;

        if (state.getWhosTurn() == MiniMax.MIN) {
            utility = calcMinUtility();
        } else if (state.getWhosTurn() == MiniMax.MAX) {
            utility = calcMaxUtility();
        }

        return utility;
    }

    private static int calcMinUtility() {

    }

    private static int calcMaxUtility() {

    }

    private static int Max(int sigma, int minimaxValue) {

        if (sigma > minimaxValue) {
            return sigma;
        } else {
            return minimaxValue;
        }
    }

    private static int Min(int sigma, int minimaxValue) {

        if (sigma < minimaxValue) {
            return sigma;
        } else {
            return minimaxValue;
        }
    }

    private static State getNextAvailableChild(State state) {

        char[][] copyState = state.getBoard();
        State child = null;
        boolean createdChild = false;
        Action action = new Action();

        // Generate next available child
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                // Find the empty cell
                if (copyState[row][col] == 'e') {

                    // If its max's turn
                    if (state.getWhosTurn() == MiniMax.MAX) {

                        copyState[row][col] = 'x';
                        child = new State(copyState, MiniMax.MIN);
                        child.setParent(state);
                        createdChild = true;
                        break;
                    }

                    // If its min's turn
                    else if (state.getWhosTurn() == MiniMax.MIN) {

                        copyState[row][col] = 'o';
                        child = new State(copyState, MiniMax.MAX);
                        child.setParent(state);
                        createdChild = true;
                        break;
                    }
                    action.setRow(row);
                    action.setCol(col);
                    child.setAction(action);
                }
            }

            if (createdChild) {
                break;
            }
        }

        state.addChild(child);

        return child;
    }

}
