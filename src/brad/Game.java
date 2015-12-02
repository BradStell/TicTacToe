package brad;

import javafx.scene.layout.GridPane;

/**
 * Created by Bradley on 12/1/2015.
 */
public class Game {

    public static Winner IsOver(GridPane gameBoard) {

        Winner winner = WhoWon(gameBoard);

        if (winner.getWhoWon() == 1 || winner.getWhoWon() == -1) {
            winner.setIsOver(true);
            return winner;
        }

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                if (square.getContains() == 'e') {
                    winner.setIsOver(false);
                    return winner;
                }
            }
        }

        return winner;
    }

    public static boolean IsOver(State state) {

        char[][] boardCopy = state.getBoardCopy();
        Winner winner = whoWon(boardCopy);

        if (winner.getWhoWon() == 1 || winner.getWhoWon() == -1) {
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
                winner.setStartLine(row);
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.VERTICAL);
                winner.setStartLine(row);
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
                return winner;
            } else if (ocount == 3) {
                winner.setWhoWon(-1);
                winner.setDirection(Winner.HORIZONTAL);
                winner.setStartLine(row);
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
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(0);
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
            return winner;
        } else if (ocount == 3) {
            winner.setWhoWon(-1);
            winner.setDirection(Winner.DIAGONAL);
            winner.setStartLine(2);
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

        return whoWon(board);

    }

    public static void StartOver(GridPane gameBoard) {

        sleep(2000);

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                square.getChildren().remove(gameBoard.lookup("#image"));
                square.setContains('e');
            }
        }

    }

    private static void sleep(int time) {

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - time < startTime);
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
