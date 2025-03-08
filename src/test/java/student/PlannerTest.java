package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    Planner p;
    Set<BoardGame> games;
    @BeforeEach
    void setUp() {
        games = Set.of(
                new BoardGame("Catan", 1, 3, 5, 61, 111, 2.7, 1, 4.5, 1995),
                new BoardGame("Agricola", 2, 4, 6, 62, 112, 2.8, 2, 4.6, 1996),
                new BoardGame("Boss Quest", 4, 5, 8, 63, 113, 2.9, 3, 4.7, 1997)
        );
        p = new Planner(games);
    }

    @Test
    void filterByNameContains() {
        Stream<BoardGame> res = p.filter("name~=Catan");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan"), names);
    }

    @Test
    void filterByNameEquals() {
        Stream<BoardGame> res = p.filter("name==Agricola");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola"), names);
    }

    @Test
    void filterByMinPlayersGreaterThan() {
        Stream<BoardGame> res = p.filter("minPlayers>3");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola", "Boss Quest"), names);
    }

    @Test
    void filterByMaxPlayersLessThan() {
        Stream<BoardGame> res = p.filter("maxPlayers<7");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola","Catan"), names);
    }

    @Test
    void filterByMinTimeGreaterThan() {
        Stream<BoardGame> res = p.filter("minPlayTime>61");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola", "Boss Quest"), names);
    }

    @Test
    void filterByMaxTimeLessThan() {
        Stream<BoardGame> res = p.filter("maxPlayTime<100");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of(), names);
    }

    @Test
    void filterByYearPublished() {
        Stream<BoardGame> res = p.filter("year>=1996");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola", "Boss Quest"), names);
    }

    @Test
    void filterByRatingGreaterThan() {
        Stream<BoardGame> res = p.filter("rating>4.6");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Boss Quest"), names);
    }

    @Test
    void filterByDifficultyLessThan() {
        Stream<BoardGame> res = p.filter("difficulty<2.8");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan"), names);
    }

    @Test
    void filterByRankEquals() {
        Stream<BoardGame> res = p.filter("rank==3");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Boss Quest"), names);
    }

    @Test
    void filterByNameAndYear() {
        Stream<BoardGame> res = p.filter("name~=Catan,year>=1995");
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan"), names);
    }
    @Test
    void filterByNameSortedByRatingTwo() {
        Stream<BoardGame> res = p.filter("name~=Catan", GameData.RATING);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan"), names);
    }
    @Test
    void filterByMinPlayersSortedByYearTwo() {
        Stream<BoardGame> res = p.filter("minPlayers>3", GameData.YEAR);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of( "Agricola", "Boss Quest"), names);
    }
    @Test
    void filterByYearSortedByDifficultyTwo() {
        Stream<BoardGame> res = p.filter("year>=1996", GameData.DIFFICULTY);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola", "Boss Quest"), names);
    }
    @Test
    void filterByMaxTimeSortedByRankTwo() {
        Stream<BoardGame> res = p.filter("maxPlayTime<113", GameData.RANK);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan","Agricola"), names);
    }

    @Test
    void filterByRankSortedByRatingTwo() {
        Stream<BoardGame> res = p.filter("rank<3", GameData.RATING);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan", "Agricola"), names);
    }

    @Test
    void filterByNameSortedByRatingAscendingThree() {
        Stream<BoardGame> res = p.filter("name~=Catan", GameData.RATING, true);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Catan"), names);
    }

    @Test
    void filterByMinPlayersSortedByYearDescendingThree() {
        Stream<BoardGame> res = p.filter("minPlayers>2", GameData.YEAR, false);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Boss Quest", "Agricola", "Catan"), names);
    }

    @Test
    void filterByYearSortedByDifficultyAscendingThree() {
        Stream<BoardGame> res = p.filter("year>=1996", GameData.DIFFICULTY, true);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Agricola", "Boss Quest"), names);
    }

    @Test
    void filterByMaxTimeSortedByRankDescendingThree() {
        Stream<BoardGame> res = p.filter("maxPlayTime<200", GameData.RANK, false);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Boss Quest","Agricola","Catan"), names);
    }

    @Test
    void filterByRankSortedByRatingAscendingThree() {
        Stream<BoardGame> res = p.filter("rank==3", GameData.RATING, true);
        List<String> names = res.map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of("Boss Quest"), names);
    }



    @Test
    void reset() {
        p.filter("rating>4.5"); // Apply filter
        p.reset(); // Reset should restore original set
        List<String> names = p.filter("").map(BoardGame::getName).collect(Collectors.toList());
        assertEquals(List.of( "Agricola", "Boss Quest","Catan"), names);
    }

    @Test
    void splitCondition() {
        String[] res = p.splitCondition("name>=Catan");
        String[] expected = new String[]{"name", ">=", "Catan"};
        assertArrayEquals(expected, res);
    }

    @Test
    void compareString() {
        assertTrue(p.compareString("catan", "==", "Catan"));
        assertFalse(p.compareString("Catan", "==", "Agricola"));
        assertFalse(p.compareString("Agricola", ">", "Catan"));
        assertTrue(p.compareString("Agricola", "<", "Catan"));
    }

    @Test
    void compareInt() {
        assertTrue(p.compareInt(5, "==", "5"));
        assertFalse(p.compareInt(3, "==", "5"));
        assertTrue(p.compareInt(7, ">", "5"));
        assertFalse(p.compareInt(3, ">", "5"));
    }

    @Test
    void compareDouble() {
        assertTrue(p.compareDouble(114.5, "==", "114.5"));
        assertFalse(p.compareDouble(3.5, "==", "41.5"));
        assertTrue(p.compareDouble(15.5, ">", "4.5"));
        assertFalse(p.compareDouble(3.5, ">", "14.5"));
    }

    @Test
    void comparator() {
        Comparator<BoardGame> comp = p.comparator(GameData.NAME, true);
        List<String> sorted = games.stream().sorted(comp).map(BoardGame::getName).toList();
        assertEquals(List.of( "Agricola", "Boss Quest","Catan"), sorted);
        p.reset();
        Comparator<BoardGame> compTwo = p.comparator(GameData.NAME, false);
        List<String> sortedTwo = games.stream().sorted(compTwo).map(BoardGame::getName).toList();
        assertEquals(List.of("Catan","Boss Quest", "Agricola"), sortedTwo);

    }

    @Test
    void matchCondition() {
        BoardGame game = new BoardGame("Catan", 1, 3, 5, 61, 111, 2.7, 1, 4.5, 1995);
        assertTrue(p.matchCondition(game, "name==Catan"));
        assertTrue(p.matchCondition(game, "id==1"));
        assertTrue(p.matchCondition(game, "minplayers==3"));
        assertTrue(p.matchCondition(game, "year==1995"));
        assertFalse(p.matchCondition(game, "rank==3"));
        assertFalse(p.matchCondition(game, "difficulty==5"));
        assertTrue(p.matchCondition(game, "Rating==4.5"));
    }
}