package brad;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 * Created by Brad on 11/7/2015.
 */
public class MiniMax {

    static final char MAX = 'x';
    static final char MIN = 'o';
    static Image PLAYER;
    static Image CPU;

    public static void Start(GridPane gameBoard, int size, Image player, Image cpu) {

        PLAYER = player;
        CPU = cpu;

        char[][] board = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
                System.out.print(board[row][col] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        // Call minimax with the state
        State state = new State(board, MIN);
        Action action = MiniMax_AlphaBetaPruning(state, 0);

        int row = action.getRow();
        int col = action.getCol();
        GridSquare gridSquare = (GridSquare) gameBoard.lookup("#" + row + col);

        MyImageView imageView = new MyImageView();

        if (action.getImage() == 'x') {

            imageView.setImage(PLAYER);
            gridSquare.setContains('x');

        } else if (action.getImage() == 'o') {

            imageView.setImage(CPU);
            gridSquare.setContains('o');

        }

        GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);

        imageView.fitWidthProperty().bind(square.widthProperty().subtract(square.widthProperty().divide(4)));
        imageView.fitHeightProperty().bind(square.heightProperty().subtract(square.heightProperty().divide(4)));

        square.getChildren().add(imageView);
    }

    private static Action MiniMax_AlphaBetaPruning(State state, int depth) {

        int sigma = MinValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

        System.out.print("Sigma = " + sigma + "\n");

        int min = Integer.MAX_VALUE;
        Action action = null;
        for (int i = 0; i < state.getNumChildren(); i++) {
            System.out.print(state.getChild(i).getUtilityValue() + " ");
            if (state.getChild(i).getUtilityValue() < min) {
                min = state.getChild(i).getUtilityValue();
                action = state.getChild(i).getAction();
            }
        }


        /*System.out.print("Sigma = " + sigma + "\n");

        for (int i = 0; i < state.getNumChildren(); i++) {
            System.out.print(state.getChild(i).getUtilityValue() + " ");
        }
        Action action = null;

        for (int i = 0; i < state.getNumChildren(); i++) {
            if (state.getChild(i).getUtilityValue() == sigma) {
                action = state.getChild(i).getAction();
                System.out.print("Row = " + state.getChild(i).getAction().getRow() + " Col = " + state.getChild(i).getAction().getCol() + "\n\n");
                break;
            }
        }*/

        return action;
    }

    private static int MaxValue(State state, int alpha, int beta, int depth) {

        if (terminalTest(state)) {
            return utility(state, depth);
        }

        state.setUtilityValue(Integer.MIN_VALUE);

        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = getNextAvailableChild(state);

            state.setUtilityValue(Max(state.getUtilityValue(), MinValue(child, alpha, beta, depth + 1)));

            if (state.getUtilityValue() >= beta) {
                return state.getUtilityValue();
            }

            alpha = Max(alpha, state.getUtilityValue());
        }

        return state.getUtilityValue();
    }

    private static int MinValue(State state, int alpha, int beta, int depth) {

        if (terminalTest(state)) {
            return utility(state, depth);
        }

        state.setUtilityValue(Integer.MAX_VALUE);

        // Generate all children
        for (int i = 0; i < state.getNumChildren(); i++) {

            State child = getNextAvailableChild(state);

            state.setUtilityValue(Min(state.getUtilityValue(), MaxValue(child, alpha, beta, depth + 1)));

            if (state.getUtilityValue() <= alpha) {
                return state.getUtilityValue();
            }

            beta = Min(beta, state.getUtilityValue());
        }

        return state.getUtilityValue();
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

    private static int utility(State state, int depth) {

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
                return 10 + depth;
            } else if (ocount == 3) {
                return -10 - depth;
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
                return 10 + depth;
            } else if (ocount == 3) {
                return -10 - depth;
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
            return 10 + depth;
        } else if (ocount == 3) {
            return -10 - depth;
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
            return 10 + depth;
        } else if (ocount == 3) {
            return -10 - depth;
        }

        return score;

        /*// Check for vertical winner
        int verticalScore = checkVerticalScore(board, depth);

        // Check for horizontal winner
        int horizontalScore = checkHorizontalScore(board, depth);

        // Check for diagonal winner
        int diagonalScore = checkDiagonalScore(board, depth);

        return Max(verticalScore, horizontalScore, diagonalScore);*/
    }

    private static int Max(int v, int h, int d) {

        int score = 0;

        if (v > h) {
            score = v;
        } else {
            score = h;
        }

        if (d > score) {
            score = d;
        }

        return score;
    }

    private static int checkVerticalScore(char[][] board, int depth) {

        int xcount;
        int ocount;
        int score = 0;

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

        return score;
    }

    private static int checkHorizontalScore(char[][] board, int depth) {

        int xcount = 0;
        int ocount = 0;
        int score = 0;

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

        return score;
    }

    private static int checkDiagonalScore(char[][] board, int depth) {

        int xcount = 0;
        int ocount = 0;
        int score = 0;

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

        char[][] actualState = state.getBoard();
        char[][] copyState = state.getBoardCopy();
        State child = null;
        boolean createdChild = false;
        Action action = new Action();

        // Generate next available child
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                // Find the empty cell
                if (actualState[row][col] == 'e') {

                    // If its max's turn
                    if (state.getWhosTurn() == MiniMax.MAX) {

                        actualState[row][col] = 'x';
                        copyState[row][col] = 'x';
                        child = new State(copyState, MiniMax.MIN);
                        child.setParent(state);
                        createdChild = true;

                        action.setRow(row);
                        action.setCol(col);
                        action.setImage('x');
                        child.setAction(action);

                        break;
                    }

                    // If its min's turn
                    else if (state.getWhosTurn() == MiniMax.MIN) {

                        actualState[row][col] = 'o';
                        copyState[row][col] = 'o';
                        child = new State(copyState, MiniMax.MAX);
                        child.setParent(state);
                        createdChild = true;

                        action.setRow(row);
                        action.setCol(col);
                        action.setImage('o');
                        child.setAction(action);

                        break;
                    }


                }
            }

            if (createdChild) {
                break;
            }
        }



        if (child == null) {
            return state;
        } else {
            state.addChild(child);
            return child;
        }
    }

}
