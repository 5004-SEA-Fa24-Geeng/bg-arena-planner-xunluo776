package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameList implements IGameList {

    private final Set<BoardGame> wishList;

    /**
     * Constructor for the GameList.
     */
    public GameList() {

        wishList = new HashSet<>();
    }


    @Override
    public List<String> getGameNames() {
        // TODO Auto-generated method stub
        return wishList.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        wishList.clear();
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        return wishList.size();
    }

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
