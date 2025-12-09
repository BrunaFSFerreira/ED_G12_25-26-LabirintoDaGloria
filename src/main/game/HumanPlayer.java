package main.game;

import main.model.Hall;
import main.model.Room;

import java.util.Iterator;
import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name, Room startingPosition) {
        super(name, startingPosition);
    }

    @Override
    public Room chooseMovement(Game game) {
        Room current = getCurrentPosition();

        System.out.println("You are currently in: " + current.getName());
        System.out.println("Available moves:");

        Iterator<Hall> neighbors = current.getNeighbors().iterator();

        if (!neighbors.hasNext()) {
            System.out.println("No available moves from this room.");
            return null;
        }

        int i = 1;
        while (neighbors.hasNext()) {
            Hall hall = neighbors.next();
            System.out.print(" " + i + ". To room: " + hall.getDestination().getName());
        }

        System.out.print("Sua escolha (ID da Sala): ");
        String chosenId = scanner.nextLine().trim();

        Iterator<Hall> validationNeighbors = current.getNeighbors().iterator();
        while (validationNeighbors.hasNext()) {
            Room destination = validationNeighbors.next().getDestination();
            if (destination.getName().equalsIgnoreCase(chosenId)) {
                return destination;
        }
    }
        System.out.println("Invalid choice. Please try again.");
        return null;
    }
}
