package main.io;

import main.data.impl.list.LinkedUnorderedList;

/**
 * Data Transfer Object representing the entire map of the maze.
 * Used for serialization and deserialization of map data.
 */
public class MapDTO {
    /** List of rooms in the map */
    public LinkedUnorderedList<RoomDTO> rooms = new LinkedUnorderedList<>();
    /** List of halls in the map */
    public LinkedUnorderedList<HallDTO> halls = new LinkedUnorderedList<>();

    /** Default constructor for MapDTO. */
    public MapDTO() {
    }
}
