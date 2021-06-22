package pack;

public class GameLogic {

    private GameSymbols[][] gameBoard;
    private Player player1 ;
    private Player player2;

    public GameLogic(Player player1, Player player2) throws SameSymbolException{
        this(player1,player2,3,3);
    }

    public GameLogic(Player player1, Player player2,int x, int y)throws SameSymbolException{
        if (player1.getSymbol() == player2.getSymbol()){
            throw new SameSymbolException("Symbols cannot be the same");
        }
        gameBoard = new GameSymbols[x][y];
        this.player1 = player1;
        this.player2 = player2;
    }





}

