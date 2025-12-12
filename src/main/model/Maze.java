package main.model;

import main.data.impl.graph.AdjListGraph;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.data.impl.list.LinkedUnorderedList;
import main.data.impl.list.ArrayUnorderedList;
import main.io.HallDTO;
import main.io.JSONReader;
import main.io.MapDTO;
import main.io.RoomDTO;
import main.utils.ChallengeType;

import java.util.Random;
import java.util.Iterator;

/**
 * Class representing a maze composed of rooms and halls.
 */
public class Maze {

    private final AdjListGraph<Room> rooms = new AdjListGraph<>();
    private final Random random = new Random();
    private final DoubleLinkedUnorderedList<Room> entryRooms = new DoubleLinkedUnorderedList<>();

    /** Default constructor for Maze. */
    public Maze() {
    }

    /**
     * Adds a room to the maze.
     * @param d the room to add
     * @return true if the room was added successfully, false otherwise
     */
    public boolean addRoom(Room d) {
        if (d == null || d.getId() == null || getRoomById(d.getId()) != null) {
            return false;
        }

        rooms.addVertex(d);
        return true;
    }

    /**
     * Adds a hall connecting two rooms in the maze.
     * @param origin the origin room
     * @param destination the destination room
     * @param c the hall to add
     * @return true if the hall was added successfully, false otherwise
     */
    public boolean addHall(Room origin, Room destination, Hall c) {
        if (origin == null || destination == null || c == null) {
            return false;
        }

        if (getRoomById(origin.getId()) == null || getRoomById(destination.getId()) == null) {
            return false;
        }

        for (Hall existente : origin.getNeighbors()) {
            if (existente.getDestination().equals(destination)) {
                return false;
            }
        }

        origin.getNeighbors().addToRear(c);
        destination.getNeighbors().addToRear(new Hall(origin, c.getSize()));
        rooms.addEdge(origin, destination, c.getSize());
        return true;
    }

    /**
     * Retrieves the list of entry rooms in the maze.
     * @return a list of entry rooms
     */
    public DoubleLinkedUnorderedList<Room> getEntries() {
        return entryRooms;
    }

    /**
     * Retrieves a list of rooms that contain treasures.
     * @return a list of treasure rooms
     */
    public DoubleLinkedUnorderedList<Room> getTreasures() {
        DoubleLinkedUnorderedList<Room> treasures = new DoubleLinkedUnorderedList<>();
        for (Room room : rooms) {
            if (room.isHasTreasure()) {
                treasures.addToRear(room);
            }
        }
        return treasures;
    }

    /**
     * Loads a maze map from a JSON file based on the provided index.
     * @param mapIndex the index of the map to load
     */
    public void loadJSONMap(int mapIndex) {
        JSONReader reader = new JSONReader();
        LinkedUnorderedList<MapDTO> maps = new JSONReader().writeMap();

        if (maps.isEmpty()) {
            System.out.println("No maps found in JSON.");
            return;
        }

        if (mapIndex < 0 || mapIndex >= maps.size()) {
            System.out.println("Invalid map. First map will be loaded.");
            mapIndex = 0;
        }

        MapDTO map = null;
        int counter = 0;
        for (MapDTO dto : maps) {
            if (counter == mapIndex) {
                map = dto;
                break;
            }
            counter++;
        }

        if (map == null) {
            System.out.println("Map not found.");
            return;
        }

        ArrayUnorderedList<String> enigmaCandidates = new ArrayUnorderedList<>();


        for (RoomDTO roomDTO : map.rooms) {
            Room room = new Room(roomDTO.id, roomDTO.name, roomDTO.hasTreasure) {
            };
            room.setX(roomDTO.x);
            room.setY(roomDTO.y);

            if (!addRoom(room)) {
                System.out.println("Failed to add Room: " + roomDTO.id);
            }

            if (roomDTO.getChallengeType().equals("ENTRANCE")) {
                this.entryRooms.addToRear(room);
            }

            if (roomDTO.getChallengeType().equals("NORMAL")) {
                enigmaCandidates.addToRear(roomDTO.id);
            }
        }

        for (HallDTO hallDTO : map.halls) {
            Room origin = getRoomById(hallDTO.origin);
            Room destination = getRoomById(hallDTO.destination);

            if (origin == null || destination == null) {
                System.out.println("Failed to add Hall: " + hallDTO.origin + " -> " + hallDTO.destination);
                continue;
            }

            Hall hall = new Hall(destination, hallDTO.size);

            if (!addHall(origin, destination, hall)) {
                System.out.println("Failed to add Hall: " + hallDTO.origin + " -> " + hallDTO.destination);
            }
        }

        ArrayUnorderedList<String> selectedEnigmas = new ArrayUnorderedList<>();
        int enigmasToSelect = 3;
        int currentCandidatesCount = enigmaCandidates.size();

        if (currentCandidatesCount < enigmasToSelect) {
            System.err.println("Aviso: Apenas " + currentCandidatesCount + " salas candidatas a Enigma. Esperado: " + enigmasToSelect);
            enigmasToSelect = currentCandidatesCount;
        }

        for (int i = 0; i < enigmasToSelect; i++) {
            int randomIndex = random.nextInt(currentCandidatesCount);

            String selectedId = null;
            Iterator<String> it = enigmaCandidates.iterator();
            while (it.hasNext()) {
                String id = it.next();
                if (counter == randomIndex) {
                    selectedId = id;
                    break;
                }
                counter++;
            }

            if (selectedId != null) {
                selectedEnigmas.addToRear(selectedId);
                enigmaCandidates.remove(selectedId);
                currentCandidatesCount = enigmaCandidates.size();
            }
        }

        for (RoomDTO roomDTO : map.rooms) {
            Room room = getRoomById(roomDTO.id);
            if (room == null) continue;

            Challenge challenge = null;
            String challengeType = roomDTO.getChallengeType();

            if (challengeType.equals("LEVER")) {
                int correctLeverId = roomDTO.correctLeverId != null ? roomDTO.correctLeverId : 1;
                challenge = new Challenge(ChallengeType.LEVER, correctLeverId);

                for (Hall hall : room.getNeighbors()) {
                    hall.setBlock(true);
                    room.getHallsToUnlock().addToRear(hall);
                }

            } else if (selectedEnigmas.contains(roomDTO.id)) {
                challenge = new Challenge(ChallengeType.ENIGMA);
            }

            room.setChallenge(challenge);
        }

        printMaze();
    }

    /**
     * Prints a visual representation of the maze to the console.
     */
    public void printMaze() {

        if (rooms.isEmpty()) {
            System.out.println("Maze is empty.");
            return;
        }

        final int W = 7;
        final int H = 3;
        final int GAP_X = 3;
        final int GAP_Y = 1;

        int maxX = 0, maxY = 0;
        for (Room r : rooms) {
            maxX = Math.max(maxX, r.getX());
            maxY = Math.max(maxY, r.getY());
        }

        int width = (maxX + 1) * (W + GAP_X);
        int height = (maxY + 1) * (H + GAP_Y);

        String[][] grid = new String[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grid[y][x] = " ";

        for (Room room : rooms) {

            int ox = room.getX() * (W + GAP_X);
            int oy = room.getY() * (H + GAP_Y);

            grid[oy][ox] = "┌";
            for (int i = 1; i < W - 1; i++) grid[oy][ox + i] = "─";
            grid[oy][ox + W - 1] = "┐";

            grid[oy + 1][ox] = "│";
            grid[oy + 1][ox + W - 1] = "│";

            grid[oy + 2][ox] = "└";
            for (int i = 1; i < W - 1; i++) grid[oy + 2][ox + i] = "─";
            grid[oy + 2][ox + W - 1] = "┘";

            String symbol = "─";

            if (room.isHasTreasure()) {
                symbol = "*";
            } else if (room.getChallenge() != null) {
                ChallengeType type = room.getChallenge().getType();
                if (type == ChallengeType.ENIGMA) {
                    symbol = "?";
                } else if (type == ChallengeType.LEVER) {
                    symbol = "@";
                }
            } else if (entryRooms.contains(room)) {
                symbol = "#";
            }

            grid[oy + 1][ox + W / 2] = symbol;
        }

        for (Room room : rooms) {

            int ox = room.getX() * (W + GAP_X);
            int oy = room.getY() * (H + GAP_Y);

            int midX = ox + W / 2;
            int midY = oy + H / 2;

            for (Hall hall : room.getNeighbors()) {

                Room dst = hall.getDestination();
                int dx = dst.getX() * (W + GAP_X);
                int dy = dst.getY() * (H + GAP_Y);

                int midDX = dx + W / 2;
                int midDY = dy + H / 2;

                if (oy == dy) {
                    int start = Math.min(midX, midDX) + 1;
                    int end = Math.max(midX, midDX) - 1;
                    for (int x = start; x <= end; x++)
                        grid[midY][x] = "─";
                }
                else if (ox == dx) {
                    int start = Math.min(midY, midDY) + 1;
                    int end = Math.max(midY, midDY) - 1;
                    for (int y = start; y <= end; y++)
                        grid[y][midX] = "│";
                }
            }
        }

        System.out.println("====== LABYRINTH ======");
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++)
                sb.append(grid[y][x]);
            System.out.println(sb);
        }
        System.out.println("=======================");
    }

    /**
     * Retrieves a room by its unique identifier.
     * @param id the unique identifier of the room
     * @return the Room object if found, null otherwise
     */
    public Room getRoomById(String id) {
        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Gets the graph of rooms in the maze.
     * @return the adjacency list graph of rooms
     */
    public AdjListGraph<Room> getRooms() {
        return rooms;
    }


}