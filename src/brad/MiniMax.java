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
                board[row][col] = square.contains();
            }
        }

        // Call MiniMax with the incoming game state
        State state = new State(board, MIN, size);
        Action action = MiniMax_AlphaBetaPruning(state, 0, size);

        // Handle the action returned from the MiniMax algorithm
        int row = action.row();
        int col = action.column();
        GridSquare gridSquare = (GridSquare) gameBoard.lookup("#" + row + col);

        gridSquare.setContains( (action.symbol() == MAX) ? MAX : MIN );

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
        for (int i = 0; i < state.numChildInList(); i++) {
            if (state.getChild(i).utilityValue() == sigma) {
                action = state.getChild(i).action();
                break;
            }
        }

        // If there is not a child who's utility value matches sigma
        // then return the child's action that has the smallest utility value
        if (action == null) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < state.numChildInList(); i++) {
                if (state.getChild(i).utilityValue() < min) {
                    min = state.getChild(i).utilityValue();
                    action = state.getChild(i).action();
                }
            }
        }

        return action;
    }

    private static int MaxValue(State state, int alpha, int beta, int depth, int size) {

        // Check if terminal state
        Winner winner;
        if ( (winner = Game.IsOver(state, size, depth)).getIsOver()) {
            (state.parent()).done = true;
            return winner.getUtilityValue();
        }

        state.setUtilityValue(Integer.MIN_VALUE);

        // For all children
        for (int i = 0; i < state.numChildren(); i++) {
            // Call MinValue with each child
            state.setUtilityValue(Max(state.utilityValue(), MinValue(state.nextChild(), alpha, beta, depth + 1, size)));

            // If we are not in the top level of the tree
            // and the child is in a terminal state, release all children from memory
            if (state.done) break;

            // Alpha Beta pruning section
            // Stop searching children if the utility value returned from
            // MinValue is larger than the beta value
            if (state.utilityValue() >= beta) {
                if (depth != 0) state.clearChildList();
                return state.utilityValue();
            }

            // set alpha to the new alpha value
            alpha = Max(alpha, state.utilityValue());
        }

        if (depth != 0) state.clearChildList();

        // Return the utility value
        return state.utilityValue();
    }

    private static int MinValue(State state, int alpha, int beta, int depth, int size) {

        // Check if terminal state
        Winner winner;
        if ( (winner = Game.IsOver(state, size, depth)).getIsOver()) {
            (state.parent()).done = true;
            return winner.getUtilityValue();
        }

        state.setUtilityValue(Integer.MAX_VALUE);

        // For all children
        for (int i = 0; i < state.numChildren(); i++) {

            state.setUtilityValue(Min(state.utilityValue(), MaxValue(state.nextChild(), alpha, beta, depth + 1, size)));

            // If we are not in the top level of the tree
            // and the child is in a terminal state, release all children from memory
            if (state.done) break;

            if (state.utilityValue() <= alpha) {
                if (depth != 0) state.clearChildList();
                return state.utilityValue();
            }

            beta = Min(beta, state.utilityValue());
        }

        if (depth != 0) state.clearChildList();

        return state.utilityValue();
    }

    private static int Max(int sigma, int minimaxValue) {

        return (sigma > minimaxValue) ? sigma : minimaxValue;
    }

    private static int Min(int sigma, int minimaxValue) {

        return (sigma < minimaxValue) ? sigma : minimaxValue;
    }
}