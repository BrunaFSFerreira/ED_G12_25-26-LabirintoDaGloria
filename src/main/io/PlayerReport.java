package main.io;

import main.data.impl.list.DoubleLinkedUnorderedList;
import main.game.Player;

import java.util.Iterator;

/**
 * Data Transfer Object representing a report of a player's game session.
 * Used for serialization and deserialization of player report data.
 */
public class PlayerReport {
    final String playerName;
    final String finalPosition;
    final String initialPosition;
    final boolean isWinner;
    final int blockedTurnsLeft;

    final String[] pathAndEvents;

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
