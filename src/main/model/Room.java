package main.model;

import main.data.impl.list.LinkedUnorderedList;

/**
 * Class representing a room in the maze.
 */
public class Room {
    /** Unique identifier for the room */
    private String id;
    /** Name of the room */
    private String name;
    /** List of neighboring halls */
    private final LinkedUnorderedList<Hall> neighbors = new LinkedUnorderedList<>();
    /** Indicates if the room has a treasure */
    private boolean hasTreasure;
    /** Indicates if the challenge in the room has been resolved */
    private boolean isChallengeResolved;
    /** Challenge associated with the room */
    private Challenge challenge;
    /** Halls to unlock when the challenge is resolved */
    private final LinkedUnorderedList<Hall> hallsToUnlock = new LinkedUnorderedList<>();
    /** X and Y coordinates of the room */
    private int x, y;

    /** Default constructor for Room. */
    public Room() {}

    /**
     * Constructs a Room with the given id, name, and treasure status.
     * @param id the unique identifier for the room
     * @param name the name of the room
     * @param hasTreasure indicates if the room has a treasure
     */
    public Room(String id, String name, boolean hasTreasure) {
        this.id = id;
        this.name = name;
        this.hasTreasure = hasTreasure;
        this.isChallengeResolved = false;
        this.challenge = null;
    }

    /**
     * Gets the unique identifier of the room.
     * @return the unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the room.
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the neighboring halls of the room.
     * @return the list of neighboring halls
     */
    public LinkedUnorderedList<Hall> getNeighbors() {
        return neighbors;
    }

    /**
     * Checks if the room has a treasure.
     * @return true if the room has a treasure, false otherwise
     */
    public boolean isHasTreasure() {
        return hasTreasure;
    }

    /**
     * Sets whether the challenge in the room has been resolved.
     * @return true if the challenge in the room has been resolved, false otherwise
     */
    public boolean isChallengeResolved() {
        return isChallengeResolved;
    }

    /**
     * Sets whether the challenge in the room has been resolved.
     * @param resolved true if the challenge is resolved, false otherwise
     */
    public void setChallengeResolved(boolean resolved) {
        this.isChallengeResolved = resolved;
    }

    /**
     * Gets the challenge associated with the room.
     * @return the challenge
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Sets the challenge associated with the room.
     * @param challenge the challenge to set
     */
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    /**
     * Gets the halls to unlock when the challenge is resolved.
     * @return the list of halls to unlock
     */
    public LinkedUnorderedList<Hall> getHallsToUnlock() {
        return hallsToUnlock;
    }

    /**
     * Gets the X coordinate of the room.
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate of the room.
     * @param x the X coordinate to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y coordinate of the room.
     * @return the Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate of the room.
     * @param y the Y coordinate to set
     */
    public void setY(int y) {
        this.y = y;
    }
}