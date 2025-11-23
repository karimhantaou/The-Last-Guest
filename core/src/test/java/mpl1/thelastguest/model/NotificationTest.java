package mpl1.thelastguest.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testDefaultDuration() {
        Notification notif = new Notification("Hello world");

        assertEquals("Hello world", notif.getText(), "Le texte doit correspondre à celui passé en paramètre");
        assertEquals(3f, notif.getDuration(), "La durée par défaut doit être 3f");
    }

    @Test
    void testCustomDuration() {
        Notification notif = new Notification("Custom", 5.5f);

        assertEquals("Custom", notif.getText(), "Le texte doit correspondre à celui passé en paramètre");
        assertEquals(5.5f, notif.getDuration(), "La durée doit correspondre à celle passée en paramètre");
    }
}
