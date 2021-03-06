package brad;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Brad on 11/4/2015.
 *
 * Custom StackPane
 *  Used to stack a rectangle with a black border
 *  Represents one 'tile' in the game board
 */
public class GridSquare extends StackPane {

    private char contains;  // 'e' if empty, 'x' if x, 'o' if o

    public GridSquare(double squareWidth, double squareHeight, GridPane parent, int size) {

        Rectangle rect = new Rectangle(squareWidth, squareHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);

        rect.widthProperty().bind(parent.widthProperty().divide(size));
        rect.heightProperty().bind(parent.heightProperty().divide(size));

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect);
    }

    public void setContains(char contains) {
        this.contains = contains;
    }

    public char contains() {
        return contains;
    }

}
