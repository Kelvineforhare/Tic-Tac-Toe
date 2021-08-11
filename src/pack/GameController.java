package pack;


import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;




public class GameController
{
    private static final double SIZE = 3;
    @FXML
    private Pane pane;
    @FXML
    private BorderPane borderPane;

    private GameLogic gameLogic;



    @FXML
    protected void initialize()
    {
        //tacoList.add(new Taco(i))
        //make variable in a loop or do above
        //1 for vertical and one for horizontal
        int lines = 2;



        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            System.out.println(e.getX());
            System.out.println(e.getY());
            System.out.println(gridPane.getWidth());
            System.out.println(gridPane.getHeight());
        });

        //MAKE BRANCH FOR GRID AND LINES

        StackPane centerPane = new StackPane(gridPane);
        centerPane.setPrefSize(SIZE*50, SIZE*50);

        for(int y=0; y<SIZE; y++) {
            for (int j = 0; j < SIZE; j++) {
                Pane pane = new Pane();
                //pane.setStyle("-fx-background-color:red");
                gridPane.add(pane, y, j);
                pane.prefWidthProperty().bind(Bindings.min(centerPane.widthProperty().divide(SIZE),
                        centerPane.heightProperty().divide(SIZE)));
                pane.prefHeightProperty().bind(Bindings.min(centerPane.widthProperty().divide(SIZE),
                        centerPane.heightProperty().divide(SIZE)));
            }
        }
        gridPane.setGridLinesVisible(true);
        borderPane.setCenter(centerPane);
    }



    @FXML
    public void test3(javafx.scene.input.MouseEvent mouseEvent)
    {
        System.out.println(mouseEvent.getX());
        System.out.println(mouseEvent.getY());

        //pane.getScene().getWindow().setHeight(pane.getHeight()*2);
        System.out.println(pane.widthProperty() + "" + pane.heightProperty());
    }
}
