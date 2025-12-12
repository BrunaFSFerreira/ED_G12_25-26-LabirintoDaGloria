package main.game;

import main.data.impl.list.DoubleLinkedUnorderedList;
import main.model.Room;

/**
 * Abstract class representing a player in the maze game.
 */
public abstract class Player {
    /** The name of the player */
    private final String name;
    /** The current position of the player */
    private Room currentPosition;
    /** The number of blocked shifts for the player */
    private int blockedShifts;
    /** The historical actions of the player */
    private final DoubleLinkedUnorderedList<String> historicalActions;
    /** The initial position of the player */
    private Room initialPosition;

    /**
     * Constructs a Player with the given name and starting position.
     * @param name the name of the player
     * @param startingPosition the starting room of the player
     */
    public Player(String name, Room startingPosition) {
        this.name = name;
        this.currentPosition = startingPosition;
        this.blockedShifts = 0;
        this.historicalActions = new DoubleLinkedUnorderedList<>();
    }

    /**
     * Returns the name of the player.
     * @return the player's name
     */
    public String getName() { return name; }

    /**
     * Returns the current position of the player.
     * @return the current room of the player
     */
    public Room getCurrentPosition() { return currentPosition; }

    /**
     * Sets the current position of the player.
     * @param newPosition the new room of the player
     */
    public void setCurrentPosition(Room newPosition) { this.currentPosition = newPosition; }

    /**
     * Returns the number of blocked shifts for the player.
     * @return the number of blocked shifts
     */
    public int getBlockedShifts() { return blockedShifts; }

    /**
     * Sets the number of blocked shifts for the player.
     * @param shifts the number of blocked shifts to set
     */
    public void setBlockedShifts(int shifts) { this.blockedShifts = shifts; }

    /**
     * Returns the historical actions of the player.
     * @return a DoubleLinkedUnorderedList of historical actions
     */
    public DoubleLinkedUnorderedList<String> getHistoricalActions() { return historicalActions; }

    /**
     * Sets the initial position of the player.
     * @param initialPosition the initial room of the player
     */
    public void setInitialPosition(Room initialPosition) {
        this.initialPosition = initialPosition;
    }

    /**
     * Returns the initial position of the player.
     * @return the initial room of the player
     */
    public Room getInitialPosition() {
        return initialPosition;
    }

    /**
     * Adds an action to the player's historical actions.
     * @param action the action to be added
     */
    public void addActionToHistory(String action) {
        this.historicalActions.addToRear(action);
    }

    /**
     * Abstract method for choosing the next movement of the player.
     * @param game the current game instance
     * @return the room to move to
     */
    public Room chooseMovement(Game game) {
        return null;
    }
}
