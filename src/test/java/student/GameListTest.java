package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameListTest {
    private GameList gameList;

    @BeforeEach
    void setUp() {
        gameList = new GameList();
    }

    @Test
    void getGameNames() {
        gameList.addToList("Catan", Stream.of(new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Agricola", Stream.of(new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Boss Quest", Stream.of(new BoardGame("Boss Quest", 3, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));

        List<String> names = gameList.getGameNames();
        assertEquals(List.of("Agricola", "Boss Quest", "Catan"), names);
    }

    @Test
    void clear() {
        gameList.addToList("Catan", Stream.of(new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Agricola", Stream.of(new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Boss Quest", Stream.of(new BoardGame("Boss Quest", 3, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));

        gameList.clear();
        assertEquals(0, gameList.count());

    }


    @Test
    void count() {
        assertEquals(0, gameList.count());
        gameList.addToList("Catan", Stream.of(new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Agricola", Stream.of(new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Boss Quest", Stream.of(new BoardGame("Boss Quest", 3, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        assertEquals(3, gameList.count());
    }

    @Test
    void saveGame() throws IOException {
        gameList.addToList("Catan", Stream.of(new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Agricola", Stream.of(new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.addToList("Boss Quest", Stream.of(new BoardGame("Boss Quest", 3, 3, 4, 60, 30, 2.5, 1, 4.5, 1995)));
        gameList.saveGame("test");
        List<String> actual = Files.readAllLines(Paths.get("test.txt"));
        List<String> expected = gameList.getGameNames();
        assertEquals(expected, actual);


    }

    @Test
    void addToListAll() {
        Stream<BoardGame> filtered = Stream.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("ALL", filtered);
        assertEquals(3, gameList.count());

    }

    @Test
    void addToListByName() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("Catan", filtered.stream());
        assertEquals(1, gameList.count());
        assertEquals("Catan", gameList.getGameNames().get(0));
        //test not found
        gameList.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("abcde", filtered.stream());
        });

    }

    @Test
    void addToListCase() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("cAtAN", filtered.stream());
        assertEquals(1, gameList.count());
        assertEquals("Catan", gameList.getGameNames().get(0));
    }

    @Test
    void addToListById() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("2", filtered.stream());
        assertEquals(1, gameList.count());
        assertEquals("Agricola", gameList.getGameNames().get(0));
        //test input out of range
        gameList.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("0", filtered.stream());
        });
        gameList.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("4", filtered.stream());
        });

    }

    @Test
    void addToListByRange() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("1-3", filtered.stream());
        assertEquals(3, gameList.count());
        assertEquals(List.of("Agricola", "Boss Quest", "Catan"), gameList.getGameNames());
        //test out of bound
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("0-3", filtered.stream());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("1-4", filtered.stream());
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("3-1", filtered.stream());
        });
    }

    @Test
    void removeFromListByName() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("all", filtered.stream());
        gameList.removeFromList("Catan");
        assertEquals(2, gameList.count());
        assertEquals(List.of("Agricola", "Boss Quest"), gameList.getGameNames());
        gameList.clear();
        gameList.addToList("all", filtered.stream());
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("catannnn");
        });
    }

    @Test
    void removeFromListCase() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("all", filtered.stream());
        gameList.removeFromList("cAtAn");
        assertEquals(2, gameList.count());
        assertEquals(List.of("Agricola", "Boss Quest"), gameList.getGameNames());
    }

    @Test
    void removeFromListByIndex() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("all", filtered.stream());
        gameList.removeFromList("1");
        assertEquals(2, gameList.count());
        assertEquals(List.of("Boss Quest", "Catan"), gameList.getGameNames());
        gameList.clear();
        gameList.addToList("all", filtered.stream());
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("0");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("4");
        });
    }

    @Test
    void removeFromListByRange() {
        List<BoardGame> filtered = List.of
                (new BoardGame("Catan", 1, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Agricola", 2, 3, 4, 60, 30, 2.5, 1, 4.5, 1995),
                        new BoardGame("Boss Quest", 4, 3, 4, 60, 30, 2.5, 1, 4.5, 1995));
        gameList.addToList("all", filtered.stream());
        gameList.removeFromList("1-2");
        assertEquals(1, gameList.count());
        assertEquals(List.of("Catan"), gameList.getGameNames());
        gameList.clear();
        gameList.addToList("all", filtered.stream());
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("0-2");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("1-4");
        });
    }
}