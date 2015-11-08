package brad;

import javafx.scene.layout.GridPane;

/**
 * Created by Brad on 11/7/2015.
 */
public class MiniMax {

    //private static char[][] state;

    public static void Start(GridPane gameBoard, int size) {

        char[][] state = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                state[row][col] = square.getContains();
            }
        }

        // Call minimax with the state
        MiniMax(state);
    }

    private static void MiniMax(char[][] state) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void MaxValue() {

    }

    private static void MinValue() {

    }

}
