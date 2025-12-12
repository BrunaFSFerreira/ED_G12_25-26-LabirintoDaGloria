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

public class JSONWriter {

    private final String outputFilePath;

    public JSONWriter() {
        this("resource-files/game_report_" + System.currentTimeMillis() + ".json");
    }

    public JSONWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

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
            System.out.println("Relatório do jogo escrito com sucesso em " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o relatório JSON: " + e.getMessage());
        }
    }
}