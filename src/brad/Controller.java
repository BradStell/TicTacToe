package brad;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

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
    BorderPane root;

    @FXML
    AnchorPane mainAnchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        sizeBox.setItems(FXCollections.observableArrayList("3", "5", "7", "9", "11", "13"));
        sizeBox.setEditable(true);
        sizeBox.setValue("3");
    }
}
