package pack;

import java.util.Arrays;

public class GameLogic {

    private GameSymbols[][] gameBoard; //o,o top left
    private Player player1;
    private Player player2;
    private boolean isPlayer1;

    public GameLogic(Player player1, Player player2){
        this(player1,player2,3,3);
    }

    //Make is so that x == y to win need x (or y) in any direction
    public GameLogic(Player player1, Player player2,int x, int y){
        gameBoard = new GameSymbols[x][y];
        this.player1 = player1;
        this.player2 = player2;
        isPlayer1 = true;
    }

    public void placeSymbol(int x, int y){
        if(gameBoard[x][y] == null) {
            if (isPlayer1) {
                gameBoard[x][y] = GameSymbols.X;
            } else {
                gameBoard[x][y] = GameSymbols.O;
            }
            isPlayer1 = !isPlayer1;
        }
    }
    //Make method that cecks for win and returns player that has won
    //public Player checkWin(){}

    /**
     * For visuals
     */
    private void printBoard(){
        for(int i = 0;i < gameBoard.length;i++){
            System.out.println(Arrays.toString(gameBoard[i]));
        }
    }

    public static void main(String[] args){
        Player player = new Player("Bob");
        Player player2 = new ComputerPlayer();
        GameLogic gameLogic = new GameLogic(player,player2);
        gameLogic.printBoard();
        gameLogic.placeSymbol(0,0);
        System.out.println("\n");
        gameLogic.printBoard();
        gameLogic.placeSymbol(0,0);
        System.out.println("\n");
        gameLogic.printBoard();
    }


}

