package main.io;

/**
 * Class representing a report of a completed game.
 */
public class GameReport {
    final String game_id;
    final String end_time;
    final String winner;
    final PlayerReport[] players;

    GameReport(String game_id, String end_time, String winner, PlayerReport[] players) {
        this.game_id = game_id;
        this.end_time = end_time;
        this.winner = winner;
        this.players = players;
    }
}
