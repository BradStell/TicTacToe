package brad;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Application Start Class
 */

public class Main extends Application {

    private final Image X_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/x-red.png")));
    private final Image O_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/o-neon-green.png")));
    public final Image PLAYER = X_IMAGE;
    public final Image CPU = O_IMAGE;
    private static GridPane gameBoard;
    private static int playerScore = 0;
    private static int cpuScore = 0;
    AnchorPane bottomAnchor;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/brad/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        buildGameBoard(root);
    }

    private void buildGameBoard(Parent root) {

        gameBoard = new GridPane();
        AnchorPane mainAnchor = (AnchorPane) root.lookup("#mainAnchor");
        bottomAnchor = (AnchorPane) root.lookup("#bottomAnchor");

        // Set Grid Pane Column and Row constraints
        setGridPaneConstraines(gameBoard, mainAnchor);

        // Set gameBoard anchors to AnchorPane
        setAnchors(gameBoard);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                // Make a new Tic Tac Toe game square | set it to empty ('e') | make the square grow vertically
                // and horizontally with an expanding window frame
                GridSquare square = new GridSquare(mainAnchor.getWidth() / 3, mainAnchor.getHeight() / 3, gameBoard, row, col);
                square.setContains('e');
                square.setId("" + row + col);
                square.setOnMouseClicked(myMouseHandler);

                gameBoard.add(square, col, row);
            }
        }

        gameBoard.setAlignment(Pos.CENTER);
        mainAnchor.getChildren().add(gameBoard);
    }

    private void setAnchors(GridPane gameBoard) {

        AnchorPane.setTopAnchor(gameBoard, 0.0);
        AnchorPane.setLeftAnchor(gameBoard, 0.0);
        AnchorPane.setRightAnchor(gameBoard, 0.0);
        AnchorPane.setBottomAnchor(gameBoard, 0.0);
    }

    private void setGridPaneConstraines(GridPane gameBoard, AnchorPane mainAnchor) {

        ColumnConstraints col1 = new ColumnConstraints(10.0, mainAnchor.getWidth() / 3, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true);
        ColumnConstraints col2 = new ColumnConstraints(10.0, mainAnchor.getWidth() / 3, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true);
        ColumnConstraints col3 = new ColumnConstraints(10.0, mainAnchor.getWidth() / 3, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true);

        RowConstraints row1 = new RowConstraints(10.0, mainAnchor.getHeight() / 3, Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER, true);
        RowConstraints row2 = new RowConstraints(10.0, mainAnchor.getHeight() / 3, Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER, true);
        RowConstraints row3 = new RowConstraints(10.0, mainAnchor.getHeight() / 3, Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER, true);

        gameBoard.getColumnConstraints().addAll(col1, col2, col3);
        gameBoard.getRowConstraints().addAll(row1, row2, row3);
    }

    private final EventHandler<MouseEvent> myMouseHandler = event -> {

        Object source = event.getSource();
        GridSquare square = (GridSquare) source;

        if (square.getContains() == 'e' && square.getContains() != 'o') {

            square.setContains('x');

            MyImageView imageView = new MyImageView();
            imageView.setId("image");
            imageView.setImage(X_IMAGE);
            imageView.fitWidthProperty().bind(square.widthProperty().subtract(square.widthProperty().divide(4)));
            imageView.fitHeightProperty().bind(square.heightProperty().subtract(square.heightProperty().divide(4)));

            square.getChildren().add(imageView);

            if (!terminalState()) {
                MiniMax.Start(gameBoard, 3, PLAYER, CPU);
            } else {

                Label cpu = (Label) bottomAnchor.lookup("#cpuScore");
                Label player = (Label) bottomAnchor.lookup("#yourScore");

                int who_won = whoWon();

                if (who_won == 1) {
                    player.setText(++playerScore + "");
                } else if (who_won == -1) {
                    cpu.setText(++cpuScore + "");
                }

                removeChildren();

            }
        }
        else
            System.out.print("Already has an x\n");

    };

    private static void removeChildren() {

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                square.getChildren().remove(gameBoard.lookup("#image"));
                square.setContains('e');
            }
        }

    }

    private static int whoWon() {

        char[][] board = new char[3][3];

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                board[row][col] = square.getContains();
            }
        }

        // Check for a winner, either x's or o's

        int xcount;
        int ocount;

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
                return 1;
            } else if (ocount == 3) {
                return -1;
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
                return 1;
            } else if (ocount == 3) {
                return -1;
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
            return 1;
        } else if (ocount == 3) {
            return -1;
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
            return 1;
        } else if (ocount == 3) {
            return -1;
        }

        return 0;
    }

    private static boolean terminalState() {

        boolean terminalState = false;

        // Convert gameBoard into 2D array we can work with easier
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                GridSquare square = (GridSquare) gameBoard.lookup("#" + row + col);
                if (square.getContains() == 'e') {
                    return false;
                }
            }
        }

        int winner = whoWon();

        if (winner == 1 || winner == -1 || winner == 0) {
            return true;
        }

        return terminalState;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
