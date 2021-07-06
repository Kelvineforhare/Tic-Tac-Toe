package pack;

public class Player {
    private String name;
    private int score;
    //abilty to save - make class that implements save?
    public Player(String name){
        this.name = name;
    }

    public void incrementScore()
    {
        score++;
    }

    @Override
    public String toString(){
        return name;
    }

    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }
}
