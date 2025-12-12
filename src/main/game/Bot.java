package main.game;

import main.data.impl.graph.AdjListGraph;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.model.*;

import java.util.Iterator;
import java.util.Random;

public class Bot extends Player {
    Random random = new Random();

    public Bot(String name, Room startingPosition) {
        super(name, startingPosition);
    }

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
        System.out.println("-> Bot " + this.getName() + " tentou o Enigma. Escolha: " + choice + ". " + (botChoseCorrectly ? "Acertou!" : "Errou."));

        if (botChoseCorrectly) {
            return true;
        } else {
            return false;
        }

    }

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
            System.out.println("-> Bot " + this.getName() + " resolveu o desafio LEVER! Acesso desbloqueado permanentemente.");
            return true;
        } else {
            this.addActionToHistory("Bot: Failed LEVER challenge in room " + roomToUnlock.getName() + ". Wrong lever pulled (" + playerChoice + "). Access blocked.");
            System.out.println("-> Bot " + this.getName() + " errou a alavanca! Acesso bloqueado neste turno.");
            return false;
        }
    }
}
