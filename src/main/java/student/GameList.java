package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * a class that represent game what want to play.
 */
public class GameList implements IGameList {
    /**
     * a wish list that stores selected games.
     */
    private final Set<BoardGame> wishList;

    /**
     * Constructor for the GameList.
     */
    public GameList() {

        wishList = new HashSet<>();
    }

    /**
     * get a list of game names in String format.
     *
     * @return String list of game names.
     */
    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
        return wishList.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    /**
     * clear the wish list.
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        wishList.clear();
    }

    /**
     * count how many games in the wishlist.
     *
     * @return return the length of wishlist.
     */
    @Override
    public int count() {
        // TODO Auto-generated method stub
        return wishList.size();
    }

    /**
     * Saves the list of games to a file.
     * <p>
     * The contents of the file will be each game name on a new line. It will
     * overwrite the file if
     * it already exists.
     * <p>
     * Saves them in the same order as getGameNames.
     *
     * @param filename The name of the file to save the list to.
     */
    @Override
    public void saveGame(String filename) {
        // TODO Auto-generated method stub
        filename = filename + ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (String game : this.getGameNames()) {
                writer.write(game);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving game");
            e.printStackTrace();
        }
    }

    /**
     * Adds a game or games to the list.
     * <p>
     * If a single name is specified, that takes priority. However, it could also
     * use a number such
     * as 1 which would indicate game 1 from the current filtered list should be
     * added to the list.
     * (1 being the first game in the list, normal counting).
     * <p>
     * A range can also be added, so if 1-5 was presented, it is assumed that games
     * 1 through 5
     * should be added to the list - or if the number is larger than the filtered
     * group 1-n (with n
     * being the last game in the filter). 1-1 type formatting
     * is allowed, and treated as just adding a single game.
     * <p>
     * If "all" is specified, then all games in the filtered collection should be
     * added to the list.
     * <p>
     * If any part of the string is not valid, an IllegalArgumentException should be
     * thrown. Such as
     * ranges being out of range.
     *
     * @param str      the string to parse and add games to the list.
     * @param filtered the filtered list to use as a basis for adding.
     * @throws IllegalArgumentException if the string is not valid.
     */

    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        List<BoardGame> fList = filtered.toList();
        if (str.equalsIgnoreCase("ALL")) {
            wishList.addAll(fList);
            return;
        }
        try {

            for (BoardGame game : fList) {
                if (game.getName().equalsIgnoreCase(str)) {
                    wishList.add(game);
                    return;
                }
            }
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= fList.size()) {
                    throw new IllegalArgumentException("wrong input");
                }
                wishList.add(fList.get(index));
                return;

            }
            if (str.matches("\\d+-\\d+")) {
                String[] range = str.split("-");
                int start = Integer.parseInt(range[0]) - 1;
                int end = Integer.parseInt(range[1]) - 1;
                if (start < 0 || end >= fList.size() || start > end) {
                    throw new IllegalArgumentException("wrong input");
                }
                for (int i = start; i <= end; i++) {
                    wishList.add(fList.get(i));
                }
                return;
            }
            throw new IllegalArgumentException("game not found");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("wrong input");

        }
    }

    /**
     * Removes a game or games from the list.
     * <p>
     * If a single name is specified, that takes priority. However, it could also
     * use a number such
     * as 1 which would indicate game 1 from the current games list should be
     * removed. A range can
     * also be specified to remove multiple games.
     * <p>
     * If all is provided, then clear should be called.
     * <p>
     * If any part of the string is not valid, an IllegalArgumentException should be
     * thrown. Such as
     * ranges being out of range, or none of the results doing anything.
     *
     * @param str The string to parse and remove games from the list.
     * @throws IllegalArgumentException If the string is not valid.
     */

    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        List<String> sortedName = this.getGameNames();

        if (str.equalsIgnoreCase("ALL")) {
            wishList.clear();
            return;
        }
        try {
            for (BoardGame game : wishList) {
                if (game.getName().equalsIgnoreCase(str)) {
                    wishList.remove(game);
                    return;
                }
            }
            if (str.matches("\\d+")) {
                int index = Integer.parseInt(str) - 1;
                if (index < 0 || index >= sortedName.size()) {
                    throw new IllegalArgumentException("wrong input");
                }
                String gameName = sortedName.get(index);
                wishList.removeIf(game -> game.getName().equalsIgnoreCase(gameName));
                return;
            }
            if (str.matches("\\d+-\\d+")) {
                String[] range = str.split("-");
                int start = Integer.parseInt(range[0]) - 1;
                int end = Integer.parseInt(range[1]) - 1;
                if (start < 0 || end >= sortedName.size() || start > end) {
                    throw new IllegalArgumentException("wrong input");
                }
                List<String> removeList = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    removeList.add(sortedName.get(i));
                }
                wishList.removeIf(game -> removeList.contains(game.getName()));
                return;
            }
            throw new IllegalArgumentException("game not found");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("wrong input");
        }


    }


}
