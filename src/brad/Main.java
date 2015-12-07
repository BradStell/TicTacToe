package brad;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Application Start Class
 */

public class Main extends Application {

    private Image X_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/x-red.png")));
    private final Image O_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/o-neon-green.png")));
    public final Image PLAYER = X_IMAGE;
    public final Image CPU = O_IMAGE;
    private static GridPane gameBoard;
    private static int playerScore = 0;
    private static int cpuScore = 0;
    AnchorPane bottomAnchor;
    private static AnchorPane mainAnchor;
    private static ComboBox comboBox;
    private static int size;
    public static boolean IN_BACKGROUND_THREAD = false;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        primaryStage.setTitle("Tic Tac Toe | MiniMax & Alpha Beta Pruning");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/brad/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        size = 3;

        buildGameBoard(root);
    }

    private void buildGameBoard(Parent root) {

        gameBoard = new GridPane();
        gameBoard.setId("gameBoard");
        mainAnchor = (AnchorPane) root.lookup("#mainAnchor");
        bottomAnchor = (AnchorPane) root.lookup("#bottomAnchor");
        comboBox = (ComboBox) root.lookup("#sizeBox");
        Button sizeButton = (Button) root.lookup("#sizeButton");
        sizeButton.setOnMouseClicked(sizeBoxClickListner);

        // Set Grid Pane Column and Row constraints
        setGridPaneConstraints(gameBoard, mainAnchor, size);

        // Set anchors on gameBoard so it expands and contracts with window size
        setAnchors(gameBoard);

        // Build tic-tac-toe grid
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                // Make a new Tic Tac Toe game square | set it to empty ('e') | make the square grow vertically
                // and horizontally with an expanding window frame
                GridSquare square = new GridSquare(mainAnchor.getWidth() / size, mainAnchor.getHeight() / size, gameBoard, size);
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

    private void setGridPaneConstraints(GridPane gameBoard, AnchorPane mainAnchor, int size) {

        for (int i = 0; i < size; i++) {
            ColumnConstraints col1 = new ColumnConstraints(10.0, mainAnchor.getWidth() / size, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true);
            RowConstraints row1 = new RowConstraints(10.0, mainAnchor.getHeight() / size, Double.MAX_VALUE, Priority.ALWAYS, VPos.CENTER, true);

            gameBoard.getColumnConstraints().add(col1);
            gameBoard.getRowConstraints().add(row1);
        }
    }



    private final EventHandler<MouseEvent> myMouseHandler = event -> {

        if (IN_BACKGROUND_THREAD) {
            System.out.print("\nCPU is thinking\n");
        } else {

            Object source = event.getSource();
            GridSquare square = (GridSquare) source;

            if (square.contains() == 'e') {

                square.setContains('x');

                MyImageView imageView = new MyImageView() {{
                    setId("image");
                    setImage(X_IMAGE);
                    fitWidthProperty().bind(square.widthProperty().subtract(square.widthProperty().divide(4)));
                    fitHeightProperty().bind(square.heightProperty().subtract(square.heightProperty().divide(4)));
                }};
                square.getChildren().add(imageView);

                Winner winner = Game.IsOver(gameBoard, size, 0);

                // If the game is not over
                if (!winner.getIsOver()) {

                    // Create new task for running minimax algo
                    // Prevents the UI thread from hanging on long processes
                    Task<Void> gameTask = new Task<Void>() {

                        Winner winner;

                        @Override
                        protected Void call() throws Exception {
                            IN_BACKGROUND_THREAD = true;
                            winner = MiniMax.Start(gameBoard, size, PLAYER, CPU);
                            return null;
                        }

                        @Override
                        protected void succeeded() {

                            MyImageView imageView = new MyImageView();
                            imageView.setId("image");

                            if (winner.action.symbol() == MiniMax.MAX) {
                                imageView.setImage(PLAYER);
                                winner.gridSquare.setContains(MiniMax.MAX);
                            } else if (winner.action.symbol() == MiniMax.MIN) {
                                imageView.setImage(CPU);
                                winner.gridSquare.setContains(MiniMax.MIN);
                            }

                            imageView.fitWidthProperty().bind(winner.gridSquare.widthProperty().subtract(winner.gridSquare.widthProperty().divide(4)));
                            imageView.fitHeightProperty().bind(winner.gridSquare.heightProperty().subtract(winner.gridSquare.heightProperty().divide(4)));

                            winner.gridSquare.getChildren().add(imageView);

                            if (winner.getIsOver()) {
                                drawRect(winner);
                                setScoreAndReset(bottomAnchor, winner);
                            }
                            IN_BACKGROUND_THREAD = false;
                        }
                    };

                    Thread th = new Thread(gameTask);
                    th.setDaemon(true);
                    th.start();

                } else {    // If the game is over
                    drawRect(winner);
                    setScoreAndReset(bottomAnchor, winner);
                }
            }
        }
    };

    private final EventHandler<MouseEvent> sizeBoxClickListner = event -> {

        mainAnchor.getChildren().clear();
        gameBoard.getChildren().clear();
        gameBoard = new GridPane();
        gameBoard.setId("gameBoard");
        String sizeString = (String) comboBox.getValue();
        size = Integer.parseInt(sizeString);

        // Set Grid Pane Column and Row constraints
        setGridPaneConstraints(gameBoard, mainAnchor, size);

        // Set anchors on gameBoard so it expands and contracts with window size
        setAnchors(gameBoard);

        // Build tic-tac-toe grid
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                // Make a new Tic Tac Toe game square | set it to empty ('e') | make the square grow vertically
                // and horizontally with an expanding window frame
                GridSquare square = new GridSquare(mainAnchor.getWidth() / size, mainAnchor.getHeight() / size, gameBoard, size);
                square.setContains('e');
                square.setId("" + row + col);
                square.setOnMouseClicked(myMouseHandler);

                gameBoard.add(square, col, row);
            }
        }

        gameBoard.setAlignment(Pos.CENTER);
        mainAnchor.getChildren().add(gameBoard);
    };

    private static void drawRect(Winner winner) {

        if (winner.getWhoWon() != 0) {

            if (winner.getDirection().equals(Winner.HORIZONTAL)) {
                drawHorizontalLine(winner);
            } else if (winner.getDirection().equals(Winner.VERTICAL)) {
                drawVerticalLine(winner);
            } else if (winner.getDirection().equals(Winner.DIAGONAL)) {
                drawDiagonalLine(winner);
            }
        }
    }

    private static void drawHorizontalLine(Winner winner) {

        double width = mainAnchor.getWidth() - 20;
        double height = 7;
        double startX = 10;
        double startY = (mainAnchor.getHeight() / (2 * size)) + ((mainAnchor.getHeight() / size) * winner.getStartLine()) - (height /2);

        drawLine(startX, startY, width, height, false, winner);
    }

    private static void drawVerticalLine(Winner winner) {

        double width = 7;
        double height = mainAnchor.getHeight() - 20;
        double startX = (mainAnchor.getWidth() / (2 * size)) + ((mainAnchor.getWidth() / size) * winner.getStartLine()) - (width /2);
        double startY = 10;


        drawLine(startX, startY, width, height, false, winner);
    }

    private static void drawDiagonalLine(Winner winner) {

        double width = mainAnchor.getWidth() - 20;
        double height = 7;
        double startX = 10;
        double startY = (mainAnchor.getHeight() / (2 * size)) + ((mainAnchor.getHeight() / size) * 1) - (height /2);

        drawLine(startX, startY, width, height, true, winner);
    }

    private static void drawLine(double startX, double startY, double width, double height, boolean rotate, Winner winner) {

        Rectangle r = new Rectangle();
        r.setId("winner-rect");
        r.setX(startX);
        r.setY(startY);
        r.setWidth(width);
        r.setHeight(height);
        r.setFill(Color.GRAY);
        r.setId("Rectangle");

        if (rotate) {
            if (winner.getStartLine() == 0)
                r.setRotate(33);
            else
                r.setRotate(-33);
        }

        mainAnchor.getChildren().add(r);
    }

    private static void setScoreAndReset(AnchorPane bottomAnchor, Winner winner) {

        Label cpu = (Label) bottomAnchor.lookup("#cpuScore");
        Label player = (Label) bottomAnchor.lookup("#yourScore");

        if (winner.getWhoWon() == 1) {
            player.setText(++playerScore + "");
        } else if (winner.getWhoWon() == -1) {
            cpu.setText(++cpuScore + "");
        }

        // Start a new thread to delay 2 seconds without hanging the GUI thread
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> Game.StartOver(gameBoard, mainAnchor, size));
        new Thread(sleeper).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
