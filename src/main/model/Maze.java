package main.model;

import main.data.impl.graph.WeightedGraph.AdjListGraph;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.data.impl.list.LinkedUnorderedList;
import main.data.impl.list.ArrayUnorderedList;
import main.io.JSONReader;
import main.utils.ChallengeType;

import java.util.Random;
import java.util.Iterator;

public class Maze {

    private final AdjListGraph<Room> rooms = new AdjListGraph<>();
    private final Random random = new Random();
    private final DoubleLinkedUnorderedList<Room> entryRooms = new DoubleLinkedUnorderedList<>();

    public boolean addRoom(Room d) {
        if (d == null || d.getId() == null || getRoomById(d.getId()) != null) {
            return false;
        }

        rooms.addVertex(d);
        return true;
    }

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

    public DoubleLinkedUnorderedList<Room> getEntries() {
        return entryRooms;
    }

    public DoubleLinkedUnorderedList<Room> getTreasures() {
        DoubleLinkedUnorderedList<Room> treasures = new DoubleLinkedUnorderedList<>();
        for (Room room : rooms) {
            if (room.isHasTreasure()) {
                treasures.addToRear(room);
            }
        }
        return treasures;
    }

    public void loadJSONMap(int mapIndex) {
        JSONReader reader = new JSONReader();
        LinkedUnorderedList<JSONReader.MapDTO> maps = new JSONReader().writeMap();

        if (maps.isEmpty()) {
            System.out.println("No maps found in JSON.");
            return;
        }

        if (mapIndex < 0 || mapIndex >= maps.size()) {
            System.out.println("Invalid map. First map will be loaded.");
            mapIndex = 0;
        }

        JSONReader.MapDTO map = null;
        int counter = 0;
        for (JSONReader.MapDTO dto : maps) {
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


        for (JSONReader.RoomDTO roomDTO : map.rooms) {
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

        for (JSONReader.HallDTO hallDTO : map.halls) {
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

        for (JSONReader.RoomDTO roomDTO : map.rooms) {
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

    public void printMaze() {

        if (rooms.isEmpty()) {
            System.out.println("Maze is empty.");
            return;
        }

        final int W = 7;   // largura da sala (mais pequeno)
        final int H = 3;   // altura da sala (mais pequeno)
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

        // --- DESENHAR SALAS ---
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

        // --- DESENHAR LIGAÇÕES ---
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

                // Horizontal
                if (oy == dy) {
                    int start = Math.min(midX, midDX) + 1;
                    int end = Math.max(midX, midDX) - 1;
                    for (int x = start; x <= end; x++)
                        grid[midY][x] = "─";
                }
                // Vertical
                else if (ox == dx) {
                    int start = Math.min(midY, midDY) + 1;
                    int end = Math.max(midY, midDY) - 1;
                    for (int y = start; y <= end; y++)
                        grid[y][midX] = "│";
                }
            }
        }

        // --- IMPRIMIR ---
        System.out.println("====== LABIRINTO ======");
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++)
                sb.append(grid[y][x]);
            System.out.println(sb);
        }
        System.out.println("=======================");
    }

    public Room getRoomById(String id) {
        for (Room room : rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public AdjListGraph<Room> getRooms() {
        return rooms;
    }


}