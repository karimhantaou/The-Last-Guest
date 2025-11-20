package mpl1.thelastguest.Test;

import com.badlogic.gdx.ApplicationAdapter;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    @Mock
    private Player mockPlayer;

    @Mock
    private Character mockCharacter1;

    @Mock
    private Character mockCharacter2;

    @Mock
    private Item mockItem1;

    @Mock
    private Item mockItem2;

    private List<Character> characters;
    private List<Item> items;

    private Board board;
    @BeforeAll
    static <HeadlessApplicationConfiguration> void initGdx() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new Headless(new ApplicationAdapter(){}, config);
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        characters = new ArrayList<>(Arrays.asList(mockCharacter1, mockCharacter2));
        items = new ArrayList<>(Arrays.asList(mockItem1, mockItem2));

        board = new Board(32, characters, mockPlayer, items);
    }

    @Test
    void testGetStep() {
        assertEquals(32, board.getStep());
    }

    @Test
    void testSetSize() {
        board.setSize(500, 300);  // min / 50 = 6
        assertEquals(6, board.getStep());

        board.setSize(100, 100);  // 100/50 = 2
        assertEquals(2, board.getStep());
    }

    @Test
    void testFindRoom() {
        Room room = board.findRoom("Kitchen");
        assertNotNull(room);
        assertEquals("Kitchen", room.getName());

        assertNull(board.findRoom("UnknownRoom"));
    }

    @Test
    void testCreateAllRoom() {
        List<Room> rooms = board.createAllRoom();

        assertNotNull(rooms);
        assertEquals(9, rooms.size()); // toujours 9 rooms

        // Chaque item doit avoir été placé dans une room
        boolean foundItem1 = rooms.stream().anyMatch(r -> r.getItems().contains(mockItem1));
        boolean foundItem2 = rooms.stream().anyMatch(r -> r.getItems().contains(mockItem2));

        assertTrue(foundItem1);
        assertTrue(foundItem2);
    }

    @Test
    void testRandomItem() {
        Item item = board.randomItem();

        assertNotNull(item);

        assertFalse(items.contains(item));
    }

    @Test
    void testMoveToPoint() {
        when(mockPlayer.moveToPoint(10, 20)).thenReturn(true);

        boolean result = board.moveToPoint(10, 20);

        assertTrue(result);
        verify(mockPlayer, times(1)).moveToPoint(10, 20);
    }
}
