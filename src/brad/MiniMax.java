package brad;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * Created by Brad on 11/7/2015.
 *
 * MiniMax algorithm with alpha beta pruning implementation
 */
public class MiniMax {

    static final char MAX = 'x';
    static final char MIN = 'o';
    static Image PLAYER;
    static Image CPU;

    /**
     * Returns true if the game is over - false if it is not over
     * @param gameBoard - The tic tac toe grid
     * @param size - the number of rows and columns in the grid
     * @param player - the players image (x or o)
     * @param cpu - the cpu's image (x or o)
     * @return - A winner object that contains information about the game
     */
    public static Winner Start(GridPane gameBoard, int size, Image player, Image cpu) {

        PLAYER = player;
        CPU = cpu;
        char[][] board = new char[size][size];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
            }
        }

        // Call MiniMax with the incoming game state
        State state = new State(board, MIN, size);
        Action action = MiniMax_AlphaBetaPruning(state, 0, size);

        System.out.print("\n\n**** Done with algo\n\n");

        // Handle the action returned from the MiniMax algorithm
        int row = action.getRow();
        int col = action.getCol();
        GridSquare gridSquare = (GridSquare) gameBoard.lookup("#" + row + col);

        if (action.getImage() == MAX) {
            gridSquare.setContains(MAX);

        } else if (action.getImage() == MIN) {
            gridSquare.setContains(MIN);

        }

        Winner win = Game.IsOver(gameBoard, size, 0);
        win.gridSquare = gridSquare;
        win.action = action;

        return win;
    }

    private static Action MiniMax_AlphaBetaPruning(State state, int depth, int size) {

        Action action = null;

        // Call MinValue with the incoming state
        int sigma = MinValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, size);

        // Find the state's child who's utility value matches the sigma value
        // returned from MinValue
        for (int i = 0; i < state.getNumChildArraySize(); i++) {
            if (state.getChild(i).getUtilityValue() == sigma) {
                action = state.getChild(i).getAction();
                break;
            }
        }

        // If there is not a child who's utility value matches sigma
        // then return the child's action that has the smallest utility value
        if (action == null) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < state.getNumChildArraySize(); i++) {
                if (state.getChild(i).getUtilityValue() < min) {
                    min = state.getChild(i).getUtilityValue();
                    action = state.getChild(i).getAction();
                }
            }
        }

        return action;
    }

    private static int MaxValue(State state, int alpha, int beta, int depth, int size) {

        Winner winner = Game.IsOver(state, size, depth);

        // If the state is a finished state return its utility value
        if (winner.getIsOver()) {
            State parent = state.getParent();
            parent.done = true;
            return winner.getUtilityValue();
        }

        state.setUtilityValue(Integer.MIN_VALUE);

        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = state.getNextChild();

            // Call MinValue with each child
            state.setUtilityValue(Max(state.getUtilityValue(), MinValue(child, alpha, beta, depth + 1, size)));

            // If the states child has reached a terminal state
            // no need to check other children
            if (state.done) {
                if (depth != 0) {
                    state.clearChildArrayList();
                }
                break;
            }

            // Alpha Beta pruning section
            // Stop searching children if the utility value returned from
            // MinValue is larger than the beta value
            if (state.getUtilityValue() >= beta) {
                return state.getUtilityValue();
            }

            // set alpha to the new alpha value
            alpha = Max(alpha, state.getUtilityValue());
        }

        if (depth != 0) {
            state.clearChildArrayList();
        }

        // Return the utility value
        return state.getUtilityValue();
    }

    private static int MinValue(State state, int alpha, int beta, int depth, int size) {

        Winner winner = Game.IsOver(state, size, depth);

        if (winner.getIsOver()) {
            State parent = state.getParent();
            parent.done = true;
            return winner.getUtilityValue();
        }

        state.setUtilityValue(Integer.MAX_VALUE);

        // Generate all children
        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = state.getNextChild();

            state.setUtilityValue(Min(state.getUtilityValue(), MaxValue(child, alpha, beta, depth + 1, size)));

            if (state.done) {
                if (depth != 0) {
                    state.clearChildArrayList();
                }
                break;
            }

            if (state.getUtilityValue() <= alpha) {
                return state.getUtilityValue();
            }

            beta = Min(beta, state.getUtilityValue());
        }

        if (depth != 0) {
            state.clearChildArrayList();
        }

        return state.getUtilityValue();
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
}
