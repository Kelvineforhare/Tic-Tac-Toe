package pack;


import javafx.beans.InvalidationListener;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

        score.setText("0 - 0");

        pane.setStyle("-fx-background-color: #ffe990");
        //tacoList.add(new Taco(i))
        //make variable in a loop or do above
        //1 for vertical and one for horizontal

        drawGameBoard();
    }


    //issue with the swap colours
    @FXML
    public void pressButton(javafx.scene.input.MouseEvent mouseEvent)
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

            placeSymbol(symbol,ticTacX,ticTacY);

            if(gameLogic.isPlayer1Turn()){
                player1.setStyle(null);
                player2.setStyle("-fx-background-color: firebrick");
            }
            else{
                player2.setStyle(null);
                player1.setStyle("-fx-background-color: firebrick");
            }

        } catch (OutOfBoundException e)
        {
            //Add text on screen saying issue for both
            System.out.println("\n  Number too large enter within range");
        } catch (BoxTakenException e)
        {
            e.printStackTrace();
        }

        gameLogic.printBoard();

        if(gameLogic.checkHasForRoundWin()){
            player2.setStyle(null);
            player1.setStyle("-fx-background-color: firebrick");
            if(gameLogic.isGameOver()){
                score.setText("0 - 0");
            }
            else
            {
                score.setText(gameLogic.getPlayer1().getScore() + " - " + gameLogic.getPlayer2().getScore());
            }
            gameLogic.resetBoardAndPlayer();
            resetSymbols();
            System.out.println("\n");
            gameLogic.printBoard();
        }
    }

    private void placeSymbol(GameSymbols gameSymbol,int x , int y)
    {

        switch (gameSymbol)
        {
            case X -> {
                Line line = new Line();

                line.startXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y));
                line.endXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y + 1));
                line.startYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x));
                line.endYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x + 1));

                Line line2 = new Line();

                line2.startXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y + 1));
                line2.endXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y));
                line2.startYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x));
                line2.endYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x + 1));

                pane.getChildren().addAll(line, line2);
            }
            case O -> {
                double radiusChoice1 = pane.widthProperty().divide(lines + 1).get();
                double radiusChoice2 = pane.heightProperty().divide(lines + 1).get();


                Circle circle = new Circle();

                circle.centerXProperty().bind(pane.widthProperty().divide(lines + 1).multiply(y).add(pane.widthProperty().divide(lines + 1).multiply(y + 1)
                        .subtract(pane.widthProperty().divide(lines + 1).multiply(y)).divide(2)));

                circle.centerYProperty().bind(pane.heightProperty().divide(lines + 1).multiply(x).add(pane.heightProperty().divide(lines + 1).multiply(x + 1)
                        .subtract(pane.heightProperty().divide(lines + 1).multiply(x)).divide(2)));


                if(Math.min(radiusChoice1, radiusChoice2) / 2 == radiusChoice1){
                    circle.radiusProperty().bind(pane.widthProperty().divide(lines + 1).divide(2));
                }
                else{
                    circle.radiusProperty().bind(pane.heightProperty().divide(lines + 1).divide(2));
                }

                pane.getChildren().addAll(circle);
            }
        }

    }

    private void resetSymbols()
    {
        pane.getChildren().clear();
        drawGameBoard();
    }

    private void drawGameBoard()
    {
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
