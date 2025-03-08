package student;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Planner class filters and sorts a set of board games.
 */
public class Planner implements IPlanner {
    /**
     * has original board game data inside.
     */
    private final Set<BoardGame> originalGames;
    /**
     * used to store filtered games.
     */
    private Set<BoardGame> filteredGames;

    /**
     * constructor for planner.
     *
     * @param games a set of original board games.
     */
    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.originalGames = new HashSet<>(games); // Store original game set
        this.filteredGames = new HashSet<>(games);
    }

    /**
     * Assumes the results are sorted in ascending order, and that the steam is sorted by the name
     * of the board game (GameData.NAME).
     *
     * @param filter The filter to apply to the board games.
     * @return A stream of board games that match the filter.
     * @see #filter(String, GameData, boolean)
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stub
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games by the passed in text filter. Assumes the results are sorted in
     * ascending order.
     *
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @return A stream of board games that match the filter.
     * @see #filter(String, GameData, boolean)
     */

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        return filter(filter, sortOn, true);
    }

    /**
     * Filters the board games by the passed in text filter.
     * <p>
     * <p>
     * A text filter can contain the following options:
     * <p>
     * > : greater than
     * <p>
     * < : less than
     * <p>
     * >= : greater than or equal to
     * <p>
     * <= : less than or equal to
     * <p>
     * == : equal to
     * <p>
     * != : not equal to
     * <p>
     * ~= : contains the text
     * <p>
     * The left side of the filter describes the column to filter on. The right side of the filter
     * describes the value to filter on.
     * <p>
     * Fo example:
     * <p>
     * minPlayers>4
     * <p>
     * would filter the board games to only those with a minimum number of players greater than 4.
     * <p>
     * Commas between filters are treated as ANDs. For example:
     * <p>
     * minPlayers>4,maxPlayers<6
     * <p>
     * It is possible to filter on the same column multiple times. For example:
     * <p>
     * minPlayers>4,minPlayers<6
     * <p>
     * This would filter the board games to only those with a minimum number of players greater than
     * 4 and less than 6.
     * <p>
     * Spaces should be ignored, but can be included for readability. For example:
     * <p>
     * minPlayers > 4
     * <p>
     * is the same as
     * <p>
     * minPlayers>4
     * <p>
     * <p>
     * If filtering on a string column, the filter should be case insensitive. For example:
     * <p>
     * name~=pandemic
     * <p>
     * would filter the board games to only those with the word "pandemic" in the name, but could
     * also have Pandemic or PANDEMIC.
     * <p>
     * Column names will match the values in GameData. As such is it possible to use
     * <p>
     * GameData.MIN_PLAYERS.getColumnName() or GameData.fromString("minplayers") to get the column
     * name for the minPlayers column.
     * <p>
     * Note: id is a special column that is not used for filtering or sorting.
     * <p>
     * if the filter is empty (""), then the results should return the current filter sorted based
     * on the sortOn column and in the defined direction.
     *
     * @param filter    The filter to apply to the board games.
     * @param sortOn    The column to sort the results on.
     * @param ascending Whether to sort the results in ascending order or descending order.
     * @return A stream of board games that match the filter.
     */

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        // TODO Auto-generated method stub
        if (filter == null || filter.isEmpty()) {
            return filteredGames.stream().sorted(comparator(sortOn, ascending));
        }
        String[] conditions = filter.split(",");
        Set<BoardGame> results = new HashSet<>();
        for (BoardGame game : filteredGames) {
            boolean passed = true;
            for (String condition : conditions) {
                if (!matchCondition(game, condition)) {
                    passed = false;
                    break;
                }
            }
            if (passed) {
                results.add(game);
            }
        }
        filteredGames = results;
        return results.stream().sorted(comparator(sortOn, ascending));
    }

    /**
     * Resets the collection to have no filters applied.
     */
    @Override
    public void reset() {
        // TODO Auto-generated method stub

        filteredGames = new HashSet<>(originalGames);
    }

    /**
     * getter for filteredGames.
     * @return filteredGames.
     */
    public Set<BoardGame> getFilteredGames() {
        return filteredGames;
    }

    /**
     * splits a filter condition to 3 parts.
     * for example split difficulty>3 to [difficulty, >, 3].
     *
     * @param condition a filter condition.
     * @return a String array that contains 3 parts.
     */
    public String[] splitCondition(String condition) {

        String[] operators = {"~=", "!=", "==", ">=", "<=", ">", "<"};

        String columnName = null;
        String operator = null;
        String value = null;

        for (String op : operators) {
            int index = condition.indexOf(op);
            if (index != -1) {
                columnName = condition.substring(0, index).trim();
                operator = op.trim();
                value = condition.substring(index + op.length()).trim();
                break;
            }
        }


        return new String[]{columnName, operator, value};
    }

    /**
     * compare two Strings based on operator.
     *
     * @param gameValue actual game value.
     * @param operator  operator.
     * @param condition user passed in condition.
     * @return return true if fits the condition.
     */

    public boolean compareString(String gameValue, String operator, String condition) {
        return switch (operator) {
            case "~=" -> gameValue.toLowerCase().contains(condition.toLowerCase());
            case "==" -> gameValue.equalsIgnoreCase(condition);
            case "!=" -> !gameValue.equalsIgnoreCase(condition);
            case ">" -> gameValue.compareToIgnoreCase(condition) > 0;
            case "<" -> gameValue.compareToIgnoreCase(condition) < 0;
            case ">=" -> gameValue.compareToIgnoreCase(condition) >= 0;
            case "<=" -> gameValue.compareToIgnoreCase(condition) <= 0;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    /**
     * compare two Integers based on operator.
     *
     * @param gameValue actual game value.
     * @param operator  operator.
     * @param condition user passed in condition.
     * @return return true if fits the condition.
     */

    public boolean compareInt(int gameValue, String operator, String condition) {
        int conditionInt = Integer.parseInt(condition);
        return switch (operator) {
            case "==" -> gameValue == conditionInt;
            case "!=" -> gameValue != conditionInt;
            case ">" -> gameValue > conditionInt;
            case "<" -> gameValue < conditionInt;
            case ">=" -> gameValue >= conditionInt;
            case "<=" -> gameValue <= conditionInt;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    /**
     * compare two doubles based on operator.
     *
     * @param gameValue actual game value.
     * @param operator  operator.
     * @param condition user passed in condition.
     * @return return true if fits the condition.
     */
    public boolean compareDouble(double gameValue, String operator, String condition) {
        double conditionDouble = Double.parseDouble(condition);
        return switch (operator) {
            case "==" -> gameValue == conditionDouble;
            case "!=" -> gameValue != conditionDouble;
            case ">" -> gameValue > conditionDouble;
            case "<" -> gameValue < conditionDouble;
            case ">=" -> gameValue >= conditionDouble;
            case "<=" -> gameValue <= conditionDouble;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    /**
     * comparator for sorting different columns.
     *
     * @param sortOn    column that need to be sorted.
     * @param ascending ascending true or descending false
     * @return a comparator based on input col.
     */
    public Comparator<BoardGame> comparator(GameData sortOn, boolean ascending) {
        Comparator<BoardGame> comparator = switch (sortOn) {
            case NAME -> (a, b) -> a.getName().compareToIgnoreCase(b.getName());
            case ID -> (a, b) -> Integer.compare(a.getId(), b.getId());
            case RANK -> (a, b) -> Integer.compare(a.getRank(), b.getRank());
            case YEAR -> (a, b) -> Integer.compare(a.getYearPublished(), b.getYearPublished());
            case RATING -> (a, b) -> Double.compare(a.getRating(), b.getRating());
            case MAX_TIME -> (a, b) -> Integer.compare(a.getMaxPlayTime(), b.getMaxPlayTime());
            case MIN_TIME -> (a, b) -> Integer.compare(a.getMinPlayTime(), b.getMinPlayTime());
            case DIFFICULTY -> (a, b) -> Double.compare(a.getDifficulty(), b.getDifficulty());
            case MAX_PLAYERS -> (a, b) -> Integer.compare(a.getMaxPlayers(), b.getMaxPlayers());
            case MIN_PLAYERS -> (a, b) -> Integer.compare(a.getMinPlayers(), b.getMinPlayers());
            default -> throw new IllegalArgumentException("Invalid sortOn: " + sortOn);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    /**
     * check if the game pass the condition.
     *
     * @param game      game.
     * @param condition condition.
     * @return true if it passes the condition, false if not.
     */
    public boolean matchCondition(BoardGame game, String condition) {
        String[] parts = splitCondition(condition);
        String columnName = parts[0];
        String operator = parts[1];
        String value = parts[2];
        GameData col = GameData.fromString(columnName);
        return switch (col) {
            case MIN_PLAYERS -> compareInt(game.getMinPlayers(), operator, value);
            case MAX_PLAYERS -> compareInt(game.getMaxPlayers(), operator, value);
            case MIN_TIME -> compareInt(game.getMinPlayTime(), operator, value);
            case MAX_TIME -> compareInt(game.getMaxPlayTime(), operator, value);
            case ID -> compareInt(game.getId(), operator, value);
            case YEAR -> compareInt(game.getYearPublished(), operator, value);
            case RANK -> compareInt(game.getRank(), operator, value);
            case RATING -> compareDouble(game.getRating(), operator, value);
            case DIFFICULTY -> compareDouble(game.getDifficulty(), operator, value);
            case NAME -> compareString(game.getName(), operator, value);
        };
    }


}
