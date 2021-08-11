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


        for(int i = 1; i <= lines;i++){
            Line line = new Line();


            //line.maxHeight() 
            //fix this stuff
            //make pane width/height property variable
           // https://stackoverflow.com/questions/66827855/maintain-gridpane-the-same-height-and-width-aspect-ratio


            line.startXProperty().bind(pane.widthProperty().divide(lines+1).multiply(i));
            line.startYProperty().bind(pane.widthProperty().subtract(pane.widthProperty()));
            line.endXProperty().bind(pane.widthProperty().divide(lines+1).multiply(i));
            line.endYProperty().bind(pane.heightProperty());
            //BorderPane.setAlignment(line, Pos.CENTER_RIGHT);


            //make end height not based on pane
            Line line1 = new Line();
            line1.startXProperty().bind(pane.heightProperty().subtract(pane.heightProperty()));
            line1.startYProperty().bind(pane.heightProperty().divide(lines+1).multiply(i));
            line1.endXProperty().bind(pane.widthProperty());
            line1.endYProperty().bind(pane.heightProperty().divide(lines+1).multiply(i));
            //BorderPane.setAlignment(line1, Pos.CENTER_RIGHT);

            pane.getChildren().addAll(line,line1);



        }
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
