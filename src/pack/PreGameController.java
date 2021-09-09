package pack;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class PreGameController
{

    private final ObservableList<String> choices = FXCollections.observableArrayList("Human", "Computer");

    //Could player2 screen load from application controller if its still null
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField player1TextField;
    @FXML
    private TextField player2TextField;
    @FXML
    private TextField bestOfTextField;
    @FXML
    private ChoiceBox<String> humanOrComputer = new ChoiceBox<>();
    @FXML
    private CheckBox player1ReadyCheckBox;
    @FXML
    private CheckBox player2ReadyCheckBox;

    private String player1Name;
    private static String player2Name;
    private ArrayList<String> test = new ArrayList<>();
    private int bestOf;
    private boolean isPlayer2Human;
    private boolean isPlayer1Ready;
    private static boolean isPlayer2Ready;

    @FXML
    public void initialize()
    {
        humanOrComputer.setItems(choices);
    }

    @FXML
    private void humanOrComputer(ActionEvent actionEvent)
    {
        if ("Human".equals(humanOrComputer.getSelectionModel().getSelectedItem()))
        {
            try
            {
                Node nextNode = FXMLLoader.load(Objects.requireNonNull(PreGameController.class.getResource("Player2.fxml")));
                borderPane.setRight(nextNode);
            } catch (IOException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Window error");
                alert.setHeaderText("A Window cannot be opened");
                alert.setContentText("Restart application");
                alert.showAndWait();
                e.printStackTrace();
                System.exit(0);
                e.printStackTrace();
            }
        } else
        {
            borderPane.setRight(null);
        }
    }

    //do checks
    @FXML
    public void player1Ready(ActionEvent actionEvent)
    {
        if (player1TextField.getText().isBlank() || bestOfTextField.getText().isBlank() || humanOrComputer.getSelectionModel().getSelectedItem().isBlank())
        {
            //tell user to add it alert or by text
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information missing");
            alert.setHeaderText("Fill out all boxes to continue");
            alert.setContentText("Enter information");
            alert.showAndWait();
            player1ReadyCheckBox.setSelected(false);
        }else
        {
            player1Name = player1TextField.getText();
            try
            {
                bestOf = Integer.parseInt(bestOfTextField.getText());
            }catch (NumberFormatException e){
                //tell user to enter number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not an number");
                alert.setHeaderText("Best of must be a number");
                alert.setContentText("Enter a number in best of");
                alert.showAndWait();
                player1ReadyCheckBox.setSelected(false);
            }
            isPlayer2Human = "Human".equals(humanOrComputer.getSelectionModel().getSelectedItem());
        }
        isPlayer1Ready = player1ReadyCheckBox.isSelected();
    }

    @FXML
    public void player2Ready(ActionEvent actionEvent)
    {
        if (player2TextField.getText().isBlank())
        {
            //tell user to add it alert or by text
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No name error");
            alert.setHeaderText("Enter a name");
            alert.setContentText("Player2 must enter a name");
            alert.showAndWait();
            player2ReadyCheckBox.setSelected(false);
        }else
        {
            player2Name = player2TextField.getText();
        }
        isPlayer2Ready = player2ReadyCheckBox.isSelected();
    }

    @FXML
    public void start(ActionEvent actionEvent)
    {
        //check if both ready ticked if human picked*******
        if (isPlayer1Ready && isPlayer2Ready)
        {
            Player player1 = new Player(player1Name);
            Player player2;
            if (isPlayer2Human)
            {
                player2 = new Player(player2Name);
            } else
            {
                player2 = new ComputerPlayer();
            }
            GameLogic gameLogic = new GameLogic(player1, player2, bestOf);
            GameController.setGameLogic(gameLogic);

            ApplicationController.tryLoad(PreGameController.class.getResource("game.fxml"));
        }
        //System.out.println(player1Name + " " + player2Name + " " + bestOf + " " + humanOrComputer.getSelectionModel().getSelectedItem());

    }


}

