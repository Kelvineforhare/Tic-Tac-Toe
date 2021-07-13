package pack;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

;
import java.io.IOException;
import java.util.Objects;


public class PreGameController
{
    private final ObservableList<String> choices = FXCollections.observableArrayList("Human","Computer");


    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> humanOrComputer;

    @FXML
    private void initialize()
    {
        humanOrComputer.setItems(choices);
        humanOrComputer.setOnAction((event) -> {
            if ("Human".equals(humanOrComputer.getSelectionModel().getSelectedItem())){
                try {
                    borderPane.setRight(FXMLLoader.load(Objects.requireNonNull(PreGameController.class.getResource("Player2.fxml"))));
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Window error");
                    alert.setHeaderText("A Window cannot be opened");
                    alert.setContentText("Restart application");
                    alert.showAndWait();
                    e.printStackTrace();
                    System.exit(0);
                    e.printStackTrace();
                }
            }
            else{
                borderPane.setRight(null);
            }
        });
    }





}

