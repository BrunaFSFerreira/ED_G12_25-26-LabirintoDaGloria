package main.io;

import main.data.impl.list.DoubleLinkedUnorderedList;
import main.game.Player;

import java.util.Iterator;

/**
 * Data Transfer Object representing a report of a player's game session.
 * Used for serialization and deserialization of player report data.
 */
public class PlayerReport {
    /** The name of the player */
    final String playerName;
    /** The final position of the player */
    final String finalPosition;
    /** The initial position of the player */
    final String initialPosition;
    /** Whether the player is the winner of the game */
    final boolean isWinner;
    /** The number of blocked turns left for the player */
    final int blockedTurnsLeft;
    /** The path taken and events encountered by the player */
    final String[] pathAndEvents;

    /**
     * Constructs a PlayerReport from a Player object and the game winner.
     * @param p the player to generate the report for
     * @param gameWinner the player who won the game
     */
    PlayerReport(Player p, Player gameWinner) {
        this.playerName = p.getName();
        this.finalPosition = p.getCurrentPosition() != null ? p.getCurrentPosition().getName() : "Unknown";
        this.initialPosition = p.getInitialPosition() != null ? p.getInitialPosition().getName() : "N/A";
        this.isWinner = p.equals(gameWinner);
        this.blockedTurnsLeft = p.getBlockedShifts();

        DoubleLinkedUnorderedList<String> historyList = p.getHistoricalActions();
        String[] actionsArray = new String[historyList.size()];
        Iterator<String> it = historyList.iterator();
        int index = 0;
        while (it.hasNext()) {
            actionsArray[index++] = it.next();
        }
        this.pathAndEvents = actionsArray;
    }
}
