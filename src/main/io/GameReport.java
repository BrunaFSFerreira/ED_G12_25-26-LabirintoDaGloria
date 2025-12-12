package main.io;

/**
 * Class representing a report of a completed game.
 */
public class GameReport {
    /** The unique identifier of the game */
    final String game_id;
    /** The time the game ended */
    final String end_time;
    /** The name of the winning player */
    final String winner;
    /** An array of PlayerReport objects for all players in the game */
    final PlayerReport[] players;

    /**
     * Constructs a GameReport with the specified details.
     * @param game_id the unique identifier of the game
     * @param end_time the time the game ended
     * @param winner the name of the winning player
     * @param players an array of PlayerReport objects for all players in the game
     */
    GameReport(String game_id, String end_time, String winner, PlayerReport[] players) {
        this.game_id = game_id;
        this.end_time = end_time;
        this.winner = winner;
        this.players = players;
    }
}
