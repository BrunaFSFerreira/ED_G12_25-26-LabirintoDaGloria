package main.model;

import main.data.impl.list.LinkedList;
import main.data.impl.stack.LinkedStack;

import java.util.Iterator;
import java.util.Random;

/**
 * Class to manage enigma challenges within the maze game.
 */
public class ChallengeManager {
    /** Stack to hold enigmas that have been used recently. */
    private LinkedStack<EnigmaData> poll = new LinkedStack<>();
    /** Stack to hold enigmas that are available for use. */
    private LinkedStack<EnigmaData> available = new LinkedStack<>();
    /** Random number generator for shuffling enigmas. */
    private final Random random = new Random();

    /**
     * Constructs a ChallengeManager with a list of enigmas.
     * @param listEnigmas the list of enigmas to manage
     */
    public ChallengeManager(LinkedList<EnigmaData> listEnigmas) {
        initializeStacks(listEnigmas);
    }

    /**
     * Initializes the available stack with enigmas from the provided list, shuffled randomly.
     * @param listEnigmas the list of enigmas to initialize from
     */
    private void initializeStacks(LinkedList<EnigmaData> listEnigmas) {
        int size = listEnigmas.size();
        EnigmaData[] enigmasArray = (EnigmaData[]) new EnigmaData[size];
        int i = 0;
        Iterator<EnigmaData> it = listEnigmas.iterator();
        while (it.hasNext()) {
            enigmasArray[i++] = it.next();
        }

        for (int j = size - 1; j > 0; j--) {
            int randomIndex = random.nextInt(j + 1);
            EnigmaData temp = enigmasArray[j];
            enigmasArray[j] = enigmasArray[randomIndex];
            enigmasArray[randomIndex] = temp;
        }

        for (EnigmaData enigma : enigmasArray) {
            available.push(enigma);
        }
    }

    /**
     * Retrieves the next available enigma challenge.
     * @return the next EnigmaData, or null if none are available
     */
    public EnigmaData getNextEnigma() {
        if (available.isEmpty()){
            recycleEnigmas();
        }
        if (available.isEmpty()){
            return null;
        }

        EnigmaData enigma = available.pop();

        poll.push(enigma);

        return enigma;
    }

    /**
     * Recycles enigmas from the poll stack back to the available stack.
     */
    private void recycleEnigmas() {
        while (!poll.isEmpty()) {
            available.push(poll.pop());
        }
    }
}
