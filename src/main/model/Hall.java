package main.model;

import main.game.Game;
import main.game.RandomEvent;
import main.game.Player;
import main.utils.EventType;

import java.util.Random;

/**
 * Class representing a hall connecting rooms in the maze.
 */
public class Hall {

    private final Room destination;
    private boolean block;
    private final int size;

    /**
     * Constructs a Hall with the specified destination room, block status, and size.
     * @param destination the room this hall leads to
     * @param block whether the hall is blocked
     * @param size the size of the hall
     */
    public Hall(Room destination, boolean block, int size) {
        this.destination = destination;
        this.block = block;
        this.size = size;
    }

    /**
     * Constructs a Hall with the specified destination room and size. The hall is not blocked by default.
     * @param destination the room this hall leads to
     * @param size the size of the hall
     */
    public Hall(Room destination, int size) {
        this(destination, false, size);
    }

    /**
     * Gets the destination room of this hall.
     * @return the destination room
     */
    public Room getDestination() {
        return destination;
    }

    /**
     * Checks if the hall is blocked.
     * @param block true if the hall is blocked, false otherwise
     */
    public void setBlock(boolean block) {
        this.block = block;
    }

    /**
     * Gets the size of the hall.
     * @return the size of the hall
     */
    public int getSize() {
        return size;
    }

    /**
     * Activates a random event when a player uses this hall.
     * @param player the player using the hall
     * @param game the current game instance
     * @return true if the event was activated, false if the hall is blocked
     */
    public boolean activateEvent(Player player, Game game) {
        if (this.block) {
            System.out.println("The hall is blocked. You cannot proceed.");
            return false;
        }

        Random random = new Random();

        if (random.nextDouble() < 0.50) {
            EventType[] allEventTypes = EventType.values();
            EventType eventType = allEventTypes[random.nextInt(allEventTypes.length)];

            RandomEvent localEvent = new RandomEvent(eventType);

            localEvent.activate(player, game);

            System.out.println("A random event occurred: " + eventType);
        }

    return true;

    }
}