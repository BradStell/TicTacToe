package brad;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller Class
 *  Used to interface between FXML file MainView.fxml
 */
public class Controller implements Initializable {

    @FXML
    ComboBox<String> sizeBox;

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
    }
}
