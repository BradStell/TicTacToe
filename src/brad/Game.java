package brad;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Created by Bradley on 12/1/2015.
 *
 * Contains methods for the tic tac toe game
 * Designed to keep minimax as generic as possible handling specific
 * game related tasks here.
 */
public class Game {

    public static Winner IsOver(GridPane gameBoard, int size, int depth) {

        char[][] boardCopy = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                boardCopy[row][col] = ((GridSquare)gameBoard.lookup("#" + row + col)).contains();
            }
        }

        return isOver(boardCopy, size, depth);
    }

    public static Winner IsOver(State state, int size, int depth) {

        return isOver(state.getBoard(), size, depth);
    }

    private static Winner isOver(char[][] board, int size, int depth) {

        Winner winner = whoWon(board, depth, size);

        if (winner.getWhoWon() != 0) {
            winner.setIsOver(true);
            return winner;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 'e') {
                    winner.setIsOver(false);
                    return winner;
                }
            }
        }

        winner.setIsOver(true);
        return winner;
    }

    private static Winner whoWon(char[][] board, int depth, int size) {

        // Check for a winner, either x's or o's
        int xcount;
        int ocount;
        Winner winner = new Winner();

        // Check Vertical Winner
        for (int row = 0; row < size; row++) {
            xcount = 0;
            ocount = 0;
            for (int col = 0; col < size; col++) {
                if (board[col][row] == 'x')
                    xcount++;
                 else if (board[col][row] == 'o')
                    ocount++;
            }
            if (xcount == size) {
                winner.setWhoWon(1);
                winner.setDirection(Winner.VERTICAL);
                winner.setStartLine(row);
                winner.setUtilityValue( ((size * 2) + 1) - depth);
                return winner;
            } else if (ocount == size) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.VERTICAL);
                winner.setStartLine(row);
                winner.setUtilityValue(depth - ((size * 2) + 1));
                return winner;
            }
        }

        // Horizontal Winner
        xcount = 0;
        ocount = 0;
        for (int row = 0; row < size; row++) {
            xcount = 0;
            ocount = 0;
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 'x')
                    xcount++;
                 else if (board[row][col] == 'o')
                    ocount++;
            }
            if (xcount == size) {
                winner.setWhoWon(1);
                winner.setDirection(Winner.HORIZONTAL);
                winner.setStartLine(row);
                winner.setUtilityValue(((size * 2) + 1) - depth);
                return winner;
            } else if (ocount == size) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.HORIZONTAL);
                winner.setStartLine(row);
                winner.setUtilityValue(depth - ((size * 2) + 1));
                return winner;
            }
        }

        // Diagonal top left bottom right
        xcount = 0;
        ocount = 0;
        for (int row = 0; row < size; row++) {
            if (board[row][row] == 'x')
                xcount++;
             else if (board[row][row] == 'o')
                ocount++;
        }
        if (xcount == size) {
            winner.setWhoWon(1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(0);
            winner.setUtilityValue(((size * 2) + 1) - depth);
            return winner;
        } else if (ocount == size) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(0);
            winner.setUtilityValue(depth - ((size * 2) + 1));
            return winner;
        }

        // Diagonal top right bottom left
        xcount = 0;
        ocount = 0;
        int row = 0, col = size - 1;
        for (int i = 0; i < size; i++) {
            if (board[row][col] == 'x')
                xcount++;
             else if (board[row][col] == 'o')
                ocount++;
            row++;
            col--;
        }
        if (xcount == size) {
            winner.setWhoWon(1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(size - 1);
            winner.setUtilityValue(((size * 2) + 1) - depth);
            return winner;
        } else if (ocount == size) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(size - 1);
            winner.setUtilityValue(depth - ((size * 2) + 1));
            return winner;
        }

        return winner;
    }

    public static void StartOver(GridPane gameBoard, AnchorPane mainAnchor, int size) {

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                square.getChildren().remove(gameBoard.lookup("#image"));
                square.setContains('e');
            }
        }

        mainAnchor.getChildren().remove(mainAnchor.lookup("#Rectangle"));
    }
}
