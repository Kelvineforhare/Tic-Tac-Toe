package pack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ApplicationController extends Application {
    private static Stage stage;
    private static BorderPane mainPane;
    private static Stack<URL> screenStack = new Stack<>(); //Make a queue
    private static Map<URL, String> title = new HashMap<>();
    private static Node displayingNode;


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationController.stage = stage;
        mainPane = getMainPane();
        tryLoad(MenuController.class.getResource("Menu.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void tryLoad(URL currentUrl) {
        try {
            Node nextNode = FXMLLoader.load(currentUrl);
            mainPane.setCenter(nextNode);
            stage.setTitle(title.get(currentUrl));
            displayingNode = FXMLLoader.load(currentUrl);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Window error");
            alert.setHeaderText("A Window cannot be opened");
            alert.setContentText("Restart application");
            alert.showAndWait();
            e.printStackTrace();
            System.exit(0);
        }
        screenStack.add(currentUrl);
    }

    private BorderPane getMainPane() throws IOException {
        return (BorderPane) FXMLLoader.load(Objects.requireNonNull(ApplicationController.class.getResource("Main.fxml")));
    }



   /* private void showMainView() {
        URL url = pack.Main.class.getResource("pack.Main.fxml");
        try {
            Node node = FXMLLoader.load(url);
            mainPane.setCenter(node);
            stage.setTitle(title.get(url));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Window error");
            alert.setHeaderText("A Window cannot be opened");
            alert.setContentText("Restart application");
            alert.showAndWait();
            System.exit(0);
        }
    }*/

}
