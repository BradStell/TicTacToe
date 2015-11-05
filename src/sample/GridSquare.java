package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Brad on 11/4/2015.
 */
public class GridSquare extends StackPane {

    private int col;
    private int row;
    private char contains;  // 'e' if empty, 'x' if x, 'o' if o

    public GridSquare(double squareWidth, double squareHeight, GridPane parent, int row, int col) {

        Rectangle rect = new Rectangle(squareWidth, squareHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);

        rect.widthProperty().bind(parent.widthProperty().divide(3.0));
        rect.heightProperty().bind(parent.heightProperty().divide(3.0));

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect);

        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setContains(char contains) {
        this.contains = contains;
    }

    public char getContains() {
        return contains;
    }

}
