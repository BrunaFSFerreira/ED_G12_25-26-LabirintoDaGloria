package main.io;

/**
 * Data Transfer Object representing a room in the maze.
 * Used for serialization and deserialization of room data.
 */
public class RoomDTO {
    /** Room unique identifier */
    public String id;
    /** Room name */
    public String name;
    /** Room has treasure */
    public boolean hasTreasure;
    /** Room is an entrance */
    public boolean isEntrance;
    /** Room coordinates */
    public int x, y;
    /** Correct lever ID for lever challenges, null if not applicable */
    public Integer correctLeverId;

    /**
     * Default constructor for RoomDTO.
     */
    public RoomDTO() {
    }

    /**
     * Determines the type of challenge associated with the room.
     * @return the challenge type as a string
     */
    public String getChallengeType() {
        if (correctLeverId != null) return "LEVER";
        if (isEntrance) return "ENTRANCE";
        if (hasTreasure) return "TREASURE";
        return "NORMAL";
    }

}