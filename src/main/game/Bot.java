package main.game;

import main.data.impl.graph.WeightedGraph.AdjListGraph;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.model.*;

import java.util.Iterator;
import java.util.Random;

/**
 * Class representing a Bot player in the maze game.
 */
public class Bot extends Player {
    /** Random number generator for the bot's decisions */
    Random random = new Random();

    /**
     * Constructs a Bot with the given name and starting position.
     * @param name the name of the bot
     * @param startingPosition the starting room of the bot
     */
    public Bot(String name, Room startingPosition) {
        super(name, startingPosition);
    }

    /**
     * Chooses the next room to move to based on the shortest path to the nearest treasure.
     * It uses the maze's graph structure to find the optimal path, using the shortest path algorithm.
     * @param game the current game instance
     * @return the next room to move to
     */
    @Override
    public Room chooseMovement(Game game) {
        Maze lab = game.getMaze();
        AdjListGraph<Room> graph = lab.getRooms();
        DoubleLinkedUnorderedList<Room> treasure = game.getMaze().getTreasures();
        Room current = getCurrentPosition();

        if (treasure != null) {
            Iterator<Room> itTreasury = treasure.iterator();
            if (itTreasury.hasNext()) {
                Room destination = itTreasury.next();
                Iterator<Room> path = graph.interatorShortestPath(current, destination);

                if (path != null && path.hasNext()) {
                    path.next();
                    if (path.hasNext()) {
                        return path.next();
                    }
                }
            }
        }

        Iterator<Hall> neighbors = current.getNeighbors().iterator();
        if (neighbors.hasNext()) {
            return neighbors.next().getDestination();
        }
        return null;
    }

    /**
     * Simulates the bot attempting to solve an enigma challenge.
     * He does not know the answer, so he randomly selects one of the possible answers.
     * @param game the current game instance
     * @param roomToUnlock the room associated with the enigma challenge
     * @return true if the bot solves the enigma, false otherwise
     */
    public boolean solveEnigma(Game game, Room roomToUnlock) {
        ChallengeManager manager = game.getChallengeManager();
        EnigmaData enigma = manager.getNextEnigma();

        if (enigma == null) {
            this.addActionToHistory("Enigma not retrieved for room " + roomToUnlock.getName() + ".");
            return true;
        }

        String[] wrongAnswers = enigma.getWrongAnswers();
        int wrongCount = (wrongAnswers != null) ? wrongAnswers.length : 0;
        int size = 1 + wrongCount;
        int choice = random.nextInt(size) + 1;
        boolean botChoseCorrectly = (random.nextInt(size) == 0);

        this.addActionToHistory("Bot: Attempted ENIGMA in " + roomToUnlock.getName() + ", selected index " + choice + ". Result: " + (botChoseCorrectly ? "SUCCESS" : "FAIL"));
        System.out.println("-> Bot " + this.getName() + " tried the Enigma. Choose: " + choice + ". " + (botChoseCorrectly ? "Success!" : "Failed."));

        if (botChoseCorrectly) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Simulates the bot pulling a lever in a lever challenge.
     * He randomly selects one of the levers.
     * @param game the current game instance
     * @param roomToUnlock the room associated with the lever challenge
     * @param correctLeverId the ID of the correct lever to pull
     * @return true if the bot pulled the correct lever, false otherwise
     */
    public boolean pullLever(Game game, Room roomToUnlock, int correctLeverId) {
        int numberOfLevers = 3;
        if (numberOfLevers < 2) numberOfLevers = 3;

        int playerChoice = random.nextInt(numberOfLevers) + 1;

        if (playerChoice == correctLeverId) {
            roomToUnlock.setChallengeResolved(true);
            for (Hall hall : roomToUnlock.getHallsToUnlock()) {
                hall.setBlock(false);
            }

            this.addActionToHistory("Bot: Solved LEVER challenge in room " + roomToUnlock.getName() + ". Correct lever pulled (" + correctLeverId + "). Halls unlocked.");
            System.out.println("-> Bot " + this.getName() + " Solved the LEVER challenge! Access unlocked permanently.");
            return true;
        } else {
            this.addActionToHistory("Bot: Failed LEVER challenge in room " + roomToUnlock.getName() + ". Wrong lever pulled (" + playerChoice + "). Access blocked.");
            System.out.println("-> Bot " + this.getName() + " pulled the wrong lever! Access blocked this shift.");
            return false;
        }
    }
}
