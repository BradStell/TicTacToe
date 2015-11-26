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

        int sigma = MaxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static int MaxValue(State state, int alpha, int beta) {

    }

    private static int MinValue(State state, int alpha, int beta) {

    }

}
