package brad;

import javafx.scene.layout.GridPane;

/**
 * Created by Bradley on 12/1/2015.
 */
public class Game {

    public static boolean IsOver(GridPane gameBoard) {

        boolean terminalState = true;
        int winner = WhoWon(gameBoard);

        if (winner == 1 || winner == -1) {
            return true;
        }

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                if (square.getContains() == 'e') {
                    return false;
                }
            }
        }

        return terminalState;
    }

    public static boolean IsOver(State state) {

        char[][] boardCopy = state.getBoardCopy();
        int winner = whoWon(boardCopy);

        if (winner == 1 || winner == -1) {
            return true;
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (boardCopy[row][col] == 'e') {
                    return false;
                }
            }
        }

        return true;
    }

    private static Winner whoWon(char[][] board) {

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
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.VERTICAL);
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
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.HORIZONTAL);
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
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
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
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            return winner;
        }

        return winner;
    }

    public static int WhoWon(GridPane gameBoard) {

        char[][] board = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
            }
        }

        return whoWon(board);

    }

    public static void StartOver(GridPane gameBoard) {

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                square.getChildren().remove(gameBoard.lookup("#image"));
                square.setContains('e');
            }
        }

    }

    public static int UtilityValue(State state, int depth) {

        char[][] board = state.getBoard();
        int score = 0;

        // Check for a winner, either x's or o's

        int xcount;
        int ocount;

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
                return 10 - depth;
            } else if (ocount == 3) {
                return depth - 10;
            }
        }


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
                return 10 - depth;
            } else if (ocount == 3) {
                return depth - 10;
            }
        }

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
            return 10 - depth;
        } else if (ocount == 3) {
            return depth - 10;
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
            return 10 - depth;
        } else if (ocount == 3) {
            return depth - 10;
        }

        return score;
    }


}
