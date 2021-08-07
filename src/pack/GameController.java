package pack;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;




public class GameController
{
    @FXML
    private Pane pane;

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
            line1.endYProperty().bind(pane.widthProperty().divide(lines+1).multiply(i));
            //BorderPane.setAlignment(line1, Pos.CENTER_RIGHT);

            pane.getChildren().addAll(line,line1);
        }

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
