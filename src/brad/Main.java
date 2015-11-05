package brad;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Application Start Class
 */

public class Main extends Application {

    public Image X_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/x-red.png")));
    public Image O_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/o-neon-green.png")));


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/brad/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        buildGameBoard(root);

        System.out.print(Font.getFamilies() + "\n");
        System.out.print(Font.getFontNames());
    }

    private void buildGameBoard(Parent root) {

        GridPane gameBoard = new GridPane();
        AnchorPane mainAnchor = (AnchorPane) root.lookup("#mainAnchor");

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

        System.out.print("Contains an " + square.getContains() + "\n");

        if (square.getContains() == 'e' && square.getContains() != 'o') {

            square.setContains('x');

            MyImageView imageView = new MyImageView();
            imageView.setImage(X_IMAGE);
            imageView.fitWidthProperty().bind(square.widthProperty().subtract(square.widthProperty().divide(4)));
            imageView.fitHeightProperty().bind(square.heightProperty().subtract(square.heightProperty().divide(4)));

            square.getChildren().add(imageView);
        }
        else
            System.out.print("Already has an x\n");

    };

    public static void main(String[] args) {
        launch(args);
    }
}
