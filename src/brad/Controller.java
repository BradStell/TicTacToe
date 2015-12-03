package brad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller Class
 *  Used to interface between FXML file MainView.fxml
 */
public class Controller implements Initializable {

    @FXML
    ComboBox<Integer> sizeBox;

    @FXML
    BorderPane root;

    @FXML
    AnchorPane mainAnchor;

    GridPane gameBoard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sizeBox.setItems(FXCollections.observableArrayList(3, 5, 7, 9, 11, 13));
        sizeBox.setEditable(true);
    }

    @FXML
    public void sizeBoxButtonClicked() {

        gameBoard = (GridPane) root.lookup("#gameBoard");

        reDrawGameBoard(gameBoard);
    }

    private void reDrawGameBoard(GridPane gameBoard) {

        gameBoard.getChildren().removeAll();

        /*// Build tic-tac-toe grid
        for (int row = 0; row < sizeBox.getValue(); row++) {
            for (int col = 0; col < sizeBox.getValue(); col++) {

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
        mainAnchor.getChildren().add(gameBoard);*/
    }

    /*private final EventHandler<MouseEvent> myMouseHandler = event -> {

        Object source = event.getSource();
        GridSquare square = (GridSquare) source;

        if (square.getContains() == 'e') {

            square.setContains('x');

            MyImageView imageView = new MyImageView();
            imageView.setId("image");
            imageView.setImage(X_IMAGE);
            imageView.fitWidthProperty().bind(square.widthProperty().subtract(square.widthProperty().divide(4)));
            imageView.fitHeightProperty().bind(square.heightProperty().subtract(square.heightProperty().divide(4)));

            square.getChildren().add(imageView);

            Winner winner = Game.IsOver(gameBoard);

            if (!winner.getIsOver()) {
                winner = MiniMax.Start(gameBoard, 3, PLAYER, CPU);
                if (winner.getIsOver()) {
                    drawRect(winner);
                    setScoreAndReset(bottomAnchor, winner);
                }
            } else {
                drawRect(winner);
                setScoreAndReset(bottomAnchor, winner);
            }
        }

    };*/
}
