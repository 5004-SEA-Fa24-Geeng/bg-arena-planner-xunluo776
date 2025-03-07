package student;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


public class Planner implements IPlanner {
    private final Set<BoardGame> originalGames;
    private Set<BoardGame> filteredGames;
    public Planner(Set<BoardGame> games) {
        // TODO Auto-generated method stub
        this.originalGames = new HashSet<>(games); // Store original game set
        this.filteredGames = new HashSet<>(games);
    }

    @Override
    public Stream<BoardGame> filter(String filter) {
        // TODO Auto-generated method stub
        return filter(filter, GameData.NAME, true);
    }

    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        // TODO Auto-generated method stub
        return filter(filter, sortOn, true);
    }

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

    @Override
    public void reset() {
        // TODO Auto-generated method stub

        filteredGames = new HashSet<>(originalGames);
    }

    private String[] splitCondition(String condition) {

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

    private boolean compareString(String gameValue, String operator, String condition) {
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
    private boolean compareInt(int gameValue, String operator, String condition) {
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
    private boolean compareDouble(double gameValue, String operator, String condition) {
        double conditionDouble = Double.parseDouble(condition);
        return switch (operator){
            case "==" -> gameValue == conditionDouble;
            case "!=" -> gameValue != conditionDouble;
            case ">" -> gameValue > conditionDouble;
            case "<" -> gameValue < conditionDouble;
            case ">=" -> gameValue >= conditionDouble;
            case "<=" -> gameValue <= conditionDouble;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
    private Comparator<BoardGame> comparator(GameData sortOn, boolean ascending) {
        Comparator<BoardGame> comparator;
        switch (sortOn) {
            case NAME -> comparator = (a, b) -> a.getName().compareToIgnoreCase(b.getName());
            case ID -> comparator = (a,b)-> Integer.compare(a.getId(), b.getId());
            case RANK -> comparator = (a,b) -> Integer.compare(a.getRank(), b.getRank());
            case YEAR -> comparator =(a,b)-> Integer.compare(a.getYearPublished(), b.getYearPublished());
            case RATING -> comparator = (a,b)-> Double.compare(a.getRating(), b.getRating());
            case MAX_TIME -> comparator = (a,b)-> Integer.compare(a.getMaxPlayTime(), b.getMaxPlayTime());
            case MIN_TIME -> comparator = (a, b) -> Integer.compare(a.getMinPlayTime(), b.getMinPlayTime());
            case DIFFICULTY -> comparator = (a, b) -> Double.compare(a.getDifficulty(), b.getDifficulty());
            case MAX_PLAYERS -> comparator = (a,b)-> Integer.compare(a.getMaxPlayers(), b.getMaxPlayers());
            case MIN_PLAYERS -> comparator = (a, b)-> Integer.compare(a.getMinPlayers(), b.getMinPlayers());
            default -> throw new IllegalArgumentException("Invalid sortOn: " + sortOn);
        }
        if (!ascending) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

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
