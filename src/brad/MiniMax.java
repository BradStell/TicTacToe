package brad;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Created by Brad on 11/7/2015.
 */
public class MiniMax {

    static final char MAX = 'x';
    static final char MIN = 'o';
    static Image PLAYER;
    static Image CPU;

    public static void Start(GridPane gameBoard, int size, Image player, Image cpu, int depth) {

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


        /****** Test Board *******/
        char[][] testBoard = {
                {'e', 'x', 'e'},
                {'e', 'e', 'x'},
                {'o', 'o', 'x'}
        };

        // Call minimax with the state
        State state = new State(board, MIN);
        Action action = MiniMax_AlphaBetaPruning(state, 0);

        int row = action.getRow();
        int col = action.getCol();
        GridSquare gridSquare = (GridSquare) gameBoard.lookup("#" + row + col);

        MyImageView imageView = new MyImageView();
        imageView.setId("image");

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

        Action action = null;

        System.out.println("Sigma = " + sigma);

        // Print util values
        for (int i = 0; i < state.getNumChildArraySize(); i++) {
            System.out.print(state.getChild(i).getUtilityValue() + " ");
        }
        System.out.println();

        for (int i = 0; i < state.getNumChildren(); i++) {

            if (state.getChild(i).getUtilityValue() == sigma) {
                action = state.getChild(i).getAction();

                System.out.print("Row = " + state.getChild(i).getAction().getRow() + " Col = " + state.getChild(i).getAction().getCol() + "\n\n");
                break;
            }
        }


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

    private static int MaxValue(State state, int alpha, int beta, int depth) {

        if (Game.IsOver(state)) {
            int blah = Game.UtilityValue(state, depth);
            System.out.print("Util " + blah + "\n");
            State parent = state.getParent();
            parent.done = true;
            return blah;
        }

        char[][] b = state.getBoardCopy();
        System.out.print("Depth " + depth + "\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        state.setUtilityValue(Integer.MIN_VALUE);
        ArrayList<State> children = getAllChildren(state);

        for (int i = 0; i < children.size(); i++) {

            state.setUtilityValue(Max(state.getUtilityValue(), MinValue(children.get(i), alpha, beta, depth + 1)));

            if (state.done) {
                break;
            }

            if (state.getUtilityValue() >= beta) {
                return state.getUtilityValue();
            }

            alpha = Max(alpha, state.getUtilityValue());
        }

        return state.getUtilityValue();
    }

    private static int MinValue(State state, int alpha, int beta, int depth) {

        if (Game.IsOver(state)) {
            int blah = Game.UtilityValue(state, depth);
            System.out.print("Util " + blah + "\n");
            State parent = state.getParent();
            parent.done = true;
            return blah;
        }

        char[][] b = state.getBoardCopy();
        System.out.print("Depth " + depth + "\n");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        state.setUtilityValue(Integer.MAX_VALUE);
        ArrayList<State> children = getAllChildren(state);

        // Generate all children
        for (int i = 0; i < children.size(); i++) {

            state.setUtilityValue(Min(state.getUtilityValue(), MaxValue(children.get(i), alpha, beta, depth + 1)));

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

    private static ArrayList<State> getAllChildren(State state) {

        char[][] boardCopy = state.getBoardCopy();
        Action action = null;
        ArrayList<State> children = new ArrayList<>();
        int index = 0;
        boolean done = false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                if (boardCopy[row][col] == 'e') {

                    action = new Action();
                    State child;

                    if (state.getWhosTurn() == MiniMax.MIN) {

                        boardCopy[row][col] = 'o';
                        action.setImage('o');
                        child = new State(boardCopy, MiniMax.MAX);

                    } else {

                        boardCopy[row][col] = 'x';
                        action.setImage('x');
                        child = new State(boardCopy, MiniMax.MIN);
                    }

                    action.setRow(row);
                    action.setCol(col);
                    child.setAction(action);
                    child.setParent(state);
                    children.add(child);
                    boardCopy[row][col] = 'e';
                    state.addChild(child);

                    /*if (!Game.IsOver(child)) {
                        done = true;
                        break;
                    }*/
                }
            }

            /*if (done) {
                break;
            }*/
        }

        return children;
    }

}
