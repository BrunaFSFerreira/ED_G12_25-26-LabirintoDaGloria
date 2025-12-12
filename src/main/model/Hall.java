package main.model;

import main.game.Game;
import main.game.RandomEvent;
import main.game.Player;
import main.utils.EventType;

import java.util.Random;

public class Hall {

    private final Room destination;
    private boolean block;
    private final int size;

    public Hall(Room destination, boolean block, int size) {
        this.destination = destination;
        this.block = block;
        this.size = size;
    }

    public Hall(Room destination, int size) {
        this(destination, false, size);
    }

    public Room getDestination() {
        return destination;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public int getSize() {
        return size;
    }

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