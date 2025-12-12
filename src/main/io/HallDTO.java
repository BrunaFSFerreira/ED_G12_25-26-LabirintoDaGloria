package main.io;

/**
 * Data Transfer Object representing a hall in the maze.
 * Used for serialization and deserialization of hall data.
 */
public class HallDTO {
    /** The origin room ID of the hall */
    public String origin;
    /** The destination room ID of the hall */
    public String destination;
    /** The size (length) of the hall */
    public int size;

    /**
     * Default constructor for HallDTO.
     */
    public HallDTO() {
    }
}