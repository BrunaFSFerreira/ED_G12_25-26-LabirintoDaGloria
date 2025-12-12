package main.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.game.Game;
import main.game.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class responsible for writing game reports to JSON files.
 */
public class JSONWriter {
    /** The output file path for the JSON report. */
    private final String outputFilePath;

    /**
     * Constructs a JSONWriter with a default output file path.
     */
    public JSONWriter() {
        this("resource-files/game_report_" + System.currentTimeMillis() + ".json");
    }

    /**
     * Constructs a JSONWriter with a specified output file path.
     * @param outputFilePath the path to the output JSON file
     */
    public JSONWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * Writes the game report to a JSON file.
     * @param game the game instance to generate the report from
     */
    public void writeGameReport(Game game) {

        String gameId = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        String endTime = sdf.format(new Date());

        DoubleLinkedUnorderedList<Player> allPlayers = game.getAllPlayers();
        Player gameWinner = game.winner;

        PlayerReport[] playersArray = new PlayerReport[allPlayers.size()];

        int index = 0;
        for (Player p : allPlayers) {
            playersArray[index++] = new PlayerReport(p, gameWinner);
        }

        String winnerName = gameWinner != null ? gameWinner.getName() : "None";
        GameReport finalReportObject = new GameReport(gameId, endTime, winnerName, playersArray);


        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            gson.toJson(finalReportObject, writer);
            System.out.println("Game report successfully written in " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing JSON report: " + e.getMessage());
        }
    }
}