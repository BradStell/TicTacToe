package brad;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller Class
 *  Used to interface between FXML file MainView.fxml
 */
public class Controller implements Initializable {

    @FXML
    ComboBox<String> sizeBox;

    @FXML
    Label timer;

    int seconds = 0;
    int minutes = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sizeBox.setItems(FXCollections.observableArrayList("3", "4", "5", "7", "9", "11", "13"));
        sizeBox.setEditable(false);
        sizeBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            getStyleClass().add("my-list-cell");
                        }
                    }
                };
            }
        });
        sizeBox.getSelectionModel().selectFirst();

        // For running the clock
        Timeline timeline = new Timeline(new KeyFrame(
           Duration.millis(1000),
            ae -> changeTime()
        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // Method that updates the clock
    private void changeTime() {

        minutes = (seconds == 59) ? minutes + 1 : minutes;
        seconds = (seconds == 59) ? 0 : seconds + 1;

        timer.setText(((minutes < 10) ? "0" + minutes : minutes) + ":" + ((seconds < 10) ? "0" + seconds : seconds));
    }


}
