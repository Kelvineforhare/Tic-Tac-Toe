package pack;

import pack.MadeExceptions.BoxTakenException;
import pack.MadeExceptions.OutOfBoundException;

import java.util.Arrays;
import java.util.Scanner;


public class GameLogic
{

    private GameSymbols[][] gameBoard; //o,o top left
    private Player player1;
    private Player player2;
    private boolean isPlayer1;
    private int bestOf;

    public GameLogic(Player player1, Player player2){
        this(player1,player2,3);
    }

    public GameLogic(Player player1, Player player2,int bestOf){
        this(player1,player2,3,bestOf);
    }

    //Make is so that x == y to win need x (or y) in any direction
    public GameLogic(Player player1, Player player2,int size,int bestOf)
    {
        gameBoard = new GameSymbols[size][size];
        this.player1 = player1;
        this.player2 = player2;
        isPlayer1 = true;
        this.bestOf = bestOf;
    }

    /**
     * Places symbol on the game board and returns if placed
     * @param x the row of the gameboard to place in
     * @param y the column of the gameboard to place in
     */
    public void placeSymbol(int x, int y) throws OutOfBoundException, BoxTakenException
    {
        try {
            if (gameBoard[x][y] == null) {
                if (isPlayer1) {
                    gameBoard[x][y] = GameSymbols.X;
                } else {
                    gameBoard[x][y] = GameSymbols.O;
                }
                isPlayer1 = !isPlayer1;
            }
            else{
                throw new BoxTakenException("Box already taken");
            }
        }catch (ArrayIndexOutOfBoundsException e){
            throw new OutOfBoundException("Input too large");
        }
    }

    /**
     * Checks if a player has won a round and increments that players score
     * @return true if a round has been one false otherwise
     */
    public boolean checkHasForRoundWin()
    {
        GameSymbols winAcrossSymbol;
        GameSymbols winDownSymbol;
        Player playerWin;
        for(int i = 0; i < gameBoard.length;i++){
            if(gameBoard[i][i] != null){
                winAcrossSymbol = checkWinAcross(i);
                winDownSymbol = checkWinDown(i);
                playerWin = getPlayerWin(winAcrossSymbol,winDownSymbol);
                if(playerWin != null){
                    playerWin.incrementScore();
                    return true;
                }
            }
        }
        winAcrossSymbol = checkLeftAcross();
        winDownSymbol =checkRightAcross();
        playerWin = getPlayerWin(winAcrossSymbol,winDownSymbol);
        if(playerWin != null){
            playerWin.incrementScore();
            return true;
        }
        return false;
    }

    /**
     * Checks if the overall game is over
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver()
    {
        int x = player1.getScore();
        int y = player2.getScore();
        return !((bestOf - Math.max(x, y)) < Math.max(x, y));
    }

    /**
     * Gets the current players who's turn it is
     * @return the players who's turn it is
     */
    public Player getCurrentPlayer()
    {
        if(isPlayer1){
            return player1;
        }
        return player2;
    }

    /**
     * Resets the board and which players turn it is
     */
    public void resetBoardAndPlayer()
    {
        for (GameSymbols[] gameSymbols : gameBoard)
        {
            Arrays.fill(gameSymbols, null);
        }
        isPlayer1 = true;
    }

    private Player getPlayerWin(GameSymbols winSymbol, GameSymbols winSymbol2)
    {
        if(winSymbol == GameSymbols.X || winSymbol2 == GameSymbols.X){
            player1.incrementScore();
            bestOf++;
            return player1;
        }
        else if(winSymbol == GameSymbols.O || winSymbol2 == GameSymbols.O){
            player2.incrementScore();
            bestOf++;
            return player2;
        }
        return null;
    }

    private GameSymbols checkWinAcross(int row)
    {
        GameSymbols checkSymbol = gameBoard[row][0];
        for(int i = 1; i < gameBoard[row].length; i++){
            if(gameBoard[row][i] != checkSymbol){
                return null;
            }
        }
        return checkSymbol;
    }


    private GameSymbols checkWinDown(int column)
    {
        GameSymbols checkSymbol = gameBoard[0][column];
        for(int i = 1; i < gameBoard.length; i++){
            if(gameBoard[i][column] != checkSymbol){
                return null;
            }
        }
        return checkSymbol;
    }

    private GameSymbols checkLeftAcross()
    {
        GameSymbols checkSymbol = gameBoard[0][0];
        for(int i = 1; i < gameBoard.length; i++){
            if(gameBoard[i][i] != checkSymbol){
                return null;
            }
        }
        return checkSymbol;
    }

    private GameSymbols checkRightAcross()
    {
        int n = gameBoard[0].length-1;
        GameSymbols checkSymbol = gameBoard[0][n];
        n--;
        for(int i = 1; i < gameBoard.length; i++,n--){
            if(gameBoard[i][n] != checkSymbol){
                return null;
            }
        }
        return checkSymbol;
    }

    /**
     * For visuals
     */
    private void printBoard()
    {
        for (GameSymbols[] gameSymbols : gameBoard)
        {
            System.out.println(Arrays.toString(gameSymbols));
        }
    }

    public static void main(String[] args)
    {
        Player player = new Player("Bob");
        Player player2 = new ComputerPlayer();
        GameLogic gameLogic = new GameLogic(player,player2);

        gameLogic.printBoard();

        while (gameLogic.isGameOver())
        {
            if(gameLogic.checkHasForRoundWin()){
                gameLogic.resetBoardAndPlayer();
                System.out.println("\n");
                gameLogic.printBoard();
            }
            System.out.println("\n" + gameLogic.getCurrentPlayer());
            System.out.println("\n Enter an x coordinate: ");

            Scanner scanner = new Scanner(System.in);
            int x = scanner.nextInt();

            System.out.println("Enter a y coordinate \n");
            int y = scanner.nextInt();

            try
            {
                gameLogic.placeSymbol(x,y);
            } catch (OutOfBoundException e)
            {
                System.out.println("\n  Number too large enter within range");

            } catch (BoxTakenException e)
            {
                e.printStackTrace();
            }

            gameLogic.printBoard();

        }
        System.out.println(player.getName() + ": " + player.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
    }
}

