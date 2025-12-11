package main.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.data.impl.list.DoubleLinkedUnorderedList;
import main.data.impl.list.ArrayOrderedList;
import main.data.impl.list.ArrayUnorderedList; // Usado como auxiliar para o loop
import main.game.Game;
import main.game.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JSONWriter {

    private final String outputFilePath;

    public JSONWriter() {
        this("resource-files/game_report.json");
    }

    public JSONWriter(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    private class PlayerActionIterator {
        final Player player;
        final Iterator<String> actionIterator;

        PlayerActionIterator(Player p, Iterator<String> it) {
            this.player = p;
            this.actionIterator = it;
        }
    }

    private String formatActionNumber(int count) {
        if (count < 10) {
            return "00" + count + ". ";
        } else if (count < 100) {
            return "0" + count + ". ";
        }
        return count + ". ";
    }

    public void writeGameReport(Game game) {
        ArrayOrderedList<String> finalActions = new ArrayOrderedList<>();

        ArrayUnorderedList<PlayerActionIterator> playerIterators = new ArrayUnorderedList<>();

        DoubleLinkedUnorderedList<Player> allPlayers = game.getAllPlayers();
        Iterator<Player> initPlayerIterator = allPlayers.iterator();
        while(initPlayerIterator.hasNext()) {
            Player p = initPlayerIterator.next();
            playerIterators.addToRear(new PlayerActionIterator(p, p.getHistoricalActions().iterator()));
        }

        int globalIndex = 1;
        boolean allFinished;

        do {
            allFinished = true;

            Iterator<PlayerActionIterator> itManager = playerIterators.iterator();

            while(itManager.hasNext()) {
                PlayerActionIterator pair = itManager.next();

                if (pair.actionIterator.hasNext()) {
                    allFinished = false;

                    String action = pair.actionIterator.next();
                    String playerName = pair.player.getName();

                    String paddedIndex = formatActionNumber(globalIndex);
                    String formattedAction = paddedIndex + "Player name " + playerName + " - " + action.replace("Movement: ", "movement ");

                    finalActions.add(formattedAction);
                    globalIndex++;
                }
            }
        } while (!allFinished);

        int size = finalActions.size();
        String[] reportArray = new String[size];

        Iterator<String> arrayIterator = finalActions.iterator();
        int k = 0;
        while (arrayIterator.hasNext()) {
            String orderedAction = arrayIterator.next();
            String finalAction = orderedAction.replaceAll("^0*(\\d+)\\. ", "$1. ");
            reportArray[k++] = finalAction;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            gson.toJson(reportArray, writer);
            System.out.println("Relatório do jogo escrito com sucesso em " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o relatório JSON: " + e.getMessage());
        }
    }
}