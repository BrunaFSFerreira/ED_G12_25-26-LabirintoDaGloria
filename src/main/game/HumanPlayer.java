package main.game;

import main.data.impl.list.LinkedUnorderedList;
import main.model.Hall;
import main.model.Room;

import java.util.Scanner;

/**
 * Class representing a Human Player in the maze game.
 * Extends the Player class and allows for user input to choose movements.
 */
public class HumanPlayer extends Player {
    /** Scanner for reading user input */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a HumanPlayer with the given name and starting position.
     * @param name the name of the human player
     * @param startingPosition the starting room of the human player
     */
    public HumanPlayer(String name, Room startingPosition) {
        super(name, startingPosition);
    }

    /**
     * Allows the human player to choose their next movement based on available neighboring rooms.
     * Prompts the user for input and validates the choice.
     * @param game the current game instance
     * @return the chosen room to move to
     */
    @Override
    public Room chooseMovement(Game game) {
        Room current = getCurrentPosition();

        System.out.println("You are currently in: " + current.getName());
        System.out.println("Available moves:");

        LinkedUnorderedList<Hall> neighborsList = new LinkedUnorderedList<>();
        int idxPrint = 1;
        for (Hall h : current.getNeighbors()) {
            neighborsList.addToRear(h);
            System.out.println(" " + idxPrint + ". " + h.getDestination().getName());
            idxPrint++;
        }

        if (idxPrint == 1) {
            System.out.println(" No available moves from this room.");
            return null;
        }

        while (true) {
            System.out.print("Choose your move (enter the number): ");
            String line;
            try {
                line = scanner.nextLine();
                if (line == null) return null;
            } catch (Exception e) {
                System.out.println("Error reading input. Try again.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Try again.");
                continue;
            }

            try {
                int chosen = Integer.parseInt(line);
                if (chosen >= 1) {
                    int counter = 1;
                    for (Hall h : neighborsList) {
                        if (counter == chosen) {
                            return h.getDestination();
                        }
                        counter++;
                    }
                }
            } catch (NumberFormatException ignored) {}

            System.out.println("Invalid choice. Please enter a valid number corresponding to your move.");
        }
    }
}
