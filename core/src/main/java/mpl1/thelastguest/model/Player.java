package mpl1.thelastguest.model;

public class Player{
    private String username;
    private Character playerCharacter;

    public Player(String username, Character playerCharacter) {
        this.username = username;
        this.playerCharacter = playerCharacter;
    }

    public String getUsername() {
        return username;
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }
}
