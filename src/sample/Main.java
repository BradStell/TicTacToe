package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    public Image X_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/x-red.png")));
    public Image O_IMAGE = new Image(String.valueOf(getClass().getResource("/resources/images/o-neon-green.png")));

    private final EventHandler<MouseEvent> mouseHandler = event -> {

        Object source = event.getSource();
        GridSquare square = (GridSquare) source;

        if (square.getRow() == 0 && square.getCol() == 0) {

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
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        buildGameBoard(root);
    }

    private void buildGameBoard(Parent root) {

        GridPane gameBoard = new GridPane();
        AnchorPane mainAnchor = (AnchorPane) root.lookup("#mainAnchor");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                // Make a new Tic Tac Toe game square | set it to empty ('e') | make the square grow vertically
                // and horizontally with an expanding window frame
                GridSquare square = new GridSquare(mainAnchor.getWidth() / 3, mainAnchor.getHeight() / 3, gameBoard, row, col);
                square.setContains('e');
                GridPane.setHgrow(square, Priority.ALWAYS);
                GridPane.setVgrow(square, Priority.ALWAYS);

                square.setOnMouseClicked(mouseHandler);

                // Set the squares mouse click listener with Java 8 lambda expression
                /*square.setOnMouseClicked( event -> {

                    System.out.print("clicked " + square.getRow() + " " + square.getCol() + "\n");

                    if (square.getRow() == 0 && square.getCol() == 0) {

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
                    }
                });*/

                gameBoard.setVgap(0.0);
                gameBoard.setHgap(0.0);
                gameBoard.add(square, col, row);
            }
        }

        AnchorPane.setTopAnchor(gameBoard, 0.0);
        AnchorPane.setLeftAnchor(gameBoard, 0.0);
        AnchorPane.setRightAnchor(gameBoard, 0.0);
        AnchorPane.setBottomAnchor(gameBoard, 0.0);

        gameBoard.setAlignment(Pos.CENTER);

        mainAnchor.getChildren().add(gameBoard);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
