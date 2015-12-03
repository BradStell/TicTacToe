package brad;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
    private static int DEPTH = 0;
    private static AnchorPane mainAnchor;


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
        mainAnchor = (AnchorPane) root.lookup("#mainAnchor");
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

            Winner winner = Game.IsOver(gameBoard);

            if (!winner.getIsOver()) {
                winner = MiniMax.Start(gameBoard, 3, PLAYER, CPU, DEPTH++);
                if (winner.getIsOver()) {
                    drawRect(winner);
                    setScoreAndReset(bottomAnchor);
                }
            } else {
                drawRect(winner);
                setScoreAndReset(bottomAnchor);
            }
        }
        else
            System.out.print("Already has an x\n");

    };

    private static void drawRect(Winner winner) {

        if (winner.getWhoWon() != 0) {
            Game.WhoWon(gameBoard);

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
        double startY = (mainAnchor.getHeight() / 6.0) + ((mainAnchor.getHeight() / 3) * winner.getStartLine()) - (height /2);

        drawLine(startX, startY, width, height, false, winner);
    }

    private static void drawVerticalLine(Winner winner) {

        double width = 7;
        double height = mainAnchor.getHeight() - 20;
        double startX = (mainAnchor.getWidth() / 6.0) + ((mainAnchor.getWidth() / 3) * winner.getStartLine()) - (width /2);
        double startY = 10;


        drawLine(startX, startY, width, height, false, winner);
    }

    private static void drawDiagonalLine(Winner winner) {

        double width = mainAnchor.getWidth() - 20;
        double height = 7;
        double startX = 10;
        double startY = (mainAnchor.getHeight() / 6.0) + ((mainAnchor.getHeight() / 3) * 1) - (height /2);

        drawLine(startX, startY, width, height, true, winner);
    }

    private static void drawLine(double startX, double startY, double width, double height, boolean rotate, Winner winner) {

        Rectangle r = new Rectangle();
        r.setX(startX);
        r.setY(startY);
        r.setWidth(width);
        r.setHeight(height);
        r.setId("Rectangle");

        if (rotate) {
            if (winner.getStartLine() == 0)
                r.setRotate(33);
            else
                r.setRotate(-33);
        }

        mainAnchor.getChildren().add(r);

        System.out.print("\n\n**** Drew Line ****\n\n");
    }

    private static void setScoreAndReset(AnchorPane bottomAnchor) {

        DEPTH = 0;
        Label cpu = (Label) bottomAnchor.lookup("#cpuScore");
        Label player = (Label) bottomAnchor.lookup("#yourScore");

        Winner who_won = Game.WhoWon(gameBoard);

        if (who_won.getWhoWon() == 1) {
            player.setText(++playerScore + "");
        } else if (who_won.getWhoWon() == -1) {
            cpu.setText(++cpuScore + "");
        }

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> Game.StartOver(gameBoard, mainAnchor));
        new Thread(sleeper).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
