package mpl1.thelastguest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DialogueTest {

    @Test
    void testDefaultValues() {
        Dialogue dialogue = new Dialogue();

        assertNull(dialogue.getType(), "Le type devrait être null par défaut");
        assertNull(dialogue.getMessage(), "Le message devrait être null par défaut");
    }
}
