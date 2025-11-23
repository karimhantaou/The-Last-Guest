package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.SelectCharacterScreen;

/**
 * Factory for create Player or Murderer
 */

public class CharacterFactory {

    /**
     * methode to create an instance of player or murderer
     * if type is player: new player
     * if type is murderer: nex murderer
     * @param type type of character (player or murderer)
     * @param npc instance of npc for create instance of player or murderer
     * @return instance of player or murderer
     */
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
