package pack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
    @FXML
    private Button play;
    @FXML
    private Button instruction;

    @FXML
    public void playButton(ActionEvent actionEvent){
        ApplicationController.tryLoad(MenuController.class.getResource("Menu.fxml"));
        System.out.println("test");
    }

}
