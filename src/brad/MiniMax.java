package brad;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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

        // Handle the action returned from the MiniMax algorithm
        int row = action.getRow();
        int col = action.getCol();
        GridSquare gridSquare = (GridSquare) gameBoard.lookup("#" + row + col);

        MyImageView imageView = new MyImageView();
        imageView.setId("image");

        if (action.getImage() == MAX) {

            imageView.setImage(PLAYER);
            gridSquare.setContains(MAX);

        } else if (action.getImage() == MIN) {

            imageView.setImage(CPU);
            gridSquare.setContains(MIN);

        }

        imageView.fitWidthProperty().bind(gridSquare.widthProperty().subtract(gridSquare.widthProperty().divide(4)));
        imageView.fitHeightProperty().bind(gridSquare.heightProperty().subtract(gridSquare.heightProperty().divide(4)));

        gridSquare.getChildren().add(imageView);

        return Game.IsOver(gameBoard, size);
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

        // If the state is a finished state return its utility value
        if ((Game.IsOver(state, size)).getIsOver()) {
            Winner utilityValue = Game.UtilityValue(state, depth, size);
            State parent = state.getParent();
            parent.done = true;
            return utilityValue.getUtilityValue();
        }

        state.setUtilityValue(Integer.MIN_VALUE);
        ArrayList<State> children = getAllChildren(state, size);

        for (State child : children) {

            // Call MinValue with each child
            state.setUtilityValue(Max(state.getUtilityValue(), MinValue(child, alpha, beta, depth + 1, size)));

            // If the states child has reached a terminal state
            // no need to check other children
            if (state.done) {
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

        // Return the utility value
        return state.getUtilityValue();
    }

    private static int MinValue(State state, int alpha, int beta, int depth, int size) {

        if ((Game.IsOver(state, size)).getIsOver()) {
            Winner utilityValue = Game.UtilityValue(state, depth, size);
            State parent = state.getParent();
            parent.done = true;
            return utilityValue.getUtilityValue();
        }

        state.setUtilityValue(Integer.MAX_VALUE);
        ArrayList<State> children = getAllChildren(state, size);

        // Generate all children
        for (State child : children) {

            state.setUtilityValue(Min(state.getUtilityValue(), MaxValue(child, alpha, beta, depth + 1, size)));

            if (state.done) {
                break;
            }

            if (state.getUtilityValue() <= alpha) {
                return state.getUtilityValue();
            }

            beta = Min(beta, state.getUtilityValue());
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

    private static ArrayList<State> getAllChildren(State state, int size) {

        char[][] boardCopy = state.getBoardCopy();
        Action action = null;
        ArrayList<State> children = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                if (boardCopy[row][col] == 'e') {

                    action = new Action();
                    State child;

                    if (state.getWhosTurn() == MiniMax.MIN) {

                        boardCopy[row][col] = MIN;
                        action.setImage(MIN);
                        child = new State(boardCopy, MiniMax.MAX, size);

                    } else {

                        boardCopy[row][col] = MAX;
                        action.setImage(MAX);
                        child = new State(boardCopy, MiniMax.MIN, size);
                    }

                    action.setRow(row);
                    action.setCol(col);
                    child.setAction(action);
                    child.setParent(state);
                    children.add(child);
                    boardCopy[row][col] = 'e';
                    state.addChild(child);
                }
            }
        }

        return children;
    }
}
