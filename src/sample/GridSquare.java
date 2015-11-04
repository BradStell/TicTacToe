package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Brad on 11/4/2015.
 */
public class GridSquare extends StackPane {

    public GridSquare(double squareWidth, double squareHeight) {

        Rectangle border = new Rectangle(squareWidth, squareHeight);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);

    }


}
