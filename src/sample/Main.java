package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Main extends Application {

    public static Image X_IMAGE = new Image(String.valueOf(Main.class.getResource("../../resources/images/x-red.png")));

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
                GridSquare square = new GridSquare(mainAnchor.getWidth() / 3, mainAnchor.getHeight() / 3, gameBoard);
                GridPane.setHgrow(square, Priority.ALWAYS);
                GridPane.setVgrow(square, Priority.ALWAYS);

                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.print("clicked");
                    }
                });

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
