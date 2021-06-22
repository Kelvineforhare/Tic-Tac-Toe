package pack;

public class Player {
    private String name;
    private GameSymbols symbol;
    //abilty to save - make class that implements save?
    public Player(String name){
        this.name = name;
    }

    public void assignSymbol(GameSymbols symbol){
        this.symbol = symbol;
    }

    public GameSymbols getSymbol(){
        return symbol;
    }

}
