package main;

import main.data.impl.list.ArrayUnorderedList;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.data.impl.list.LinkedList;
import main.data.impl.list.LinkedUnorderedList;
import main.game.Bot;
import main.game.Game;
import main.game.HumanPlayer;
import main.game.Player;
import main.io.JSONReader;
import main.io.JSONWriter;
import main.io.MapDTO;
import main.model.EnigmaData;
import main.model.Maze;
import java.util.Scanner;

/**
 * Main application class to run the maze game.
 */
public class App {

    /**
     * Default constructor for App class.
     */
    public App() {
    }

    /**
     * Main method to start the maze game application.
     * It handles user input for map selection and player creation,
     * initializes the game, and starts the game loop.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JSONReader reader = new JSONReader();
        JSONWriter writer = new JSONWriter();

        LinkedUnorderedList<MapDTO> maps = new JSONReader().writeMap();
        LinkedList<EnigmaData> listEnigmas = reader.readEnigmas();
        ArrayUnorderedList<Player> players = new ArrayUnorderedList<>();
        ArrayUnorderedList<Player> playersForGame = new ArrayUnorderedList<>();
        DoubleLinkedUnorderedList<Player> initialList = new DoubleLinkedUnorderedList<>();

        Maze maze = new Maze();

        if (maps.isEmpty()) {
            System.out.println("No maps available. Exiting.");
            return;
        }

        System.out.println("Maps available: ");
        int index = 1;
        for (MapDTO map : maps) {
            int roomsCount = map.rooms != null ? map.rooms.size() : 0;
            System.out.println("Map " + index + " - " + roomsCount + " rooms");
            index++;
        }

        int selectedMap = 1;
        while (true) {
            System.out.print("Select a map (1-" + maps.size() + "): ");
            String line = scanner.nextLine().trim();
            try {
                selectedMap = Integer.parseInt(line);
                if (selectedMap >= 1 && selectedMap <= maps.size()) {
                    break;
                } else {
                    System.out.println("Please, insert a number between 1 and " + maps.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry insert a number between 1 and " + maps.size());
            }
        }

        maze.loadJSONMap(selectedMap - 1);

        int numPlayers = 1;
        while (true) {
            System.out.print("Number of players (1-5): ");
            String line = scanner.nextLine().trim();
            try {
                numPlayers = Integer.parseInt(line);
                if (numPlayers >= 1 && numPlayers <= 5) {
                    break;
                } else {
                    System.out.println("Please, insert a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry insert a number between 1 and 5");
            }
        }

        for (int p = 1; p <= numPlayers; p++) {
            System.out.println("--- Player " + p + " ---");
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            String type = "";
            while (true) {
                System.out.print("Type (H = Human, B = Bot): ");
                type = scanner.nextLine().trim().toUpperCase();
                if (type.equals("H") || type.equals("B")) break;
                System.out.println("Invalid type. Enter H or B.");
            }

            if (type.equals("B")) {
                players.addToRear(new Bot(name, null));
            } else {
                players.addToRear(new HumanPlayer(name, null));
            }
        }

        System.out.println("Players created:");
        for (Player pl : players) {
            System.out.println(" - " + pl.getName() + " (" + pl.getClass().getSimpleName() + ")");
        }

        for (Player pl : players) {
            playersForGame.addToRear(pl);
        }

        Game game = new Game(maze, initialList, listEnigmas, scanner);
        game.addPlayers(playersForGame);
        game.start();

        writer.writeGameReport(game);

        scanner.close();
    }
}