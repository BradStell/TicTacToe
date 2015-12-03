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

    public static Winner IsOver(GridPane gameBoard) {

        char[][] boardCopy = new char[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardCopy[row][col] = ((GridSquare)gameBoard.lookup("#" + row + col)).getContains();
            }
        }

        return isOver(boardCopy);
    }

    private static Winner isOver(char[][] board) {

        Winner winner = whoWon(board, 0);

        if (winner.getWhoWon() != 0) {
            winner.setIsOver(true);
            return winner;
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 'e') {
                    winner.setIsOver(false);
                    return winner;
                }
            }
        }

        winner.setIsOver(true);
        return winner;
    }

    public static Winner IsOver(State state) {

        char[][] boardCopy = state.getBoardCopy();
        return isOver(boardCopy);
    }

    private static Winner whoWon(char[][] board, int depth) {

        // Check for a winner, either x's or o's

        int xcount;
        int ocount;
        Winner winner = new Winner();

        // Check Vertical Winner
        for (int row = 0; row < 3; row++) {
            xcount = 0;
            ocount = 0;
            for (int col = 0; col < 3; col++) {
                if (board[col][row] == 'x') {
                    xcount++;
                } else if (board[col][row] == 'o') {
                    ocount++;
                }
            }
            if (xcount == 3) {
                winner.setWhoWon(1);
                winner.setDirection(Winner.VERTICAL);
                winner.setStartLine(row);
                winner.setUtilityValue(10 - depth);
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.VERTICAL);
                winner.setStartLine(row);
                winner.setUtilityValue(depth - 10);
                return winner;
            }
        }

        // Horizontal Winner
        xcount = 0;
        ocount = 0;
        for (int row = 0; row < 3; row++) {
            xcount = 0;
            ocount = 0;
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == 'x') {
                    xcount++;
                } else if (board[row][col] == 'o') {
                    ocount++;
                }
            }
            if (xcount == 3) {
                winner.setWhoWon(1);
                winner.setDirection(Winner.HORIZONTAL);
                winner.setStartLine(row);
                winner.setUtilityValue(10 - depth);
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.HORIZONTAL);
                winner.setStartLine(row);
                winner.setUtilityValue(depth - 10);
                return winner;
            }
        }

        // Diagonals
        xcount = 0;
        ocount = 0;
        for (int row = 0; row < 3; row++) {
            if (board[row][row] == 'x') {
                xcount++;
            } else if (board[row][row] == 'o') {
                ocount++;
            }
        }
        if (xcount == 3) {
            winner.setWhoWon(1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(0);
            winner.setUtilityValue(10 - depth);
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(0);
            winner.setUtilityValue(depth - 10);
            return winner;
        }

        xcount = 0;
        ocount = 0;
        int row = 0, col = 2;
        for (int i = 0; i < 3; i++) {
            if (board[row][col] == 'x') {
                xcount++;
            } else if (board[row][col] == 'o') {
                ocount++;
            }
            row++;
            col--;
        }
        if (xcount == 3) {
            winner.setWhoWon(1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(2);
            winner.setUtilityValue(10 - depth);
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(2);
            winner.setUtilityValue(depth - 10);
            return winner;
        }

        return winner;
    }

    public static Winner WhoWon(GridPane gameBoard) {

        char[][] board = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
            }
        }

        return whoWon(board, 0);

    }

    public static void StartOver(GridPane gameBoard, AnchorPane mainAnchor) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                square.getChildren().remove(gameBoard.lookup("#image"));
                square.setContains('e');
            }
        }

        mainAnchor.getChildren().remove(mainAnchor.lookup("#Rectangle"));
    }

    public static Winner UtilityValue(State state, int depth) {

        char[][] board = state.getBoard();
        return whoWon(board, depth);
    }
}
