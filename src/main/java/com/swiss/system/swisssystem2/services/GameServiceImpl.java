package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Matchup;
import com.swiss.system.swisssystem2.dtos.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;

    @Override
    public List<Matchup> startGame() {
        List<Player> players = playerService.listAllPlayers();
        removePlayersWith3WinsOr3Losses(players);

        Map<String, List<Player>> splitUpScores = splitUpScores(players);
        List<Matchup> matchups = new ArrayList<>();
        for (List<Player> playerList : splitUpScores.values()) {
            if (playerList.isEmpty()) break;

            Collections.shuffle(playerList);

            Set<String> allReadyPlayingPlayers = new HashSet<>();
            Queue<Player> playerQueue = new LinkedList<>(playerList);
            while (!playerQueue.isEmpty()) {
                Player player1 = playerQueue.poll();

                Optional<Player> notPlayedAgainstPlayer = playerQueue.stream()
                        .filter(player -> !player1.getListOfPlayedPlayers().contains(player.getName() + " - " + player.getPlayerId()))
                        .findFirst();

                if (notPlayedAgainstPlayer.isEmpty()) {
                    player1.getRecords().add(1);
                    player1.getListOfPlayedPlayers().add("bye");
                    player1.setCurrentScore(Player.getCurrentScore(player1.getRecords()));
                    playerService.updatePlayers(List.of(player1), false);
                    matchups.add(new Matchup(player1, null, true));
                    continue;
                }
                Player player2 = notPlayedAgainstPlayer.get();

                String player1FullName = player1.getName() + " - " + player1.getPlayerId();
                String player2FullName = player2.getName() + " - " + player2.getPlayerId();
                if (allReadyPlayingPlayers.contains(player1FullName) || allReadyPlayingPlayers.contains(player2FullName)) {
                    playerQueue.offer(player1);
                    continue;
                }

                matchups.add(new Matchup(player1, player2, false));
                allReadyPlayingPlayers.add(player1FullName);
                allReadyPlayingPlayers.add(player2FullName);

                player1.getListOfPlayedPlayers().add(player2FullName);
                player2.getListOfPlayedPlayers().add(player1FullName);

                playerService.updatePlayers(List.of(player1, player2), false);

                playerQueue.remove(player2);
            }
        }

        return matchups;
    }

    private void removePlayersWith3WinsOr3Losses(List<Player> players){
        players.removeIf(player -> player.getCurrentScore().charAt(0) == '3' || player.getCurrentScore().charAt(2) == '3');
    }

    private Map<String, List<Player>> splitUpScores(List<Player> players){
        Map<String, List<Player>> splitUpScores = new HashMap<>();
        players.forEach(player -> {
            if (splitUpScores.containsKey(player.getCurrentScore())) {
                splitUpScores.get(player.getCurrentScore()).add(player);
            } else {
                List<Player> playerList = new ArrayList<>();
                playerList.add(player);
                splitUpScores.put(player.getCurrentScore(), playerList);
            }
        });
        return splitUpScores;
    }
}
