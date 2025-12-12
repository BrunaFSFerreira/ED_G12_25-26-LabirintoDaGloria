package main.game;

import main.model.Room;
import main.utils.EventType;

import java.util.Random;

/**
 * Class representing a random event that can occur in the game.
 */
public class RandomEvent {

    private final EventType type;
    private final Random random;

    /**
     * Constructs a RandomEvent with the specified type.
     * @param type the type of the random event
     */
    public RandomEvent(EventType type) {
        this.type = type;
        this.random = new Random();
    }

    /**
     * Activates the random event for the given player in the context of the game.
     * @param player affected player
     * @param game current game instance
     */
    public void activate(Player player, Game game) {
        player.addActionToHistory("Event triggered: " + type.toString());
        System.out.print("--- EVENT: " + type + "for " + player.getName() + " ---\n");

        switch(type) {
            case EXTRA_MOVE:
                player.addActionToHistory("Gained an extra move!");
                break;
            case POSITION_SWAP:
                Player target = game.chooseRandomPlayer(player);
                if (target != null) {
                    Room temp = player.getCurrentPosition();
                    player.setCurrentPosition(target.getCurrentPosition());
                    target.setCurrentPosition(temp);
                    player.addActionToHistory("Swapped position with " + target.getName());
                    target.addActionToHistory("Swapped position with " + player.getName());
                }
                break;
            case MOVE_BACK:
                player.setBlockedShifts(1);
                player.addActionToHistory("Will miss next shift!");
                break;
            case TURN_BLOCK:
                int tunrs = random.nextInt(3) + 1;
                player.setBlockedShifts(tunrs);
                player.addActionToHistory("Will miss next " + tunrs + " shifts!");
                break;
            case GENERAL_SWAP:
                game.swapAllPlayerPositions();
                player.addActionToHistory("All players' positions have been swapped!");
                break;
            case NONE:
                player.addActionToHistory("No effect.");
                break;
        }
    }
}
