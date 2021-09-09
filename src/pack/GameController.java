package pack;


import javafx.beans.InvalidationListener;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pack.MadeExceptions.BoxTakenException;
import pack.MadeExceptions.OutOfBoundException;




public class GameController
{
    @FXML
    private Pane pane;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label score;

    private static GameLogic gameLogic;
    private int lines;
    private boolean player1Turn;

    public static void setGameLogic(GameLogic gameLogic)
    {
        GameController.gameLogic = gameLogic;
    }

    @FXML
    protected void initialize()
    {
        player1.setText("Player 1: " + gameLogic.getPlayer1().getName());
        player2.setText("Player 2: " + gameLogic.getPlayer2().getName());

        player1.setStyle("-fx-background-color: firebrick");
        player1Turn = true;

        score.setText("0 - 0");

        pane.setStyle("-fx-background-color: #ffe990");
        //tacoList.add(new Taco(i))
        //make variable in a loop or do above
        //1 for vertical and one for horizontal
        int lines = 2;
        this.lines = lines;


        for(int i = 1; i <= lines;i++){
            Line line = new Line();

            line.startXProperty().bind(pane.widthProperty().divide(lines+1).multiply(i));
            line.startYProperty().bind(pane.widthProperty().subtract(pane.widthProperty()));
            line.endXProperty().bind(pane.widthProperty().divide(lines+1).multiply(i));
            line.endYProperty().bind(pane.heightProperty());




            Line line1 = new Line();

            line1.startXProperty().bind(pane.heightProperty().subtract(pane.heightProperty()));
            line1.startYProperty().bind(pane.heightProperty().divide(lines+1).multiply(i));
            line1.endXProperty().bind(pane.widthProperty());
            line1.endYProperty().bind(pane.heightProperty().divide(lines+1).multiply(i));


            pane.getChildren().addAll(line,line1);
        }

    }



    @FXML
    public void test3(javafx.scene.input.MouseEvent mouseEvent)
    {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        int ticTacX = 0;
        int ticTacY = 0;

        boolean xFound = false;
        boolean yFound = false;

        for(int i = 1; i <= lines+1;i++){
            if(x < pane.widthProperty().divide(lines+1).multiply(i).get() && !(yFound)){
                ticTacY = i - 1;
                yFound = true;
            }
            if(y < pane.heightProperty().divide(lines+1).multiply(i).get() && !(xFound)){
                ticTacX = i - 1;
                xFound = true;
            }
        }

        GameSymbols symbol = gameLogic.getCurrentPlayerSymbol();

        try
        {
            gameLogic.placeSymbol(ticTacX,ticTacY);
        } catch (OutOfBoundException e)
        {
            //Add alert box dude
            System.out.println("\n  Number too large enter within range");
        } catch (BoxTakenException e)
        {
            e.printStackTrace();
        }

        placeSymbol(symbol,ticTacX,ticTacY);

        gameLogic.printBoard();

        if(player1Turn){
            player1.setStyle(null);
            player2.setStyle("-fx-background-color: firebrick");
        }
        else{
            player2.setStyle(null);
            player1.setStyle("-fx-background-color: firebrick");
        }

        if(gameLogic.checkHasForRoundWin()){
            player2.setStyle(null);
            player1.setStyle("-fx-background-color: firebrick");
            //check game over (best of 3)
            //Increment score board
            if(gameLogic.isGameOver()){
                score.setText("0 - 0");
            }
            else
            {
                score.setText(gameLogic.getPlayer1().getScore() + " - " + gameLogic.getPlayer2().getScore());
            }
            gameLogic.resetBoardAndPlayer();
            System.out.println("\n");
            gameLogic.printBoard();
        }


        player1Turn = !player1Turn;
    }

    private void placeSymbol(GameSymbols gameSymbol,int x , int y)
    {

        switch (gameSymbol){
            case X:

                System.out.println(x + "" + y);

                Line line = new Line();
                //Maybe make width instrad of width property?
                line.startXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y));
                line.endXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y+1));
                line.startYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x));
                line.endYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x+1));


                Line line2 = new Line();


                line2.startXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y+1));
                line2.endXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y));
                line2.startYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x));
                line2.endYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x+1));


                pane.getChildren().addAll(line,line2);

                break;

            case O :
                double radiusChoice1 = pane.widthProperty().divide(lines + 1).multiply(1).get();
                double radiusChoice2 = pane.heightProperty().divide(lines + 1).multiply(1).get();

                double xCoordinate = pane.heightProperty().divide(lines + 1).multiply(y+ 1)
                                        .subtract(pane.heightProperty().divide(lines + 1).multiply(y).doubleValue()).divide(2).get();

                double yCoordinate = pane.widthProperty().divide(lines + 1).multiply(x+ 1)
                        .subtract(pane.widthProperty().divide(lines + 1).multiply(x).doubleValue()).divide(2).get();


                //make circle bind to coordinate and fix where its placed maybe height and width the issue or x and y
                Circle circle = new Circle(xCoordinate,yCoordinate,Math.min(radiusChoice1,radiusChoice2)/2);
                pane.getChildren().addAll(circle);
                break;

        }

    }


    @FXML
    public void promptClick(javafx.scene.input.MouseEvent mouseEvent)
    {
        pane.getScene().setCursor(Cursor.HAND);

    }

    @FXML
    public void unPromptClick(javafx.scene.input.MouseEvent mouseEvent)
    {
        pane.getScene().setCursor(Cursor.DEFAULT);

    }
}
