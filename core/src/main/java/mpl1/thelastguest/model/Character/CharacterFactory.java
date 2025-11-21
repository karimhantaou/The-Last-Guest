package mpl1.thelastguest.model.Character;

public class CharacterFactory {

    public static Character create(String type, Npc npc) {
        switch (type.toLowerCase()) {
            case "player":
                return new Player(npc);
            case "murderer":
                return new Murderer(npc);
                default:
                    return null;
        }
    }
}
