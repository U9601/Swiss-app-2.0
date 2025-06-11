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
        if (players.size() < 4) return Collections.emptyList();

        removePlayersWith3Wins(players);

        Map<String, List<Player>> splitUpScores = splitUpScores(players);
        List<Matchup> matchups = new ArrayList<>();
        for(List<Player> playerList : splitUpScores.values()) {
            if(playerList.isEmpty()) break;

            if(playerList.size() == 1){
                matchups.add(Matchup.builder().player1(playerList.get(0)).hasByeRound(true).build());
                break;
            }

            Collections.shuffle(playerList);

            for (int i = 0; i < (playerList.size() + 1) / 2; i++) {
                Player player1 = playerList.get(i);
                Optional<Player> notPlayedAgainstPlayer = playerList.stream()
                        .filter(player -> !player.equals(player1) && !player1.getListOfPlayedPlayers().contains(player.getName() + " - " + player.getPlayerId()))
                        .findFirst();

                if (notPlayedAgainstPlayer.isEmpty()) {
                    matchups.add(Matchup.builder().player1(playerList.get(i)).hasByeRound(true).build());
                    break;
                }

                Player player2 = notPlayedAgainstPlayer.get();
                matchups.add(new Matchup(player1, player2, false));
                player1.getListOfPlayedPlayers().add(notPlayedAgainstPlayer.get().getName() + " - " + player2.getPlayerId());
                player2.getListOfPlayedPlayers().add(player1.getName() + " - " + player1.getPlayerId());
            }
        }
        return matchups;
    }

    private void removePlayersWith3Wins(List<Player> players){
        players.removeIf(player -> player.getCurrentScore().charAt(0) == '3');
    }

    private Map<String, List<Player>> splitUpScores(List<Player> players){
        Map<String, List<Player>> splitUpScores = new HashMap<>();
        players.forEach(player -> {
            if(splitUpScores.containsKey(player.getCurrentScore())){
                splitUpScores.get(player.getCurrentScore()).add(player);
            }else{
                List<Player> playerList = new ArrayList<>();
                playerList.add(player);
                splitUpScores.put(player.getCurrentScore(), playerList);
            }
        });
        return splitUpScores;
    }
}
