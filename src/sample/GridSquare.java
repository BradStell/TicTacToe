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

    public GridSquare(double squareWidth, double squareHeight, GridPane parent) {

        Rectangle rect = new Rectangle(squareWidth, squareHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);

        rect.widthProperty().bind(parent.widthProperty().divide(3.0));
        rect.heightProperty().bind(parent.heightProperty().divide(3.0));

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect);

    }


}
